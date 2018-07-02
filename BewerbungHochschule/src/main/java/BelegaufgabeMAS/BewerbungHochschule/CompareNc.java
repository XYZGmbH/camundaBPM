package BelegaufgabeMAS.BewerbungHochschule;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import domain.Bewerber;
import utils.DBAccess;
import utils.Studiengang;

public class CompareNc implements JavaDelegate {

	/*
	 * (non-Javadoc) 
	 * Uses the variable bewerberquote which was the output of the Dmn
	 * Uses the variable studienfach that was set by a user input 
	 * Uses the variable Pid which is the candidate's primary key in the database
	 * Checks whether our candiadte's grade - also considering the bewerberquote -is enough 
	 * to get the admission for the university 
	 * If the grade was sufficient boolean ncPassend is true and type zusage is set otherwise it
	 * is false and type absage is set
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		HashMap<String, Object> map = (HashMap<String, Object>) execution.getVariable("bewerberquote");
		double bewerberquote = (double) map.get("bewerberquote");

		Studiengang studienfach = Studiengang.valueOf((String) execution.getVariable("Studienfach"));
		int pid = (int) execution.getVariable("pid");

		// Number of pepople per field of study, 0.3 rate for cases of hadrship
		// (30%)
		boolean ncPassend = unserBewerberImNcVergleich(20, 0.3, bewerberquote, pid, studienfach);

		execution.setVariable("unterlagenKorrekt", false);

		if (ncPassend == true) {
			execution.setVariable("ncPassend", true);
			execution.setVariable("type", "zusage");
		} else {
			execution.setVariable("ncPassend", false);
			execution.setVariable("type", "absage");
		}

	}

	/**
	 * Checks whether our candidate's grade is sufficient to pass the normal admission
	 * If it is not, it checks whether our candidate is a case of hardship
	 * If this is the case, the candidate's grade is compared with the grades 
	 * of the other people with cases of hardship
	 * @param anzahlStuediengangsplaetze number of persons per field of study
	 * @param haertefallquote rate of cases of hardship
	 * @param bewerberquote rate of candidate's case of hardship
	 * @param pid candidate's primary key in database
	 * @param studienfach candidate's field of study
	 * @return continueProcess boolean true if grade sufficient false if not
	 */
	public boolean unserBewerberImNcVergleich(int anzahlStuediengangsplaetze, double haertefallquote,
			double bewerberquote, int pid, Studiengang studienfach) {

		Bewerber ourCandidate = DBAccess.getInstance().getOurCandidate(pid);

		boolean continueProcess = true;

		LinkedList<Double> ncWerte = DBAccess.getInstance().getAlleNcs(studienfach);
		double letzterNcZulassung = getLetzterNcFuerZulassung(anzahlStuediengangsplaetze, ncWerte);

		if (ourCandidate.getNc() >= letzterNcZulassung) {
			if (ourCandidate.getHaertefall() == 0) {
				continueProcess = false;

			} else {

				LinkedList<Bewerber> schlechteBewerberListe = DBAccess.getInstance()
						.getCandidatesWithInsufficientGrades(letzterNcZulassung, studienfach);
				LinkedList<Double> ncsHaertefaelle = getNcsVonHaertefaellen(schlechteBewerberListe);
				Double schlechtesterHaertefallNc = getLetzterNcHaertefaelle(haertefallquote, anzahlStuediengangsplaetze,
						ncsHaertefaelle);
				if (ourCandidate.getNc() - bewerberquote >= schlechtesterHaertefallNc) {
					continueProcess = false;
				}

			}
		}
		return continueProcess;

	}

	/**
	 * Sorts the list of all ncs of all candidates 
	 * If the number of people per field of study is smaller than the list of ncs the last nc for admission
	 * is identified by its position in the list
	 * 
	 * @param anzahlStuediengangsplaetze
	 * @param ncWerte
	 * @return double last nc for admission
	 */
	public double getLetzterNcFuerZulassung(int anzahlStuediengangsplaetze, LinkedList<Double> ncWerte) {

		double letzterNc;

		Collections.sort(ncWerte, new Comparator<Double>() {

			@Override
			public int compare(Double i, Double i2) {
				// TODO Auto-generated method stub
				return i.compareTo(i2);
			}
		});

		if (anzahlStuediengangsplaetze <= ncWerte.size()) {

			letzterNc = ncWerte.get(anzahlStuediengangsplaetze - 1);
			System.out.println("Letzter Nc: " + letzterNc);

		} else {
			letzterNc = 5;
		}
		return letzterNc;
	}

	/**
	 * Gets all ncs from people with cases of hardship that did not have the
	 * sufficient grade to pass in the first admission round 
	 * @param schlechteBewerberListe list of candidates that did not pass the first admission round
	 * @return ncsVonHaertefaellen list of double ncs by people with cases of hardship
	 */
	public LinkedList<Double> getNcsVonHaertefaellen(LinkedList<Bewerber> schlechteBewerberListe) {

		LinkedList<Double> ncsVonHaertefaellen = new LinkedList<Double>();
		for (Bewerber bewerber : schlechteBewerberListe) {
			if (!(bewerber.getHaertefall() == 0)) {

				double nc = bewerber.getNc();

				ncsVonHaertefaellen.add(nc);
			}
		}
		return ncsVonHaertefaellen;

	}

	/**
	 * With the number of persons per field of study and the number of cases of
	 * hardship the last numerus clausus is found out by the position in the
	 * ordererd list of ncs
	 * 
	 * @param haertefallquote
	 *            rate of case of hardship
	 * @param anzahlStuediengangsplaetze
	 *            number of persosns per field of study
	 * @param ncsVonHaertefaellen
	 *           list of ncs of cases of hardship
	 * @return double last nc for admission
	 */
	public Double getLetzterNcHaertefaelle(Double haertefallquote, int anzahlStuediengangsplaetze,
			LinkedList<Double> ncsVonHaertefaellen) {

		double schlechtesterHaertefallNc;

		if (!ncsVonHaertefaellen.isEmpty()) {

			int schlechtesterNcPos = (int) Math.round(anzahlStuediengangsplaetze * haertefallquote);
			schlechtesterHaertefallNc = getLetzterNcFuerZulassung(schlechtesterNcPos, ncsVonHaertefaellen);

		} else {
			schlechtesterHaertefallNc = 5;
		}
		return schlechtesterHaertefallNc;

	}

}
