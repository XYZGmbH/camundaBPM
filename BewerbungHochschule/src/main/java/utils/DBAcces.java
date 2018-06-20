package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import domain.Bewerber;

public class DBAcces{
	
	//Constructor
	private DBAcces() {
		
	}

	//Objects
	private static DBAcces exemplar = null;
	private Connection conn = null;
	
	//methods
	//String name, String vorname, int alter, int pid, String telefonnummer, String email,
	//Studiengang studiengang, Haertefall haertefall, int nc
	public LinkedList<Bewerber> getBewerber(){
		setCon();
		
		String sql = "select * from 'Bewerber'";
		
		try(Statement s = conn.createStatement()) {
			try(ResultSet rs = s.executeQuery(sql)){
				LinkedList<Bewerber> bewerberListe = new LinkedList<Bewerber>();
				while(rs.next()) {
					int bid = rs.getInt("PID");
					ResultSet personAtr = this.getPerson(bid);
					Bewerber b = new Bewerber(personAtr.getString(2), personAtr.getString(3),
							personAtr.getInt(4), personAtr.getInt(1), personAtr.getString(5), 
							personAtr.getString(6), Studiengang.valueOf(personAtr.getString(7)), null, 0);
					
					if(personAtr.getObject(8) != null) {
						
					}
					bewerberListe.add(b);
					
				}
			}catch(SQLException e) {
				
			}
		}catch(SQLException e2) {
			
		}
		
		closeCon();
		return null;
	}
	
	public ResultSet getPerson(int pid) {
		
		String sql = "select * from 'Person' where 'pid' = ?";
		ResultSet rsReal = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, pid);
			try(ResultSet rs = ps.executeQuery(sql)){
				
				rsReal = rs;
				
			}catch(SQLException e) {
				
			}
		}catch(SQLException e2) {
			
		}		
		return rsReal;
	}
	
	public void insertIntoStudent(String matrikelNummer, SemesterbeitragBezahlt beitragBezahlt) {
		setCon();
		
		String sql = "insert into student (SID, Matrikelnummer, SemesterbeitragBezahlt) values (?,?,?)";
		
		try(PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, this.getPid());
			ps.setString(2, matrikelNummer);
			ps.setString(3, beitragBezahlt.toString());
			
			try {
				ps.executeUpdate();
				System.out.println("Student inserted");
			}catch(SQLException e1) {
				System.out.println("Unable to execute update");
				e1.printStackTrace();
			}
			
			
		} catch (SQLException e) {
			System.out.println("Unable to prepare Statement");
			e.printStackTrace();
		}
		
		closeCon();
	}
	
	public void insertIntoBewerber(String haertefall, int nc) {
		setCon();
		
		String sql = "insert into bewerber (BID, Haertefall, NC) "
				+ "values (?,?,?)";
		
		try(PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, this.getPid());
			ps.setString(2, haertefall);
			ps.setInt(3, nc);
			
			try {
				ps.executeUpdate();
				System.out.println("Bewerber inserted");
			}catch(SQLException e1) {
				System.out.println("Unable to execute update");
				e1.printStackTrace();
			}
			
			
		} catch (SQLException e) {
			System.out.println("Unable to prepare Statement");
			e.printStackTrace();
		}
		
		closeCon();
	}
	
	public void insertIntoPerson(String name, String vorname, int alter, String telefonnummer, String email, Studiengang studiengang) {
		setCon();
		String sql = "insert into person (Name, Vorname, alterJahre, Telefonnummer, EmailAdresse, Studiengang) "
				+ "values (?,?,?,?,?,?)";
		
		try(PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, name);
			ps.setString(2, vorname);
			ps.setInt(3, alter);
			ps.setString(4, telefonnummer);
			ps.setString(5, email);
			ps.setString(6, studiengang.toString());
			
			try {
				ps.executeUpdate();
				System.out.println("person inserted");
			}catch(SQLException e1) {
				System.out.println("Unable to execute update");
				e1.printStackTrace();
			}
			
			
		} catch (SQLException e) {
			System.out.println("Unable to prepare Statement");
			e.printStackTrace();
		}
		
		
		closeCon();
	}
	
	public void insertIntoAdresse(String Adresszeile1, String Adresszeile2, String plz, String ort) {
		setCon();
		
		String sql = "insert into Adresse (Adresszeile1, Adresszeile2, plz, ort) values (?,?,?,?)";
		
		try(PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, Adresszeile1);
			ps.setString(2, Adresszeile2);
			ps.setString(3, plz);
			ps.setString(4, ort);
			
			try {
				ps.executeUpdate();
				System.out.println("Adresse inserted");
			}catch(SQLException e1) {
				System.out.println("Unable to execute update");
				e1.printStackTrace();
			}
			
			
		} catch (SQLException e) {
			System.out.println("Unable to prepare Statement");
			e.printStackTrace();
		}
		
		closeCon();
	}
	
	private int getPid() {
		
		String sql = "select max(pid) from 'person'";
		
		try(Statement s = conn.createStatement()) {			
			try(ResultSet rs = s.executeQuery(sql)) {
			return rs.getInt(1);
			
			}catch(SQLException e1) {
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
		}catch(Exception e) {
			System.out.println("Unable to load Driver\n");
			e.printStackTrace();
		}
		
		String url ="jdbc:mysql://" + DBCred.HOSTNAME + ":" + DBCred.PORT + "/" + DBCred.DBNAME; 
		
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
	
	public static DBAcces getInstance() {
		if(exemplar == null) {
			exemplar = new DBAcces();
		}
		return exemplar;
	}
	
}
