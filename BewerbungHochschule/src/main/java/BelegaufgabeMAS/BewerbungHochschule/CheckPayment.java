package BelegaufgabeMAS.BewerbungHochschule;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import domain.Bewerber;
import utils.DBAccess;
import utils.SemesterbeitragBezahlt;

public class CheckPayment implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		//weiß nicht, ob es so funktioniert
		execution.setVariable("paid", checkIfPayed());
		
	}

	
	public boolean checkIfPayed(){
		
		boolean paid;
		
		Bewerber candidate = DBAccess.getInstance().getOurCandidate();
		
		SemesterbeitragBezahlt tuitionFee = DBAccess.getInstance().einzahlungPruefen(candidate);
		
		if (tuitionFee == SemesterbeitragBezahlt.j ){
			paid = true;
		}else {
			paid = false;
		}
		
		return paid;
		
	}
	
	
	
	
}
