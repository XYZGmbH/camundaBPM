package io;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import BelegaufgabeMAS.BewerbungHochschule.CheckPayment;
import BelegaufgabeMAS.BewerbungHochschule.CompareNc;
import BelegaufgabeMAS.BewerbungHochschule.InsertStudentIntoDB;
import utils.Anrede;
import utils.DBAccess;

import utils.Studiengang;

public class InsertBeerberTest {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdfToDate = new SimpleDateFormat(
				"dd.MM.yyyy");
		
		
	    Date geburtsdatum = sdfToDate.parse("19.08.1993");
	    
	    CompareNc compareNc = new CompareNc();
	    
	    CheckPayment checkPayment = new CheckPayment();
	    
	    InsertStudentIntoDB insertStudentIntoDB = new InsertStudentIntoDB();
	    
		
		DBAccess.getInstance().insertIntoPerson(Anrede.valueOf("Herr"), "Doe", "John", new java.sql.Date(geburtsdatum.getTime()), "017621145249", "judith.hoegerl@gmail.de" , Studiengang.valueOf("Wirtschaftsinformatik"));

		DBAccess.getInstance().insertIntoBewerber(0, 2.2);
    
	
		DBAccess.getInstance().insertIntoPerson(Anrede.valueOf("Herr"), "Mustermann", "Max", new java.sql.Date(geburtsdatum.getTime()), "017621145249", "judith.hoegerl@gmail.de" , Studiengang.valueOf("Wirtschaftsinformatik"));
		DBAccess.getInstance().insertIntoBewerber(0.15, 4.0);
		
		boolean weiter =   compareNc.unserBewerberImNcVergleich(3, 0.8);
	 System.out.println("Hier yo: " + weiter);
	    
	    boolean paid = checkPayment.checkIfPayed();
	    System.out.println(paid);
	    
	    DBAccess.getInstance().insertIntoStudent(insertStudentIntoDB.generateMatrikelnummer(), "1234");
		DBAccess.getInstance().insertIntoBankdaten("BYLADEM1002", "DE12345678901234567891");
		
		DBAccess.getInstance().deleteFromBewerber();
	    
	    
	}

}
