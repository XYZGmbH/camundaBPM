package domain;

import java.util.Date;

import utils.Anrede;
import utils.SemesterbeitragBezahlt;
import utils.Studiengang;

public class Student extends Person{

	public Student(Anrede anrede, String name, String vorname, Date geburtsdatum, int pid, String telefonnummer, String email,
			Studiengang studiengang, String matrikelnummer, SemesterbeitragBezahlt semBez, String versicherungsnummer) {
		super(anrede, name, vorname, geburtsdatum, pid, telefonnummer, email, studiengang);
		this.matrikelnummer = matrikelnummer;
		this.semBez = semBez;
		this.versicherungsnummer = versicherungsnummer;
	}
	

	private String matrikelnummer;
	private String versicherungsnummer;
	private SemesterbeitragBezahlt semBez;
	
	
	public String getVersicherungsnummer() {
		return versicherungsnummer;
	}
	public void setVersicherungsnummer(String versicherungsnummer) {
		this.versicherungsnummer = versicherungsnummer;
	}
	
	public String getMatrikelnummer() {
		return matrikelnummer;
	}
	public void setMatrikelnummer(String matrikelnummer) {
		this.matrikelnummer = matrikelnummer;
	}
	public SemesterbeitragBezahlt getSemBez() {
		return semBez;
	}
	public void setSemBez(SemesterbeitragBezahlt semBez) {
		this.semBez = semBez;
	}

}
