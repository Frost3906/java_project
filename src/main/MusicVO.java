package main;

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
	@Override
	public String toString() {
		return title;
	}
	
}
