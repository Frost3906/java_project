package board;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import login.LoginVO;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class BoardPanel extends JPanel implements ActionListener{
	

	private JTable table;
	private JPanel board_HeadPanel, board_FooterPanel;
	private JScrollPane board_contentsPanel;
	private JButton btn_search, btn_write, btn_read, btn_refresh;
	private DefaultTableModel model;
	private JTextField txt_search;
	
	private JComboBox cbox;
	private JLabel la_id;
	private LoginVO voo;
	
	

	
	public BoardPanel(LoginVO vo) {
		this.voo=vo;
		setBounds(12, 54, 884, 496);
		setLayout(new BorderLayout(0, 0));
		
		board_HeadPanel = new JPanel();
		add(board_HeadPanel, BorderLayout.NORTH);
		

		cbox = new JComboBox();
		cbox.setModel(new DefaultComboBoxModel(new String[] {"제목", "글내용", "작성자"}));
		board_HeadPanel.add(cbox);
		
		txt_search = new JTextField();
		txt_search.addActionListener(this);
		txt_search.setColumns(20);
		board_HeadPanel.add(txt_search);
		
		btn_search = new JButton("검색");
		board_HeadPanel.add(btn_search);
		
		JLabel lblNewLabel_1 = new JLabel("                    ");
		board_HeadPanel.add(lblNewLabel_1);
		
		btn_write = new JButton("글 쓰기");
		btn_write.setHorizontalAlignment(SwingConstants.RIGHT);
		board_HeadPanel.add(btn_write);
		
		la_id = new JLabel(voo.getId());
		board_HeadPanel.add(la_id);

		
	    board_FooterPanel = new JPanel();
		add(board_FooterPanel, BorderLayout.SOUTH);
		
		
		
		btn_read = new JButton("글보기");
		btn_read.addActionListener(this);
		btn_read.setHorizontalAlignment(SwingConstants.RIGHT);
		board_FooterPanel.add(btn_read);
		
		btn_refresh = new JButton("새로고침");
		btn_refresh.addActionListener(this);
		board_FooterPanel.add(btn_refresh);
		
	 board_contentsPanel = new JScrollPane();
		add(board_contentsPanel, BorderLayout.CENTER);
		
		
		
		String columnName[]= {"게시글","작성자","게시번호","작성일","조회수"};
		model = new DefaultTableModel(columnName,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		table.setFont(new Font("굴림", Font.PLAIN, 15));
		board_contentsPanel.setViewportView(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(800);  //JTable 의 컬럼 길이 조절
		table.getColumnModel().getColumn(3).setPreferredWidth(120);  //JTable 의 컬럼 길이 조절
		table.setRowHeight(22);
		
		btn_search.addActionListener(this);
		btn_write.addActionListener(this);
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount()==2) {
					view();
					
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		
		addview();
		setVisible(true);
	}
	
	public void addview() { // 게시판 보이기
		BoardDAO dao = new BoardDAO();
		model = (DefaultTableModel) table.getModel();
		Vector<BoardVO> vec = dao.ViewBoard();
		
		for(BoardVO vo:vec) {
			Object[] objlist = {vo.getContentname(),vo.getWriter(),vo.getBoardno(),vo.getWritedate(),vo.getViewcount()};
			model.addRow(objlist);		
		}		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		BoardDAO dao = new BoardDAO();
		
		if(e.getSource() == btn_read) {   //  글 보기	
			view();
		}
		
		else if(e.getSource()==btn_search || e.getSource()==txt_search) {  //  글 검색
			Vector<BoardVO> vec = new Vector<>();			
//				model = (DefaultTableModel) table.getModel();					
			if(cbox.getSelectedItem().equals("제목")) {
				String txt = txt_search.getText();			
				model.setNumRows(0);
				vec=dao.Searchcontentname(txt);
				for(BoardVO vo:vec) {
					Object[] objlist = {vo.getContentname(),vo.getWriter(),vo.getBoardno(),vo.getWritedate(),vo.getViewcount()};
					model.addRow(objlist);
				}
			}
			else if(cbox.getSelectedItem().equals("글내용")) {
				String txt = txt_search.getText();
				model.setNumRows(0);
				vec=dao.Searchcontent(txt);
				for(BoardVO vo:vec) {
					Object[] objlist = {vo.getContentname(),vo.getWriter(),vo.getBoardno(),vo.getWritedate(),vo.getViewcount()};
					model.addRow(objlist);
				}
			}else if(cbox.getSelectedItem().equals("작성자")) {
				String txt = txt_search.getText();
				model.setNumRows(0);
				vec=dao.Searchwriter(txt);
				for(BoardVO vo:vec) {
					Object[] objlist = {vo.getContentname(),vo.getWriter(),vo.getBoardno(),vo.getWritedate(),vo.getViewcount()};
					model.addRow(objlist);
				}
			}
						
		}
		else if(e.getSource()==btn_write) {   //  글 쓰기
			BoardWrite bw = new BoardWrite(voo);
			bw.show();
			
			
		}
				
		else if(e.getSource()==btn_refresh) {   //  새로고침
			refresh();
		}	
		
		
		
		
	}
	public void refresh() {
		model.setNumRows(0);
		addview();
	}
	
	public void getrs(int rs) {
		if(rs!=0) {
			refresh();
		}
	}

	
	
	
	public void view() {
		BoardDAO dao = new BoardDAO();
		BoardVO bo = new BoardVO();
		int rownum = table.getSelectedRow();
		Object value = table.getValueAt(rownum, 2);  // boardno 값
		Object value2 = table.getValueAt(rownum, 4);  // viewcount 값
		Object value3 = table.getValueAt(rownum, 1);  // 작성자id 값
		
		int b = Integer.parseInt(value2.toString());
		String a = value.toString();
		String idval2 = value3.toString();
		
		
		
		b+=1;
		int boardno = Integer.parseInt(a);
		BoardViewPanel bvp = new BoardViewPanel();
		String idval = la_id.getText();
		
		
		if(idval2.equals(la_id.getText())) {
			bvp.getvo(voo);
			bvp.textfill(boardno);
			bvp.show();
			int c = Integer.parseInt(a);
			dao.viewcount(b, c);
			System.out.println(la_id.getText());
			System.out.println(idval2);
		}else {
			bvp.unvi();
			System.out.println(la_id.getText());
			System.out.println(idval2);
			bvp.unvi();
			bvp.getvo(voo);
			bvp.textfill(boardno);
			bvp.show();
			int c = Integer.parseInt(a);
			dao.viewcount(b, c);
			
		}
		
	}

}
