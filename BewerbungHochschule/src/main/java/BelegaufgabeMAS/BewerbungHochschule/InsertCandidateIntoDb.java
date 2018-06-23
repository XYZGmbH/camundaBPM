package BelegaufgabeMAS.BewerbungHochschule;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.Anrede;
import utils.DBAccess;
import utils.Haertefall;
import utils.Studiengang;

public class InsertCandidateIntoDb implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {

	//vielleicht noch Anrede?
	//Berechneter Härtefall fehlt
	//Alter fehlt
	
	Anrede anrede = Anrede.valueOf(execution.getVariable("anrede").toString());
	String name = execution.getVariable("name").toString();
	String vorname = execution.getVariable("vorname").toString();
	Date geburtsdatum = (Date) execution.getVariable("geburtsdatum");
	String rufnummer = execution.getVariable("rufnummer").toString();
	String eMail = execution.getVariable("email").toString();
	Studiengang studienfach = Studiengang.valueOf(execution.getVariable("studienfach").toString());
	double nc = (double) execution.getVariable("nc");
	String bewerberquote = execution.getVariable("bewerberquote").toString();
	
	
	DBAccess.getInstance().insertIntoPerson(anrede, name, vorname, geburtsdatum, rufnummer, eMail, studienfach);
	DBAccess.getInstance().insertIntoBewerber(bewerberquote, nc);
	
	
	
		
	}
	
	
	
	

}
