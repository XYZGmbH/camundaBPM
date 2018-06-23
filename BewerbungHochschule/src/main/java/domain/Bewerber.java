package domain;

import java.util.Date;

import utils.Anrede;

import utils.SemesterbeitragBezahlt;
import utils.Studiengang;

public class Bewerber extends Person {

	public Bewerber(Anrede anrede, String name, String vorname, Date geburtsdatum, int pid, String telefonnummer, String email,
			Studiengang studiengang, double haertefall, double nc, SemesterbeitragBezahlt semesterbeitragBezahlt) {
		super(anrede, name, vorname, geburtsdatum, pid, telefonnummer, email, studiengang);
		this.haertefall = haertefall;
		this.nc = nc;
		this.semesterbeitragBezahlt = semesterbeitragBezahlt;
	}

	private double haertefall;
	private double nc;
	private SemesterbeitragBezahlt semesterbeitragBezahlt;

	public void setNc(double nc) {
		this.nc = nc;
	}

	public SemesterbeitragBezahlt getSemesterbeitragBezahlt() {
		return semesterbeitragBezahlt;
	}

	public void setSemesterbeitragBezahlt(SemesterbeitragBezahlt semesterbeitragBezahlt) {
		this.semesterbeitragBezahlt = semesterbeitragBezahlt;
	}

	public double getHaertefall() {
		return haertefall;
	}

	public void setHaertefall(double haertefall) {
		this.haertefall = haertefall;
	}

	public double getNc() {
		return nc;
	}

	public void setNc(int nc) {
		this.nc = nc;
	}

}
