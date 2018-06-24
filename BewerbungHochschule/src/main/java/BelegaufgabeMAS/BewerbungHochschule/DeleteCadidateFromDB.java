package BelegaufgabeMAS.BewerbungHochschule;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.DBAccess;

public class DeleteCadidateFromDB implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		DBAccess.getInstance().deleteFromBewerber();
		
	}

}
