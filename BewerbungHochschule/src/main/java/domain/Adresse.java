package domain;

public class Adresse {

	public Adresse(int aid, String adresszeile1, String adresszeile2, String ort, String plz) {
		this.aid = aid;
		this.adresszeile1 = adresszeile1;
		this.adresszeile2 = adresszeile2;
		this.plz = plz;
		this.ort = ort;
	}
	
	private int aid;
	private String adresszeile1, adresszeile2, ort, plz;
	
	
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getAdresszeile1() {
		return adresszeile1;
	}
	public void setAdresszeile1(String adresszeile1) {
		this.adresszeile1 = adresszeile1;
	}
	public String getAdresszeile2() {
		return adresszeile2;
	}
	public void setAdresszeile2(String adresszeile2) {
		this.adresszeile2 = adresszeile2;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	
	
	
	
}
