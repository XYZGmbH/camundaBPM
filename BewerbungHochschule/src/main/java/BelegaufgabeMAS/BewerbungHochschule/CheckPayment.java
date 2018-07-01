package BelegaufgabeMAS.BewerbungHochschule;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import domain.Bewerber;
import utils.DBAccess;
import utils.SemesterbeitragBezahlt;
import utils.Studiengang;

public class CheckPayment implements JavaDelegate{

	/* (non-Javadoc)
	 * Uses the variable payment that was set by a user input before
	 * If the variable is SemesterbeitragBezahlt.j the tuition fee was paid otherwise it was not paid. 
	 * If it was paid  the status in the database will be changed from unpaid to paid
	 * The variable paid is set on True/False depending on the result received from the database
	 */
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		int pid = (int) execution.getVariable("pid");
		SemesterbeitragBezahlt payment = SemesterbeitragBezahlt.valueOf((String) execution.getVariable("payment"));
		if(payment == SemesterbeitragBezahlt.j) {
			
			DBAccess.getInstance().setPaid(pid);
		}
		
		execution.setVariable("paid", checkIfPayed(pid));
		
	}

	
	/**
	 * Method that verifies whether the candidate paid the tuition fee or not by checking the tuition fee status in the database
	 * @param pid PersonId of the candidate
	 * @return paid boolean - true if tuition fee was paid, false if not
	 */
	public boolean checkIfPayed(int pid){
		
		boolean paid;
		
		Bewerber candidate = DBAccess.getInstance().getOurCandidate(pid);
		
		SemesterbeitragBezahlt tuitionFee = DBAccess.getInstance().einzahlungPruefen(candidate);
		
		if (tuitionFee == SemesterbeitragBezahlt.j ){
			paid = true;
		}else {
			paid = false;
		}
		
		return paid;
		
	}
	
	
	
	
}
