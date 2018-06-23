package BelegaufgabeMAS.BewerbungHochschule;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.DBAccess;
import utils.Studiengang;

public class InsertCandidateIntoDb implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {

	//vielleicht noch Anrede?
	//Berechneter Härtefall fehlt
	//Alter fehlt
		
	String name = execution.getVariable("name").toString();
	String vorname = execution.getVariable("vorname").toString();
	//int alterJahre = (int) execution.getVariable("alter");
	String rufnummer = execution.getVariable("rufnummer").toString();
	String eMail = execution.getVariable("email").toString();
	String studienfach = execution.getVariable("studienfach").toString();
	double nc = (double) execution.getVariable("nc");
	
	
	DBAccess.getInstance().insertIntoPerson(name, vorname, alter, rufnummer, eMail, studienfach);
	DBAccess.getInstance().insertIntoBewerber(haertefall, nc);
	
	
	
		
	}
	
	
	
	

}
