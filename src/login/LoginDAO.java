package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	
	
	public LoginVO login(String id,String passwd) {
		LoginVO vo = null;
		
		String sql = "select name from reg where id like ? and passwd like ?";
		
		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new LoginVO();
				vo.setName(rs.getString("name"));
//				vo.setEmail(rs.getString("email"));
//				vo.setId(rs.getString("id"));
//				vo.setPasswd(rs.getString("passwd"));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return vo;
	}
	
	public int reg(LoginVO vo) {
		// 회원가입에 필요한 정보 입력 후 primary(name , email) 이 중복되지 않을 시 int값 반환
		String sql = "insert into reg values(?,?,?,?)";
		int result=0;
		
		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getId());
			pstmt.setString(4, vo.getPasswd());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
		}
		
		return result;
		
	}
	
	
	
	
	
	
	
}
