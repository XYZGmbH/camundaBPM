package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class FileReaderEmail {

	public LinkedList<String> getEmailRows(String applicationStatus) {
		
		File f = new File("src/main/resources/templates/emailTempl" + applicationStatus + ".txt");
		BufferedReader in = null;
		String zeile = null;
		
		LinkedList<String> emailRows = new LinkedList<String>();
		
		try {
			in = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while((zeile = in.readLine()) != null) {
				emailRows.add(zeile);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return emailRows;
	}
	
	public HashMap<String, String> getEmailParts(String applicationStatus, String anrede, String vorname, String nachname){
		LinkedList<String> mail = this.getEmailRows(applicationStatus);
		HashMap<String,String> emailParts = new HashMap<String,String>();
		
		//subject
		emailParts.put("subject", applicationStatus += " f\u00FCr die Imaginary University");
		//body
		emailParts.put("body", this.createBodyFromList(mail, applicationStatus, anrede, vorname, nachname));
		
		return emailParts;
	}
	
	String createBodyFromList(LinkedList<String> emailRows, String applicationStatus, String anrede, String vorname, String nachname) {		
		String body = "";
		
		if(!emailRows.isEmpty()) {
			
		}
		
		for(String str:emailRows) {
			body += str + "\n";
		}		
		
		body = body.replaceFirst("\\$\\{applicationStatus\\}", applicationStatus);
		body = body.replaceFirst("\\$\\{anrede\\}", anrede);
		body = body.replaceFirst("\\$\\{vorname\\}", vorname);
		body = body.replaceFirst("\\$\\{nachname\\}", nachname);
		
		return body;
	}
	
}
