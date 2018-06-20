package domain;

import utils.Studiengang;

public abstract class Person {
	
	public Person(String name, String vorname, int alter,int pid, String telefonnummer, String email, Studiengang studiengang) {
		this.name = name;
		this.vorname = vorname;
		this.alter = alter;
		this.pid = pid;
		this.telefonnummer = telefonnummer;
		this.email = email;
		this.studiengang = studiengang;
	}

	private String name, vorname, telefonnummer, email;
	int alter, pid; 
	Studiengang studiengang;
	Adresse adresse;
	
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
	public int getAlter() {
		return alter;
	}
	public void setAlter(int alter) {
		this.alter = alter;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	public Studiengang getStudiengang() {
		return studiengang;
	}
	public void setStudiengang(Studiengang studiengang) {
		this.studiengang = studiengang;
	}	
	
}
