package main;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.cj.jdbc.Blob;

import login.LoginVO;



public class MusicDAO {

	int i=0;

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
	public Vector<MusicVO> getMusicList(){
		Vector<MusicVO> vecList = new Vector<MusicVO>();
		String sql = "select * from musicTBL";
		
		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();){
			
			while(rs.next()) {
				MusicVO vo = new MusicVO();
				
				Blob blob = (Blob) rs.getBlob(1);
                InputStream inputStream = blob.getBinaryStream();
                OutputStream outputStream = new FileOutputStream("C:\\Users\\admin\\Desktop\\"+(i++)+".mp3");
 
                int bytesRead = -1;
                byte[] buffer = new byte[1024];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
 
                inputStream.close();
                outputStream.close();


				
				
				vo.setTitle((rs.getString(2)));
			
				vecList.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vecList;
		
	}
	
	



}