package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;

import domain.Bewerber;

public class DBAccess {

	// Constructor
	private DBAccess() {

	}

	// Objects
	private static DBAccess exemplar = null;
	private Connection conn = null;

	// methods
	// String name, String vorname, int alter, int pid, String telefonnummer,
	// String email,
	// Studiengang studiengang, Haertefall haertefall, double nc,
//	public LinkedList<Bewerber> getBewerber() {
//		setCon();
//
//		String sql = "select * from 'Bewerber'";
//
//		try (Statement s = conn.createStatement()) {
//			try (ResultSet rs = s.executeQuery(sql)) {
//				LinkedList<Bewerber> bewerberListe = new LinkedList<Bewerber>();
//				while (rs.next()) {
//					int bid = rs.getInt("PID");
//					ResultSet personAtr = this.getPerson(bid);
//					Bewerber b = new Bewerber(personAtr.getString(2), personAtr.getString(3), personAtr.getInt(4),
//							personAtr.getInt(1), personAtr.getString(5), personAtr.getString(6),
//							Studiengang.valueOf(personAtr.getString(7)), null, 0, SemesterbeitragBezahlt.n);
//
//					if (personAtr.getObject(8) != null) {
//
//					}
//					bewerberListe.add(b);
//
//				}
//			} catch (SQLException e) {
//
//			}
//		} catch (SQLException e2) {
//
//		}
//
//		closeCon();
//		return null;
//	}

	public ResultSet getPerson(int pid) {
		setCon();
		String sql = "select * from 'Person' where 'pid' = ?";
		ResultSet rsReal = null;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, pid);
			try (ResultSet rs = ps.executeQuery(sql)) {

				rsReal = rs;

			} catch (SQLException e) {

			}
		} catch (SQLException e2) {

		}
		closeCon();
		return rsReal;
	}

	public void insertIntoStudent(String matrikelNummer, String versichertennummer) {
		setCon();

		String sql = "insert into student (SID, Matrikelnummer, Versichertennummer) values (?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, this.getPid());
			ps.setString(2, matrikelNummer);
			ps.setString(3, versichertennummer);

			try {
				ps.executeUpdate();
				System.out.println("Student inserted");
			} catch (SQLException e1) {
				System.out.println("Unable to execute update");
				e1.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("Unable to prepare Statement");
			e.printStackTrace();
		}

		closeCon();
	}

	public void insertIntoBewerber(String haertefall, double nc) {
		setCon();

		String sql = "insert into bewerber (BID, Haertefall, NC, SemesterbeitragBezahlt) " + "values (?,?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, this.getPid());
			ps.setString(2, haertefall);
			ps.setDouble(3, nc);
			ps.setString(4, SemesterbeitragBezahlt.n.toString());

			try {
				ps.executeUpdate();
				System.out.println("Bewerber inserted");
			} catch (SQLException e1) {
				System.out.println("Unable to execute update");
				e1.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("Unable to prepare Statement");
			e.printStackTrace();
		}

		closeCon();
	}

	public void insertIntoPerson(Anrede anrede, String name, String vorname, Date geburtsdatum, String telefonnummer, String email,
			Studiengang studiengang) {
		setCon();
		String sql = "insert into person (Anrede, Name, Vorname, alterJahre, Telefonnummer, EmailAdresse, Studiengang) "
				+ "values (?,?,?,?,?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, anrede.toString());
			ps.setString(2, name);
			ps.setString(3, vorname);
			ps.setDate(4, (java.sql.Date) geburtsdatum);
			ps.setString(5, telefonnummer);
			ps.setString(6, email);
			ps.setString(7, studiengang.toString());

			try {
				ps.executeUpdate();
				System.out.println("person inserted");
			} catch (SQLException e1) {
				System.out.println("Unable to execute update");
				e1.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("Unable to prepare Statement");
			e.printStackTrace();
		}

		closeCon();
	}

	
	
	public void insertIntoBankdaten(String Bic, String iban) {
	setCon();

	String sql = "INSERT INTO Bankdaten (Bic, Iban) values (?,?)";

	try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
		ps.setString(1, Bic);
		ps.setString(2, iban);
		

		try {
			ps.executeUpdate();
			System.out.println("Bankdaten inserted");
		} catch (SQLException e1) {
			System.out.println("Unable to execute update");
			e1.printStackTrace();
		}

	} catch (SQLException e) {
		System.out.println("Unable to prepare Statement");
		e.printStackTrace();
	}

	closeCon();
}
	
	
	
	
	
	
	
//	public void insertIntoAdresse(String Adresszeile1, String Adresszeile2, String plz, String ort) {
//		setCon();
//
//		String sql = "insert into Adresse (Adresszeile1, Adresszeile2, plz, ort) values (?,?,?,?)";
//
//		try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
//			ps.setString(1, Adresszeile1);
//			ps.setString(2, Adresszeile2);
//			ps.setString(3, plz);
//			ps.setString(4, ort);
//
//			try {
//				ps.executeUpdate();
//				System.out.println("Adresse inserted");
//			} catch (SQLException e1) {
//				System.out.println("Unable to execute update");
//				e1.printStackTrace();
//			}
//
//		} catch (SQLException e) {
//			System.out.println("Unable to prepare Statement");
//			e.printStackTrace();
//		}
//
//		closeCon();
//	}

	private int getPid() {

		String sql = "select max(pid) from 'person'";

		try (Statement s = conn.createStatement()) {
			try (ResultSet rs = s.executeQuery(sql)) {
				return rs.getInt(1);

			} catch (SQLException e1) {
				System.out.println("Unable to execute Statement");
				e1.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("Unable to create Statement");
			e.printStackTrace();
		}

		return 1;
	}

	private void setCon() {

		try {
			System.out.println("Load JDBC Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Unable to load Driver\n");
			e.printStackTrace();
		}

		String url = "jdbc:mysql://" + DBCred.HOSTNAME + ":" + DBCred.PORT + "/" + DBCred.DBNAME;

		try {
			conn = DriverManager.getConnection(url, DBCred.USER, DBCred.PASSWORD);
			System.out.println("Succesfully opened connection");
		} catch (SQLException e) {
			System.out.println("Unable to get Connection");
			e.printStackTrace();
		}
	}

	private void closeCon() {
		try {
			conn.close();
			System.out.println("Succesfully closed");
		} catch (SQLException e) {
			System.out.println("Unable to close Connection");
			e.printStackTrace();
		}
	}

	public static DBAccess getInstance() {
		if (exemplar == null) {
			exemplar = new DBAccess();
		}
		return exemplar;
	}

	
	// Unseren Bewerber aus DB filtern

	public Bewerber getOurCandidate() {
		setCon();
		int pid = this.getPid();
		Bewerber bewerber = null;
		ResultSet rs = null;
		PreparedStatement pSmt = null;
		String sql = "SELECT * FROM Person INNER JOIN Bewerber on pid = bid where pid = " +pid;
		
		try{
			pSmt = conn.prepareStatement(sql);
			rs = pSmt.executeQuery();
			if (!rs.next()){
				System.out.println("Our candidate wasn't found in the DB");
			}else{
				Anrede anrede = Anrede.valueOf(rs.getString("anrede"));
				String name = rs.getString("name");
				String vorname = rs.getString("vorname");
				Date geburtsdatum = rs.getDate("geburtsdatum");
				String telefonnummer = rs.getString("telefonnummer");
				String eMail = rs.getString("emailAdresse");
				Studiengang studiengang = Studiengang.valueOf(rs.getString("studiengang"));
				Haertefall haertefall = Haertefall.valueOf(rs.getString("haertefall"));
				Double nc = rs.getDouble("nc");
				SemesterbeitragBezahlt semesterbeitragBezahlt = SemesterbeitragBezahlt
						.valueOf(rs.getString("semesterbeitragBezahlt"));

				bewerber = new Bewerber(anrede, name, vorname, geburtsdatum, pid, telefonnummer, eMail, studiengang, haertefall,
						nc, semesterbeitragBezahlt);
			}
			
		}catch(SQLException e){
			System.out.println("Method getOurCandidate had some issues");
			e.printStackTrace();
		}
		
		return bewerber;

	}

	// Semesterbeitragseinzahlung prüfen

	public SemesterbeitragBezahlt einzahlungPruefen(Bewerber bewerber){
		SemesterbeitragBezahlt semesterbeitragBezahlt = null;
		
		setCon();

		ResultSet rs = null;
		PreparedStatement pSmt = null;
		String sql = "SELECT semesterbeitragBezahlt FROM Bewerber WHERE bid = " + bewerber.getPid();

		try {
			pSmt = conn.prepareStatement(sql);
			rs = pSmt.executeQuery();

			if (!rs.next()) {
				System.out.println("Kein Bewerber in Datenbank");
			} else {
				semesterbeitragBezahlt = SemesterbeitragBezahlt.valueOf(rs.getString("semesterbeitragBezahlt"));

				}
			

		}catch(SQLException e){
		System.out.println("einzahlungPruefen konnte nicht ausgefuehrt werden");
		e.printStackTrace();
	    }

	return semesterbeitragBezahlt;
	}
	
	
	

	// Delete-Methoden

	public void deleteFromPerson(int pId) {
		setCon();
		try {
			PreparedStatement pSmt = null;
			String sql = "DELETE FROM Person WHERE pid =" + pId;
			pSmt = conn.prepareStatement(sql);
			pSmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Delete from person konnte nicht ausgeführt werden");
			e.printStackTrace();
		}
		closeCon();

	}

	public void deleteFromBewerber(int pId) {
		setCon();
		try {
			PreparedStatement pSmt = null;
			String sql = "DELETE FROM Bewerber WHERE bId =" + pId;
			pSmt = conn.prepareStatement(sql);
			pSmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Delete from bewerber konnte nicht ausgeführt werden");
			e.printStackTrace();
		}
		closeCon();
	}

	public void deleteFromStudent(int pId) {
		setCon();
		try {
			PreparedStatement pSmt = null;
			String sql = "DELETE FROM Student WHERE sId =" + pId;
			pSmt = conn.prepareStatement(sql);
			pSmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Delete from student konnte nicht ausgeführt werden");
			e.printStackTrace();
		}
		closeCon();

	}

	// Methoden für Nc-Werte vergleichen

	public LinkedList<Double> getAlleNcs() {
		setCon();

		ResultSet rsNcs = null;
		PreparedStatement pSmt = null;
		String sql = "SELECT nc FROM Bewerber";
		LinkedList<Double> ncWerte = new LinkedList<Double>();
		try {
			pSmt = conn.prepareStatement(sql);
			rsNcs = pSmt.executeQuery();

			if (!rsNcs.next()) {
				System.out.println("Keine Bewerber in Datenbank");
			} else {

				while (rsNcs.next()) {
					double nc = rsNcs.getDouble("nc");
					ncWerte.add(nc);

				}
			}

		} catch (SQLException e) {
			System.out.println("NcWerte vergleichen konnte nicht ausgeführt werden");
			e.printStackTrace();
		}

		closeCon();
		return ncWerte;
	}

	public LinkedList<Bewerber> getCandidatesWithInsufficientGrades(Double lastNcForAdmission) {

		setCon();
		LinkedList<Bewerber> listOfCandidatesWithInsufficientGrades = new LinkedList<Bewerber>();
		Bewerber candidate = null;
		ResultSet rs = null;
		PreparedStatement pSmt = null;
		String sql = "SELECT * FROM Person INNER JOIN Bewerber WHERE nc > " + lastNcForAdmission;

		try {
			setCon();
			pSmt = conn.prepareStatement(sql);
			rs = pSmt.executeQuery();

			if (!rs.next()) {

				System.out.println("All candiadtes get the admission");

			} else {

				while (rs.next()) {
					int pid = rs.getInt("pid");
					Anrede anrede = Anrede.valueOf(rs.getString("anrede"));
					String name = rs.getString("name");
					String vorname = rs.getString("vorname");
					Date geburtsdatum = rs.getDate("geburtsdatum");
					String telefonnummer = rs.getString("telefonnummer");
					String eMail = rs.getString("emailAdresse");
					Studiengang studiengang = Studiengang.valueOf(rs.getString("studiengang"));
					Haertefall haertefall = Haertefall.valueOf(rs.getString("haertefall"));
					Double nc = rs.getDouble("nc");
					SemesterbeitragBezahlt semesterbeitragBezahlt = SemesterbeitragBezahlt
							.valueOf(rs.getString("semesterbeitragBezahlt"));

					candidate = new Bewerber(anrede, name, vorname, geburtsdatum, pid, telefonnummer, eMail, studiengang, haertefall,
							nc, semesterbeitragBezahlt);

					listOfCandidatesWithInsufficientGrades.add(candidate);

				}
			}

		} catch (SQLException e) {
			System.out.println("getCandidatesWithInsufficientGrades couldn't be executed");
			e.printStackTrace();
		}
		closeCon();

		return listOfCandidatesWithInsufficientGrades;

	}

}
