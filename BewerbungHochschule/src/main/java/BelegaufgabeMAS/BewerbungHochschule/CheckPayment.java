package BelegaufgabeMAS.BewerbungHochschule;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.DBAccess;
import utils.SemesterbeitragBezahlt;

public class CheckPayment implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		
		
	}

	
	public boolean checkIfPayed(){
		
		boolean bezahlt;
		
		//Bewerber bewerber = 
		
		SemesterbeitragBezahlt semesterbeitragBezahlt = DBAccess.getInstance().einzahlungPruefen(bewerber);
		
		if (semesterbeitragBezahlt == SemesterbeitragBezahlt.j ){
			bezahlt = true;
		}else {
			bezahlt = false;
		}
		
		return bezahlt;
		
	}
	
	
	
	
}
