package domain;

import utils.Haertefall;
import utils.Studiengang;

public class Bewerber extends Person{

	public Bewerber(String name, String vorname, int alter, int pid, String telefonnummer, String email,
			Studiengang studiengang, Haertefall haertefall, int nc) {
		super(name, vorname, alter, pid, telefonnummer, email, studiengang);
		this.haertefall = haertefall;
		this.nc = nc;
	}

	private Haertefall haertefall;
	private int nc;
	
	
	public Haertefall getHaertefall() {
		return haertefall;
	}
	public void setHaertefall(Haertefall haertefall) {
		this.haertefall = haertefall;
	}
	public int getNc() {
		return nc;
	}
	public void setNc(int nc) {
		this.nc = nc;
	}
	
}
