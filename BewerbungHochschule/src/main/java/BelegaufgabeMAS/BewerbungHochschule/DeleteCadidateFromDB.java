package BelegaufgabeMAS.BewerbungHochschule;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.DBAccess;

public class DeleteCadidateFromDB implements JavaDelegate{
	/*
	 * gets the vairable pid which is the candidate's primary key and uses is it 
	 * to delete the candidate from the database
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		int pid = (int) execution.getVariable("pid");
		DBAccess.getInstance().deleteFromPerson(pid);
		DBAccess.getInstance().deleteFromBewerber(pid);		
	}

}
