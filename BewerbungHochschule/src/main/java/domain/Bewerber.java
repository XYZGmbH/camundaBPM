package domain;

import utils.Haertefall;
import utils.Studiengang;

public class Bewerber extends Person{

	public Bewerber(String name, String vorname, int alter, int pid, String telefonnummer, String email,
			Studiengang studiengang, Haertefall haertefall, double nc) {
		super(name, vorname, alter, pid, telefonnummer, email, studiengang);
		this.haertefall = haertefall;
		this.nc = nc;
	}

	private Haertefall haertefall;
	private double nc;
	
	
	public Haertefall getHaertefall() {
		return haertefall;
	}
	public void setHaertefall(Haertefall haertefall) {
		this.haertefall = haertefall;
	}
	public double getNc() {
		return nc;
	}
	public void setNc(int nc) {
		this.nc = nc;
	}
	
}
