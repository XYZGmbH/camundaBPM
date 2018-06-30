package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

import domain.Bewerber;

/**
 * @author judithhogerl
 *
 */
/**
 * @author judithhogerl
 *
 */
public class DBAccess {

	/**
	 * Private Constructor for Singleton.
	 */
	private DBAccess() {

	}

	private static DBAccess exemplar = null;
	private Connection conn = null;

	/**
	 * getPerson: Returns a ResultSet with all Information dedicated to a specific
	 * person.
	 * 
	 * @param pid
	 *            PersonId of the person to get data from database
	 * @return rsRreal: Result Set with all data of the person from the database
	 */
	public ResultSet getPerson(int pid) {
		setCon();
		String sql = "SELECT * from Person where pid = ?";
		ResultSet rsReal = null;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, pid);
			try (ResultSet rs = ps.executeQuery(sql)) {

				rsReal = rs;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		closeCon();
		return rsReal;
	}

	/**
	 * insertIntoStudent: Method to insert a new student into the database
	 * 
	 * @param pid
	 *            PersonId of the person that will get an entry in the student table
	 * @param matrikelNummer
	 *            matriculation number of the new student
	 * @param versichertennummer
	 *            Social security number of the new student
	 * @param studiengang
	 *            degree program of the new student
	 */
	public void insertIntoStudent(int pid, String matrikelNummer, String versichertennummer, Studiengang studiengang) {
		setCon();

		String sql = "INSERT INTO Student (Sid, Matrikelnummer, Versichertennummer, Studiengang) values (?,?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, pid);
			ps.setString(2, matrikelNummer);
			ps.setString(3, versichertennummer);
			ps.setString(4, studiengang.toString());

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

	/**
	 * setPaid: changes the status of paid in the student database from unpaid to
	 * paid
	 * 
	 * @param pid
	 *            Id of the student who's status is to be changed
	 */
	public void setPaid(int pid) {
		setCon();
		PreparedStatement pSmt = null;

		try {
			pSmt = conn.prepareStatement("UPDATE Bewerber SET SemesterbeitragBezahlt = ? WHERE bid= ?");
			pSmt.setString(1, SemesterbeitragBezahlt.j.toString());
			pSmt.setInt(2, pid);
			pSmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Unable to execute setPaid");
			e.printStackTrace();
		}
		closeCon();
	}

	/**
	 * insertIntoBewerber: method to insert a new candidate into the database
	 * 
	 * @param haertefall
	 *            case of hardship of the candidate
	 * @param nc
	 *            numerus clausus of the candidate
	 * @param pid
	 *            PersonId of the candidate
	 */
	public void insertIntoBewerber(double haertefall, double nc, int pid) {
		setCon();

		String sql = "INSERT INTO Bewerber (BID, Haertefall, NC, SemesterbeitragBezahlt) " + "values (?,?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, pid);
			ps.setDouble(2, haertefall);
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

	/**
	 * insertIntoPerson: method to insert a new person into the database
	 * 
	 * @param anrede
	 *            the person's address (Mr./Ms.)
	 * @param name
	 *            the person's last name
	 * @param vorname
	 *            the person's first name
	 * @param geburtsdatum
	 *            the person's birthday
	 * @param telefonnummer
	 *            the person's phone number
	 * @param email
	 *            the person's email address
	 * @param studiengang
	 *            the person's degree program
	 */
	public void insertIntoPerson(Anrede anrede, String name, String vorname, Date geburtsdatum, String telefonnummer,
			String email, Studiengang studiengang) {
		setCon();
		String sql = "INSERT INTO Person (Anrede, Name, Vorname, Geburtsdatum, Telefonnummer, EmailAdresse, Studiengang) "
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

	/**
	 * insertIntoBankdaten: method to insert bank details into database
	 * 
	 * @param Bic
	 * @param iban
	 * @param pid
	 *            PersonId of the person who's bank details are about to be inserted
	 */
	public void insertIntoBankdaten(String Bic, String iban, int pid) {
		setCon();

		String sql = "INSERT INTO Bankdaten (BankId, Bic, Iban) values (?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, pid);
			ps.setString(2, Bic);
			ps.setString(3, iban);

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

	/**
	 * getPid: method to get the PersonId of the last person that was inserted into
	 * the database
	 * 
	 * @return pid PersonId of the person that had just been inserted into database
	 */
	public int getPid() {

		setCon();

		ResultSet rs = null;
		PreparedStatement pSmt = null;
		int pid = 0;
		String sql = "SELECT MAX(pid) from Person";
		try {
			pSmt = conn.prepareStatement(sql);
			rs = pSmt.executeQuery();

			if (!rs.next()) {
				System.out.println("no person in DB");
			} else {
				pid = rs.getInt(1);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		closeCon();

		return pid;
	}

	/**
	 * getSid:
	 * method to get the StudentId of the last person that was inserted into the
	 * database
	 * 
	 * @return sid StudentId of the last person that was inserted into the database
	 */
	public int getSid() {

		setCon();

		ResultSet rs = null;
		PreparedStatement pSmt = null;
		int sid = 0;
		String sql = "SELECT MAX(sid) from Student";
		try {
			pSmt = conn.prepareStatement(sql);
			rs = pSmt.executeQuery();

			if (!rs.next()) {
				System.out.println("no Student in DB");
			} else {
				sid = rs.getInt(1);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		closeCon();

		return sid;
	}

	/**
	 * setCon:
	 * method to set the connection to the database
	 */
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

	/**
	 * closeCon:
	 * method to close the connection with the database
	 */
	private void closeCon() {
		try {
			conn.close();
			System.out.println("Succesfully closed");
		} catch (SQLException e) {
			System.out.println("Unable to close Connection");
			e.printStackTrace();
		}
	}

	/**
	 * getInstance:
	 * singleton method for DBAccess class
	 * 
	 * @return exemplar singleton
	 */
	public static DBAccess getInstance() {
		if (exemplar == null) {
			exemplar = new DBAccess();
		}
		return exemplar;
	}

	// Unseren Bewerber aus DB filtern

	/**
	 * getOurCandidate:
	 * @param pid pid of desired Candidate
	 * @return Bewerber-Object, with attributes like the real candidate
	 */
	public Bewerber getOurCandidate(int pid) {
		setCon();
		Bewerber bewerber = null;
		ResultSet rs = null;
		PreparedStatement pSmt = null;
		String sql = "SELECT * FROM Person INNER JOIN Bewerber on pid = bid where pid = " + pid;

		try {
			pSmt = conn.prepareStatement(sql);
			rs = pSmt.executeQuery();
			if (!rs.next()) {
				System.out.println("Our candidate wasn't found in the DB");
			} else {
				Anrede anrede = Anrede.valueOf(rs.getString("anrede"));
				String name = rs.getString("name");
				String vorname = rs.getString("vorname");
				Date geburtsdatum = rs.getDate("geburtsdatum");
				String telefonnummer = rs.getString("telefonnummer");
				String eMail = rs.getString("emailAdresse");
				Studiengang studiengang = Studiengang.valueOf(rs.getString("studiengang"));
				double haertefall = rs.getDouble(("haertefall"));
				Double nc = rs.getDouble("nc");
				SemesterbeitragBezahlt semesterbeitragBezahlt = SemesterbeitragBezahlt
						.valueOf(rs.getString("semesterbeitragBezahlt"));

				bewerber = new Bewerber(anrede, name, vorname, geburtsdatum, pid, telefonnummer, eMail, studiengang,
						haertefall, nc, semesterbeitragBezahlt);
			}

		} catch (SQLException e) {
			System.out.println("Method getOurCandidate had some issues");
			e.printStackTrace();
		}
		closeCon();

		return bewerber;

	}

	// Semesterbeitragseinzahlung prüfen

	/**
	 * einzahlungPruefen:
	 * check if a candidate has payed his tuitition fees.
	 * @param bewerber the candidate we want the information about.
	 * @return Enum Value of SemesterbeitragBezahlt
	 */
	public SemesterbeitragBezahlt einzahlungPruefen(Bewerber bewerber) {
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

		} catch (SQLException e) {
			System.out.println("einzahlungPruefen konnte nicht ausgefuehrt werden");
			e.printStackTrace();
		}
		closeCon();

		return semesterbeitragBezahlt;
	}

	// Delete-Methoden

	/**
	 * deleteFromPerson:
	 * deletes A specific Person from the database.
	 * @param pId pid of which person should be deleted from the database
	 */
	public void deleteFromPerson(int pId) {
		setCon();
		try {
			PreparedStatement pSmt = null;
			String sql = "DELETE FROM Person WHERE pid =" + pId;
			pSmt = conn.prepareStatement(sql);
			pSmt.execute();
		} catch (SQLException e) {
			System.out.println("Delete from person konnte nicht ausgeführt werden");
			e.printStackTrace();
		}
		closeCon();

	}

	/**
	 * deleteFromBewerber:
	 * deletes a specific candidate from the database.
	 * @param pid bid of the candidate who should be deleted.
	 */
	public void deleteFromBewerber(int pid) {
		setCon();
		try {
			PreparedStatement pSmt = null;
			String sql = "DELETE FROM Bewerber WHERE bid =" + pid;
			pSmt = conn.prepareStatement(sql);
			pSmt.execute();
		} catch (SQLException e) {
			System.out.println("Delete from bewerber konnte nicht ausgeführt werden");
			e.printStackTrace();
		}
		closeCon();
	}

	/**
	 * deleteFromStudent:
	 * deletes a specific student from the database.
	 * @param pId sid of the student who should be deleted.
	 */
	public void deleteFromStudent(int pId) {
		setCon();
		try {
			PreparedStatement pSmt = null;
			String sql = "DELETE FROM Student WHERE sId =" + pId;
			pSmt = conn.prepareStatement(sql);
			pSmt.execute();
		} catch (SQLException e) {
			System.out.println("Delete from student konnte nicht ausgeführt werden");
			e.printStackTrace();
		}
		closeCon();

	}

	/**
	 * getAlleNcs:
	 * Returns the NCs of all candidates for a major. 
	 * @param studiengang enum of a major
	 * @return LinkedList with all NCs from the candidates for a major.
	 */
	public LinkedList<Double> getAlleNcs(Studiengang studiengang) {
		setCon();

		ResultSet rsNcs = null;
		PreparedStatement pSmt = null;
		String sql = "SELECT nc FROM Bewerber INNER JOIN Person on pid = bid WHERE studiengang = \""
				+ studiengang.toString() + "\"";
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

	/**
	 * getCandidatesWithInsufficientGrades:
	 * returns a LinkedList with candidates who have a NC higher than the last wich got admission.
	 * @param lastNcForAdmission
	 * @param studiengang
	 * @return LinkedList with candidates who are too bad for the major.
	 */
	public LinkedList<Bewerber> getCandidatesWithInsufficientGrades(Double lastNcForAdmission,
			Studiengang studiengang) {

		setCon();
		LinkedList<Bewerber> listOfCandidatesWithInsufficientGrades = new LinkedList<Bewerber>();
		Bewerber candidate = null;
		ResultSet rs = null;
		PreparedStatement pSmt = null;
		String sql = "SELECT * FROM Person INNER JOIN Bewerber on pid = bid WHERE nc > " + lastNcForAdmission
				+ "AND studiengang = \"" + studiengang.toString() + "\"";

		try {
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
					Studiengang studienfach = Studiengang.valueOf(rs.getString("studiengang"));
					double haertefall = rs.getDouble("haertefall");
					Double nc = rs.getDouble("nc");
					SemesterbeitragBezahlt semesterbeitragBezahlt = SemesterbeitragBezahlt
							.valueOf(rs.getString("semesterbeitragBezahlt"));

					candidate = new Bewerber(anrede, name, vorname, geburtsdatum, pid, telefonnummer, eMail,
							studienfach, haertefall, nc, semesterbeitragBezahlt);

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
