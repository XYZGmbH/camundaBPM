package io;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import BelegaufgabeMAS.BewerbungHochschule.CheckPayment;
import BelegaufgabeMAS.BewerbungHochschule.CompareNc;
import BelegaufgabeMAS.BewerbungHochschule.InsertStudentIntoDB;
import domain.Bewerber;
import utils.Anrede;
import utils.DBAccess;

import utils.Studiengang;

public class InsertBeerberTest {

//	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdfToDate = new SimpleDateFormat(
//				"dd.MM.yyyy");
//		
//		
//	    Date geburtsdatum = sdfToDate.parse("19.08.1993");
//	    
//	    CompareNc compareNc = new CompareNc();
//	    
//	    CheckPayment checkPayment = new CheckPayment();
//	    
//	    InsertStudentIntoDB insertStudentIntoDB = new InsertStudentIntoDB();
//	    
//		
//
//	    
//	    Studiengang studiengang = Studiengang.Angewandte_Informatik;
//	    DBAccess.getInstance().insertIntoStudent(insertStudentIntoDB.generateMatrikelnummer(), "1234", studiengang);
//	    int sid = DBAccess.getInstance().getSid();
//		DBAccess.getInstance().insertIntoBankdaten("BYLADEM1002", "DE12345678901234567891", sid);
//		
//		
//		
//		LinkedList <Double> ncs = new LinkedList <Double> ();
//		ncs= DBAccess.getInstance().getAlleNcs(Studiengang.Wirtschaftsinformatik);
//		for(Double he : ncs){
//			System.out.println(he);
//		}
//		
//		LinkedList <Bewerber> insufficintBewerber = new LinkedList <Bewerber>();
//		insufficintBewerber=DBAccess.getInstance().getCandidatesWithInsufficientGrades(2.4, Studiengang.Wirtschaftsinformatik);
//	    for(Bewerber bewerber : insufficintBewerber){
//	    	System.out.println("insufBewerberNC "+ bewerber.getNc());
//	    }
//	    
//	    boolean ncPassen = compareNc.unserBewerberImNcVergleich(20, 0.3, 0, 1,Studiengang.Wirtschaftsinformatik );
//	    System.out.println(ncPassen);
//	    
//	}

}
