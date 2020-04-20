package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class LoginDAO {

	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "javadb";
		String password = "12345";
		Connection con = null;
	
		try {
			con = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public int regUser(RegisterVO vo) {
		
		String sql = "insert into userTBL_p values(?,?,?,?)";
		int result = 0;
		try (Connection con = getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)){
				
				pstmt.setString(1, vo.getId());
				pstmt.setString(2, vo.getPassword());
				pstmt.setString(3, vo.getName());
				pstmt.setString(4, vo.getEmail());
				
				result = pstmt.executeUpdate();
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
}
