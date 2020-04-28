package board;

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
	static MusicVO name;
	static int pre;
	int length = 0;
	
	public String Name() {
		return Name();
	}
	
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
	public Vector<MusicVO> getMusicList(int add){
		Vector<MusicVO> vecList = new Vector<MusicVO>();
		String sql = "select * from musicTBL";
		
		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();){
			
			while(rs.next()) {
				MusicVO vo = new MusicVO();
				
				vo.setTitle((rs.getString(2)));
			
				vecList.add(vo);
			}
			
			length = vecList.size();
			if(add>=length) {
				add=0;
			}else if(add<0) {
				add=length -1;
			}
			pre = add;
			name = vecList.get(add);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vecList;
		
	}
	
	public int Random() {
		java.util.Random random = new java.util.Random();
		
		int[] check = new int[length];
		int adder = 0;
		
		for(int r=0; r<length; r++) {
			check[r] = random.nextInt(length);
			
			for(int i=0; i<r; i++) {
				if(check[i]==check[r]) {
					check[r] = random.nextInt(length);
				}
			}
			adder = check[length-1];
		}
		return adder;
	}

}