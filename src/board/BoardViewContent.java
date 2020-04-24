package board;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import login.LoginVO;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BoardViewContent extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btn_cancel;
	private JTextField txt_main;
	private JTextArea txt_content;
	private JButton btn_rewrite;
	private JLabel la_boardno;
	private JPanel panel_1;
	private JButton btn_endwrite = new JButton("수정 완료");
	private JButton btn_delete;
	private JLabel la_id = new JLabel("접속자 id");
	private JLabel lblNewLabel_5;
	private JLabel la_id2;
	private JTable table;
	private JTable table_1;
	private DefaultTableModel model;
	private JButton btn_con;
	private JTextArea txt_con;
	private JLabel la_conid;
	private JButton btn_conview;
	private JButton  btn_concancel;
	private JScrollPane scrollPane;
	private LoginVO vo;


	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardViewContent frame = new BoardViewContent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BoardViewContent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("게시판");
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 874, 31);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("글제목 : ");
		panel.add(lblNewLabel);
		
		txt_main = new JTextField();
		panel.add(txt_main);
		txt_main.setColumns(32);
		
		JLabel lblNewLabel_4 = new JLabel("                               ");
		panel.add(lblNewLabel_4);
		
		 la_boardno = new JLabel("New label");
		panel.add(la_boardno);
		
		 panel_1 = new JPanel();
		 panel_1.setBounds(5, 374, 874, 33);
		contentPane.add(panel_1);
		
		 btn_cancel = new JButton("뒤로가기");
		 btn_cancel.addActionListener(this);
		 
		 btn_concancel = new JButton("댓글 접기");
		 btn_concancel.addActionListener(this);
		btn_concancel.setVisible(false);
		
		 btn_conview = new JButton("댓글 보기");
		 panel_1.add(btn_concancel);
		panel_1.add(btn_conview);
		panel_1.add(btn_cancel);
		
		JLabel lblNewLabel_3 = new JLabel("                         ");
		panel_1.add(lblNewLabel_3);
		
		 btn_rewrite = new JButton("글 수정");
		panel_1.add(btn_rewrite);
		
		btn_delete = new JButton("글 삭제");
		panel_1.add(btn_delete);
		btn_rewrite.addActionListener(this);
		
		JLabel lblNewLabel_1 = new JLabel("글내용");
		lblNewLabel_1.setBounds(5, 36, 36, 338);
		contentPane.add(lblNewLabel_1);
		
		 txt_content = new JTextArea();
		 txt_content.setBounds(41, 36, 838, 338);
		contentPane.add(txt_content);
		txt_content.setEditable(false);
		txt_main.setEditable(false);
		
		lblNewLabel_5 = new JLabel("작성자 : ");
		panel.add(lblNewLabel_5);
		
		la_id2 = new JLabel("id");
		panel.add(la_id2);
		
		 scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 411, 874, 22);
		
		
//		setBounds(100, 100, 900, 552);  --  contentPane의 크기
//		scrollPane.setBounds(5, 411, 874, 52);  -- scrollPane의 위치 및 크기
//		txt_con.setBounds(77, 461, 715, 50); txtcon(댓글입력창) 의 위치 및 크기
//		btn_con.setBounds(794, 461, 85, 50); btncon(댓글작성버튼) 의 위치 및 크기
//		la_conid.setBounds(5, 461, 70, 50); conid ( 댓글입력창 옆 아이디) 의 위치 및 크기

		
		String columnName[]= {"아이디","댓글","작성날짜"};
		table = new JTable();
		model = new DefaultTableModel(columnName,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		
		
		
		
		table_1 = new JTable(model);
		table_1.setShowVerticalLines(false);
		scrollPane.setViewportView(table_1);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(77);  //JTable 의 컬럼 길이 조절
		table_1.getColumnModel().getColumn(1).setPreferredWidth(783);  //JTable 의 컬럼 길이 조절
		table_1.getColumnModel().getColumn(2).setPreferredWidth(140);  //JTable 의 컬럼 길이 조절
		table_1.setRowHeight(30);
		int comment = table_1.getRowCount();
		int size = comment*30;
		
		
		
		
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = table_1.getColumnModel();
		
		
		contentPane.add(scrollPane);
		
		
		 txt_con = new JTextArea();
		txt_con.setLineWrap(true);
		txt_con.setBounds(77, 461, 693, 50);
		contentPane.add(txt_con);
		
		 btn_con = new JButton("댓글작성");
		btn_con.setBounds(771, 461, 108, 50);
		contentPane.add(btn_con);
		
		 la_conid = new JLabel("id");
		la_conid.setBounds(5, 461, 70, 50);
		contentPane.add(la_conid);
		btn_endwrite.addActionListener(this);
		btn_delete.addActionListener(this);
		
		btn_con.addActionListener(this);
		btn_conview.addActionListener(this);
		
//		System.out.println(la_id.getText()+"접속자 id");
//		System.out.println(la_id2.getText()+"작성자 id");
		
		
		scrollPane.setVisible(false);
		btn_con.setVisible(false);
		txt_con.setVisible(false);
		la_conid.setVisible(false);
		
		panel_1.add(la_id);
		
		setVisible(true);
		
		
		
	}
	
	
	
	
	private void setRowHeight(int i) {
		
	}

	public void textfill(int boardno) {
		BoardVO vo = new BoardVO();
		BoardDAO dao = new BoardDAO();
		vo=dao.view(boardno);
		txt_main.setText(vo.getContentname());
		txt_content.setText(vo.getContent());
		la_boardno.setText(vo.getBoardno()+"");
		la_id2.setText(vo.getWriter());
		
		
		
		
		
		
//		txt_writer.setText(vo2.get());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn_conview) {

			
			
			addcomment();
			la_conid.setText(la_id.getText());
			
			scrollPane.setVisible(true);
			btn_con.setVisible(true);
			txt_con.setVisible(true);
			la_conid.setVisible(true);
			
			BoardVO vo = new BoardVO();
			BoardDAO dao = new BoardDAO();
			
			
			
			
			
			int comment = table_1.getRowCount();
			int size = comment*30;
			if(size>=240) {
				size=240;
			}
			setBounds(100, 100, 900, 522+size);
			txt_con.setBounds(77, 431+size, 715, 50);
			btn_con.setBounds(794, 431+size, 85, 50); 
			la_conid.setBounds(5, 431+size, 70, 50); 
			scrollPane.setBounds(5, 411, 874, 22+size);
			
			
			setVisible(true);
			btn_conview.setVisible(false);
			btn_concancel.setVisible(true);
			
			
		}
		if(btn == btn_concancel) { // 댓글접기 버튼
			btn_conview.setVisible(true);
			btn_concancel.setVisible(false);
			scrollPane.setVisible(false);
			btn_con.setVisible(false);
			txt_con.setVisible(false);
			la_conid.setVisible(false);
			setBounds(100, 100, 900, 450);
			model.setNumRows(0);
//			setVisible(true);
		}
		
		
		if(btn == btn_con) { // 댓글작성 버튼
			
			if(txt_con.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "댓글 내용을 입력 해 주세요.", "댓글입력", JOptionPane.WARNING_MESSAGE);
			}else {
				long time = System.currentTimeMillis(); 
				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String str = dayTime.format(new Date(time));
				BoardDAO dao = new BoardDAO();
				BoardVO vo2 = new BoardVO();
				vo2 = dao.gettime(vo2);
				Object[] obj = {la_conid.getText(),txt_con.getText(),vo2.getWritedate()};
				model.addRow(obj);			
				int comment = table_1.getRowCount();
				int size = comment*30;
				if(size>=240) {
					size=240;
				}
				setBounds(100, 100, 900, 522+size);
				txt_con.setBounds(77, 431+size, 715, 50);
				btn_con.setBounds(794, 431+size, 85, 50); 
				la_conid.setBounds(5, 431+size, 70, 50); 
				scrollPane.setBounds(5, 411, 874, 22+size);
				BoardVO vo = new BoardVO();
				
				int boardn = Integer.parseInt(la_boardno.getText());
				
				vo.setBoardno(boardn);
				vo.setCommentwriter(la_conid.getText());
				vo.setComment(txt_con.getText());
				dao.writecomment(vo);
				txt_con.setText("");
				JOptionPane.showMessageDialog(this, "댓글이 입력되었습니다.", "댓글입력", JOptionPane.INFORMATION_MESSAGE);
				
			}
			
			
		}
		
		
		
		if(btn == btn_cancel) { // 취소버튼
		Board board = new Board();
		board.refresh();
		board.getvo(vo);
		dispose();
		}
		
		
		if(btn==btn_rewrite) {
			if(la_id.getText().equals(la_id2.getText())==true) {
				
				// 수정버튼
				txt_content.setEditable(true);
				txt_main.setEditable(true);
				btn_rewrite.setVisible(false);
				panel_1.add(btn_endwrite);
				
			}else if(la_id.getText().equals(la_id2.getText())==false) {
				
				JOptionPane.showMessageDialog(this, "작성자만 글을 수정할 수 있습니다.", "게시글 수정취소",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		
		if(btn==btn_endwrite) { // 수정완료
			BoardVO bo = new BoardVO();
			BoardDAO dao = new BoardDAO();
		bo.setContent(txt_content.getText());
		bo.setContentname(txt_main.getText());
		bo.setBoardno(Integer.parseInt(la_boardno.getText()));
		int result = dao.modify(bo);
		if(result !=0) {
			JOptionPane.showMessageDialog(this, "글이 수정되었습니다.", "게시글 수정",JOptionPane.INFORMATION_MESSAGE);
			Board board = new Board();
			board.refresh();
			board.getvo(vo);
			
			dispose();
			
		}else {
			JOptionPane.showMessageDialog(this, "수정이 실패하였습니다.", "게시글 수정",JOptionPane.WARNING_MESSAGE);
		}
		txt_content.setEditable(false);
		txt_main.setEditable(false);
		btn_rewrite.setVisible(true);
		btn_endwrite.setVisible(false);
		}
		
		
		if(btn==btn_delete) { // 삭제버튼
			if(la_id.getText().equals(la_id2.getText())==true) {
				
				BoardVO vo = new BoardVO();
				BoardDAO dao = new BoardDAO();
				int no = Integer.parseInt(la_boardno.getText());
				
				String option[]= {"예","아니오",};
				int a = JOptionPane.showOptionDialog(this, "정말로 삭제하시겠습니까?", "게시글 삭제", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
				System.out.println(a);
				if(a==0) {
					int result = dao.delete(no);
					
					if(result !=0) {
						JOptionPane.showMessageDialog(this, "글이 삭제되었습니다.", "게시글 삭제",JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				}else {
					JOptionPane.showMessageDialog(this, "삭제를 취소하였습니다.", "게시글 삭제취소",JOptionPane.WARNING_MESSAGE);
				}
			}else if(la_id.getText().equals(la_id2.getText())==false) {
				JOptionPane.showMessageDialog(this, "작성자만 글을 삭제할 수 있습니다.", "게시글 삭제취소",JOptionPane.WARNING_MESSAGE);
				
			}
			
		}
		
	}
	

	
	public void addcomment() { // 댓글 보이기
		
		BoardDAO dao = new BoardDAO();
//		model = (DefaultTableModel) table.getModel();
		int boardno = Integer.parseInt(la_boardno.getText());
		Vector<BoardVO> vec = dao.commentSearch(boardno);
		
		for(BoardVO vo:vec) {
			Object[] objlist = {vo.getCommentwriter(),vo.getComment(),vo.getWritedate()};
			model.addRow(objlist);
			int comment = table_1.getRowCount();
			int size = comment*30;
			if(size>=240) {
				size=240;
			}
			setBounds(100, 100, 900, 522+size);
			txt_con.setBounds(77, 431+size, 715, 50);
			btn_con.setBounds(794, 431+size, 85, 50); 
			la_conid.setBounds(5, 431+size, 70, 50); 
			scrollPane.setBounds(5, 411, 874, 22+size);
			setVisible(true);
		
			
		}
	}
	
	public void unvi() {
		btn_delete.setVisible(false);
		btn_rewrite.setVisible(false);
	}
	
	
	public void getvo(LoginVO voo) {
		this.vo = voo;
		
		la_id.setText(vo.getId());
		
	}
	
	
}
