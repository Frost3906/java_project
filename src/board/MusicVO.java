package board;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.cj.jdbc.Blob;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MusicVO {
	
	private Blob blob;
	 private String title;
	 private byte[] buff;
	
	 
	 




	public Blob getBlob() {
		return blob;
	}







	public void setBlob(Blob blob) {
		this.blob = blob;
	}







	public String getTitle() {
		return title;
	}







	public void setTitle(String title) {
		this.title = title;
	}







	public byte[] getBuff() {
		return buff;
	}







	public void setBuff(byte[] buff) {
		this.buff = buff;
	}







	@Override
	public String toString() {
		return title;
	}
	
}
