package domain;

import java.util.Date;

import utils.Anrede;
import utils.Studiengang;

public abstract class Person {
	
	public Person(Anrede anrede, String name, String vorname, Date geburtsdatum, int pid, String telefonnummer, String email, Studiengang studiengang) {
		this.anrede = anrede;
		this.name = name;
		this.vorname = vorname;
		this.geburtsdatum = geburtsdatum;
		this.pid = pid;
		this.telefonnummer = telefonnummer;
		this.email = email;
		this.studiengang = studiengang;
	}

	private String name, vorname, telefonnummer, email;
	Date geburtsdatum; 
	int pid; 
	Studiengang studiengang;
	Anrede anrede;

	
	public Anrede getAnrede() {
		return anrede;
	}
	public void setAnrede(Anrede anrede) {
		this.anrede = anrede;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getTelefonnummer() {
		return telefonnummer;
	}
	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}

	public Studiengang getStudiengang() {
		return studiengang;
	}
	public void setStudiengang(Studiengang studiengang) {
		this.studiengang = studiengang;
	}	
	
}
