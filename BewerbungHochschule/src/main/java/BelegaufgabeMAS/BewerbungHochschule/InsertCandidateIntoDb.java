package BelegaufgabeMAS.BewerbungHochschule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.Anrede;
import utils.DBAccess;

import utils.Studiengang;

public class InsertCandidateIntoDb implements JavaDelegate{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 * gets all the variables from the user input
	 * uses the variables to create a new person in the database and to create a new candidate in the database 
	 * with the same primary key as the person
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {

	
	Anrede anrede = Anrede.valueOf((String) execution.getVariable("anrede"));
	String name = (String) execution.getVariable("nachname");
	String vorname = (String) execution.getVariable("vorname");
	String geburtsdatumString = (String) execution.getVariable("geburtsdatum");
	String rufnummer = (String) execution.getVariable("rufnummer");
	String eMail = (String) execution.getVariable("email");
	Studiengang studienfach = Studiengang.valueOf((String) execution.getVariable("Studienfach"));
	double nc = (double) execution.getVariable("nc");
	HashMap<String, Object> map = (HashMap<String, Object>) execution.getVariable("bewerberquote");
	double bewerberquote = (double) map.get("bewerberquote");
	
	
	Date geburtsdatum = new SimpleDateFormat("dd.MM.yyyy").parse(geburtsdatumString);
	DBAccess.getInstance().insertIntoPerson(anrede, name, vorname, new java.sql.Date(geburtsdatum.getTime()), rufnummer, eMail, studienfach);
	
	int pid = DBAccess.getInstance().getPid();
	execution.setVariable("pid", pid);
	DBAccess.getInstance().insertIntoBewerber(bewerberquote, nc, pid);
	
	
	
		
	}
	

}
