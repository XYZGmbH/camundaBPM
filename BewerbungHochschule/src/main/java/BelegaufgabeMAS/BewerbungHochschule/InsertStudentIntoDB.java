package BelegaufgabeMAS.BewerbungHochschule;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.DBAccess;
import utils.Studiengang;

public class InsertStudentIntoDB implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String versichertennummer = execution.getVariable("versichertennummer").toString();
		String iban = execution.getVariable("iban").toString();
		String bic = execution.getVariable("bic").toString();	
		String matrikelnummer = this.generateMatrikelnummer();
		
		
		int pid = (int) execution.getVariable("pid");
		Studiengang studienfach = Studiengang.valueOf((String) execution.getVariable("Studienfach"));
		DBAccess.getInstance().insertIntoStudent(matrikelnummer, versichertennummer, studienfach);
		int sid = DBAccess.getInstance().getSid();
		DBAccess.getInstance().insertIntoBankdaten(bic, iban, sid);
		DBAccess.getInstance().deleteFromBewerber(pid);
		
		execution.setVariable("matrikelnummer", matrikelnummer);
		
	}
	
	
	public String generateMatrikelnummer(){
		
		Random rand = new Random();
		String matrikelnummer = "";
		for (int i = 0; i<6; i++){
			int num = rand.nextInt(9);	
			matrikelnummer += Integer.toString(num);
		}
		return matrikelnummer;
		
	}

}
