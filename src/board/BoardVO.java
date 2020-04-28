package board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {
	
	private int boardno,viewcount,count;
	private String content;
	private String contentname;
	private String writer,writedate,comment,commentwriter,id;
	

}
