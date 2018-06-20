package domain;

import utils.SemesterbeitragBezahlt;
import utils.Studiengang;

public class Student extends Person{

	public Student(String name, String vorname, int alter, int pid, String telefonnummer, String email,
			Studiengang studiengang, String matrikelnummer, SemesterbeitragBezahlt semBez) {
		super(name, vorname, alter, pid, telefonnummer, email, studiengang);
		this.matrikelnummer = matrikelnummer;
		this.semBez = semBez;
	}
	
	private String matrikelnummer;
	private SemesterbeitragBezahlt semBez;
	
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
