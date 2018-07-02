package BelegaufgabeMAS.BewerbungHochschule;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.DBAccess;
import utils.Studiengang;

public class InsertStudentIntoDB implements JavaDelegate {

	/* (non-Javadoc)
	 * Gets the variables and types the user gave as input that are needed to create a new student in the database. 
	 * The data is then being used to insert a new student into the database
	 * After the insertion the candidate with the same primary key as the student will be deleted from the database 
	 */
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String versichertennummer = execution.getVariable("versichertennummer").toString();
		String iban = execution.getVariable("iban").toString();
		String bic = execution.getVariable("bic").toString();	
		String matrikelnummer = this.generateMatrikelnummer();
		
		
		int pid = (int) execution.getVariable("pid");
		System.out.println("pid = " + pid);
		Studiengang studienfach = Studiengang.valueOf((String) execution.getVariable("Studienfach"));
		DBAccess.getInstance().insertIntoStudent(pid, matrikelnummer, versichertennummer, studienfach);
		
		DBAccess.getInstance().insertIntoBankdaten(bic, iban, pid);
		DBAccess.getInstance().deleteFromBewerber(pid);
		
		execution.setVariable("matrikelnummer", matrikelnummer);
		
	}
	
	
	/**
	 * generates a random matriculation number
	 * @return matrikelnummer a randomly generated matriculation number consisting of 6 numbers
	 */
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
