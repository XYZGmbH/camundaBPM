package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

public class FileReaderEmail {

	public LinkedList<String> getEmailRows(String applicationStatus) {
		
		InputStream inS = FileReaderEmail.class.getResourceAsStream("/templates/emailTempl" + applicationStatus + ".txt");
		BufferedReader in = null;
		String zeile = null;
		
		LinkedList<String> emailRows = new LinkedList<String>();
		
		in = new BufferedReader(new InputStreamReader(inS));
		
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
