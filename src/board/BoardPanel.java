package board;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
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
import ui.CircleButton;
import ui.RoundedButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import java.awt.FlowLayout;

public class BoardPanel extends JPanel implements ActionListener{
	

	private JTable table;
	private JPanel board_HeadPanel, board_FooterPanel;
	private JScrollPane board_contentsPanel;
	private JButton btn_search, btn_write, btn_read, btn_refresh;
	private DefaultTableModel model;
	private JTextField txt_search;
	private JInternalFrame bw;
	
	private JComboBox cbox;
	private JLabel la_id;
	private LoginVO voo;
	private JLabel lblNewLabel;
	
	

	
	public BoardPanel(LoginVO vo) {
		this.voo=vo;
		setBounds(12, 120, 670, 400);
		setLayout(null);
		
		board_HeadPanel = new JPanel();
		board_HeadPanel.setBackground(new Color(255, 240, 245));
		board_HeadPanel.setBounds(0, 0, 670, 33);
		add(board_HeadPanel);
		board_HeadPanel.setLayout(null);
		
		btn_write = new CircleButton("글쓰기");
		btn_write.setBounds(9, 5, 88, 23);
		btn_write.setFont(new Font("굴림", Font.BOLD, 12));
		btn_write.setBackground(new Color(70, 130, 180));
		btn_write.setForeground(new Color(255, 240, 245));
		board_HeadPanel.add(btn_write);
		btn_write.addActionListener(this);
		

		cbox = new JComboBox();
		cbox.setBounds(108, 6, 72, 21);
		cbox.setModel(new DefaultComboBoxModel(new String[] {"제목", "글내용", "작성자"}));
		board_HeadPanel.add(cbox);
		
		txt_search = new JTextField();
		txt_search.setBounds(188, 6, 226, 21);
		txt_search.addActionListener(this);
		txt_search.setColumns(20);
		board_HeadPanel.add(txt_search);
		
		btn_search = new  RoundedButton("검색");
		btn_search.setBounds(419, 5, 57, 23);
		btn_search.setForeground(new Color(255, 240, 245));
		btn_search.setBackground(new Color(119, 136, 153));
		board_HeadPanel.add(btn_search);
		
		la_id = new JLabel(voo.getId());
		la_id.setFont(new Font("굴림", Font.BOLD, 10));
		la_id.setBounds(488, 10, 65, 15);
		board_HeadPanel.add(la_id);
		
		lblNewLabel = new JLabel("님 환영합니다.");
		lblNewLabel.setBounds(554, 9, 104, 15);
		board_HeadPanel.add(lblNewLabel);

//		============================================================================
		
	    board_FooterPanel = new JPanel();
	    board_FooterPanel.setBackground(new Color(255, 240, 245));
	    board_FooterPanel.setBounds(0, 363, 670, 37);
		add(board_FooterPanel);
			
		btn_read = new  RoundedButton("글보기");
		btn_read.setForeground(new Color(255, 240, 245));
		btn_read.setFont(new Font("굴림", Font.BOLD, 15));
		btn_read.setBackground(new Color(70, 130, 180));
		btn_read.addActionListener(this);
		btn_read.setHorizontalAlignment(SwingConstants.RIGHT);
		board_FooterPanel.add(btn_read);
		
		btn_refresh = new RoundedButton("새로고침");
		btn_refresh.setFont(new Font("굴림", Font.BOLD, 15));
		btn_refresh.setForeground(new Color(255, 240, 245));
		btn_refresh.setBackground(new Color(70, 130, 180));
		btn_refresh.addActionListener(this);
		board_FooterPanel.add(btn_refresh);
		
//		============================================================================
		
		board_contentsPanel = new JScrollPane();
		board_contentsPanel.setBounds(0, 33, 670, 330);
		
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
		table.getColumnModel().getColumn(0).setPreferredWidth(400);  //JTable 의 컬럼 길이 조절
		table.getColumnModel().getColumn(2).setPreferredWidth(100);  //JTable 의 컬럼 길이 조절
		table.getColumnModel().getColumn(3).setPreferredWidth(120);  //JTable 의 컬럼 길이 조절
		table.setRowHeight(22);
		
		btn_search.addActionListener(this);
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
		int result;
		
//		for(BoardVO vo:vec) {
//		}
		
		
		for(BoardVO vo:vec) {
			BoardVO bvo = dao.commentcount(vo.getBoardno());
			result = bvo.getCount();
			if(result != 0) {
			
			Object[] objlist = {vo.getContentname()+" ("+result+")",vo.getWriter(),vo.getBoardno(),vo.getWritedate(),vo.getViewcount()};
			model.addRow(objlist);
			
			}else {
				Object[] objlist = {vo.getContentname(),vo.getWriter(),vo.getBoardno(),vo.getWritedate(),vo.getViewcount()};
				model.addRow(objlist);}
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
			bw.addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent e) {
					
				}
				@Override
				public void windowIconified(WindowEvent e) {
				}
				
				@Override
				public void windowDeiconified(WindowEvent e) {
				}
				@Override
				public void windowDeactivated(WindowEvent e) {
					
				}
				@Override
				public void windowClosing(WindowEvent e) {
					refresh();
				}
				@Override
				public void windowClosed(WindowEvent e) {
					refresh();
				}
				@Override
				public void windowActivated(WindowEvent arg0) {
				}
			});
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
		BoardViewContent bvp = new BoardViewContent(voo);
		bvp.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				refresh();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				refresh();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		String idval = la_id.getText();
		
		
		if(idval2.equals(la_id.getText())) {
			bvp.textfill(boardno);
			bvp.show();
			int c = Integer.parseInt(a);
			dao.viewcount(b, c);
		}else {
			bvp.unvi();
			bvp.unvi();
			bvp.textfill(boardno);
			bvp.show();
			int c = Integer.parseInt(a);
			dao.viewcount(b, c);
			
		}
		
	}
	
	public BoardVO countcomment(int boardno) {
		BoardVO bvo = new BoardVO();
		BoardDAO dao = new BoardDAO();
		int result=0;
		bvo = dao.commentcount(boardno);
		return bvo;
		
	}

}
