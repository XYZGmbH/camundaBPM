package BelegaufgabeMAS.BewerbungHochschule;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import utils.Haertefall;

public class CompareNc implements JavaDelegate{

	@Override
	public void execute(DelegateExecution exec) throws Exception {
		// TODO Auto-generated method stub
		exec.getVariable("name");
	}
	
	public boolean compNc(int nc) {
		LinkedList<Integer> ncWerte = new LinkedList<Integer>();//hier sql mit select nc from bewerber where nc <= Studiengangsnc
		
		boolean b = false;
		//jetzt bracuehn wir ne anzahl an plätzen
		
		
		Collections.sort(ncWerte, new Comparator<Integer>() {

			@Override
			public int compare(Integer i, Integer i2) {
				// TODO Auto-generated method stub
				return i.compareTo(i2);
			}});
		
		
		
		return false;
	}
	
	public boolean compNc(int nc, Haertefall haertefall) {
		
		
		
		return false;
	}

}
