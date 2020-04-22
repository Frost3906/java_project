package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import login.LoginVO;



public class MusicDAO {


	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection() {
		String url = "jdbc:mysql://projectdb.cdiwkaggifue.ap-northeast-2.rds.amazonaws.com:3306/projectdb?serverTimezone=UTC&characterEncoding=UTF-8";
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
	public int upload(MusicVO vo) {
		// 회원가입에 필요한 정보 입력 후 primary(name , email) 이 중복되지 않을 시 int값 반환
		String sql = "insert into musicTBL values (?,?)";
		int result=0;
		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setBlob(1, vo.getBlob());
			pstmt.setString(2, vo.getTitle());
			
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}



}