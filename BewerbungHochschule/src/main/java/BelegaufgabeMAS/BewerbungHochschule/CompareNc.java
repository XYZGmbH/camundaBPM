package BelegaufgabeMAS.BewerbungHochschule;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import domain.Bewerber;
import utils.DBAccess;

public class CompareNc implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		// Anzahl Leute pro Studiengang, 0.3 Quote für Härtefälle, hier 30 Prozent
		// execution.setVariable("ncPassend", unserBewerberImNcVergleich(20, 0.3));

		HashMap<String, Object> map = (HashMap<String, Object>) execution.getVariable("bewerberquote");
		double bewerberquote = (double) map.get("bewerberquote");
		
		int pid = (int) execution.getVariable("pid");
		
		boolean ncPassend = unserBewerberImNcVergleich(20, 0.3, bewerberquote, pid);

		
		execution.setVariable("unterlagenKorrekt", false);
		
		if (ncPassend == true) {
			execution.setVariable("ncPassend", true);
			execution.setVariable("type", "zusage");
		} else {
			execution.setVariable("ncPassend", false);
			execution.setVariable("type", "absage");
		}

	}

	public boolean unserBewerberImNcVergleich(int anzahlStuediengangsplaetze, double haertefallquote,
			double bewerberquote, int pid) {

		Bewerber ourCandidate = DBAccess.getInstance().getOurCandidate(pid);

		boolean continueProcess = true;

		LinkedList<Double> ncWerte = DBAccess.getInstance().getAlleNcs();
		double letzterNcZulassung = getLetzterNcFuerZulassung(anzahlStuediengangsplaetze, ncWerte);

		if (ourCandidate.getNc() > letzterNcZulassung) {
			if (ourCandidate.getHaertefall() == 0) {
				continueProcess = false;

			} else {

				LinkedList<Bewerber> schlechteBewerberListe = DBAccess.getInstance()
						.getCandidatesWithInsufficientGrades(letzterNcZulassung);
				LinkedList<Double> ncsHaertefaelle = getNcsVonHaertefaellen(schlechteBewerberListe);
				Double schlechtesterHaertefallNc = getLetzterNcHaertefaelle(haertefallquote, anzahlStuediengangsplaetze,
						ncsHaertefaelle);
				if (ourCandidate.getNc() - bewerberquote > schlechtesterHaertefallNc) {
					continueProcess = false;
				}

			}
		}
		return continueProcess;

	}

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
