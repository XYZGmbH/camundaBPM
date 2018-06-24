package BelegaufgabeMAS.BewerbungHochschule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.Anrede;
import utils.DBAccess;

import utils.Studiengang;

public class InsertCandidateIntoDb implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {

	
	Anrede anrede = Anrede.valueOf((String) execution.getVariable("anrede"));
	String name = (String) execution.getVariable("name");
	String vorname = (String) execution.getVariable("vorname");
	String geburtsdatumString = (String) execution.getVariable("geburtsdatum");
	String rufnummer = (String) execution.getVariable("rufnummer");
	String eMail = (String) execution.getVariable("email");
	Studiengang studienfach = Studiengang.valueOf((String) execution.getVariable("studienfach"));
	double nc = (double) execution.getVariable("nc");
	double bewerberquote = (double) execution.getVariable("bewerberquote");
	
	
	Date geburtsdatum = sdfToDate.parse(geburtsdatumString);
	DBAccess.getInstance().insertIntoPerson(anrede, name, vorname, new java.sql.Date(geburtsdatum.getTime()), rufnummer, eMail, studienfach);
	DBAccess.getInstance().insertIntoBewerber(bewerberquote, nc);
	
	
	
		
	}
	
	SimpleDateFormat sdfToDate = new SimpleDateFormat(
			"dd.MM.yyyy");
	
	
	

}
