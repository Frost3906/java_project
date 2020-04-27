package board;

import java.io.File;

import com.mysql.cj.jdbc.Blob;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ListVO {
	
	private String musicname;
	private String path;
}
