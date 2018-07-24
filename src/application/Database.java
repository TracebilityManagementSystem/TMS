package application;
import java.sql.*;
import java.util.Date;

public class Database {
	
	private Connection conn;
	
	public Database(Connection conn) {
		this.conn = conn;
	}
	
	void insertArtifact(String uri, int vertionId, int charId, int consId, String title, String description) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Artifact (Art_uri, VersionId, CharId, ConsId, Title, Description) VALUES (?, ?, ?, ?, ?, ?) ");
			stmt.setString(1, uri);
			stmt.setInt(2, vertionId);
			stmt.setInt(3, charId);
			stmt.setInt(4, consId);
			stmt.setString(5, title);
			stmt.setString(6, description);
			stmt.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void insertTracelink(int linkId, String sourceArtifact, String targetArtifact) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tracelink (LinkId, sourceArtifact, targetArtifact) VALUES ( ?, ?, ?) ");
			stmt.setInt(1, linkId);
			stmt.setString(2, sourceArtifact);
			stmt.setString(3, targetArtifact);
			stmt.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void insertVersion(String owner, String modi_by, Date modi_date) {
		Timestamp modi_on = new Timestamp(modi_date.getTime());
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Version (Owner, Modi_by, Modi_on) VALUES ( ?, ?, ?) ");
			stmt.setString(1, owner);
			stmt.setString(2, modi_by);
			stmt.setTimestamp(3, modi_on);
			stmt.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
