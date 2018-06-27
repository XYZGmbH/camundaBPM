package BelegaufgabeMAS.BewerbungHochschule;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import domain.Bewerber;
import utils.DBAccess;
import utils.SemesterbeitragBezahlt;
import utils.Studiengang;

public class CheckPayment implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		int pid = (int) execution.getVariable("pid");
		SemesterbeitragBezahlt payment = SemesterbeitragBezahlt.valueOf((String) execution.getVariable("payment"));
		if(payment == SemesterbeitragBezahlt.j) {
			
			DBAccess.getInstance().setPaid(pid);
		}
		
		execution.setVariable("paid", checkIfPayed(pid));
		
	}

	
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
