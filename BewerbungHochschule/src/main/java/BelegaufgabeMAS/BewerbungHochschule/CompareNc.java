package BelegaufgabeMAS.BewerbungHochschule;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import domain.Bewerber;
import utils.DBAccess;
import utils.Haertefall;

public class CompareNc implements JavaDelegate{
	

	@Override
	public void execute(DelegateExecution execution) throws Exception {

	//Wie Boolean weitergeben?	unserBewerberImNcVergleich(20, 0.15);
		
		
	
	}
	
	
	
	
	
	public boolean unserBewerberImNcVergleich(int anzahlStuediengangsplaetze, double haertefallquote) {

		Bewerber ourCandidate = DBAccess.getInstance().getOurCandidate();
		boolean continueProcess = true;

		LinkedList<Double> ncWerte = DBAccess.getInstance().getAlleNcs();
		double letzterNcZulassung = getLetzterNcFuerZulassung(anzahlStuediengangsplaetze, ncWerte);

		if (ourCandidate.getNc() > letzterNcZulassung) {
			if (ourCandidate.getHaertefall() == Haertefall.zero) {
				continueProcess = false;
			} else {
				LinkedList<Bewerber> schlechteBewerberListe = DBAccess.getInstance().getCandidatesWithInsufficientGrades(letzterNcZulassung);
				LinkedList<Double> ncsHaertefaelle = getNcsVonHaertefaellen(schlechteBewerberListe);
				Double schlechtesterHaertefallNc = getLetzterNcHaertefaelle(haertefallquote, anzahlStuediengangsplaetze,
						ncsHaertefaelle);
				if (ourCandidate.getNc() > schlechtesterHaertefallNc) {
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

			letzterNc = ncWerte.get(anzahlStuediengangsplaetze);

		} else {
			letzterNc = 5;
		}
		return letzterNc;
	}
	
	
	public LinkedList<Double> getNcsVonHaertefaellen(LinkedList<Bewerber> schlechteBewerberListe) {

		LinkedList<Double> ncsVonHaertefaellen = new LinkedList<Double>();
		for (Bewerber bewerber : schlechteBewerberListe) {
			if (!(bewerber.getHaertefall() == Haertefall.zero)) {
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


	
	
	// public LinkedList<Bewerber>getSchelcheteBewerberAberHaertefall(LinkedList <Bewerber>schlechteBewerberListe) {
	// LinkedList <Bewerber> schlechteBewerberAberHaertefallListe = new
	// LinkedList <Bewerber>();
	// for (Bewerber bewerber : schlechteBewerberListe){
	// if (!(bewerber.getHaertefall() == Haertefall.zero)){
	// schlechteBewerberAberHaertefallListe.add(bewerber);
	// }
	// }
	// return schlechteBewerberAberHaertefallListe;
	//
	// }
	
	
	
	
	
}
