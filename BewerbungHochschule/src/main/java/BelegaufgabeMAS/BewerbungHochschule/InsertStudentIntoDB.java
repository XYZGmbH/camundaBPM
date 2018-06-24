package BelegaufgabeMAS.BewerbungHochschule;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.DBAccess;

public class InsertStudentIntoDB implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String versichertennummer = execution.getVariable("versichertennummer").toString();
		String iban = execution.getVariable("iban").toString();
		String bic = execution.getVariable("bic").toString();	
		int pid = (int) execution.getVariable("pid");
		
		DBAccess.getInstance().insertIntoStudent(this.generateMatrikelnummer(), versichertennummer, pid);
		DBAccess.getInstance().insertIntoBankdaten(bic, iban, pid);
		DBAccess.getInstance().deleteFromBewerber(pid);
	
		
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
