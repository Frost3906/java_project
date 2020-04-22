package board;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Board extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;
	private JTextField txt_search;
	private DefaultTableModel model;
	private JPanel panel_1;
	private int a=1;
	private JButton btn_read;
	private JButton btn_search;
	private JButton btn_write;
	private JComboBox cbox;
	private JButton btn_refresh;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Board frame = new Board();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Board() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("게시판");
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		 cbox = new JComboBox();
		cbox.setModel(new DefaultComboBoxModel(new String[] {"제목", "글내용", "작성자"}));
		panel.add(cbox);
		
		txt_search = new JTextField();
		panel.add(txt_search);
		txt_search.setColumns(20);
		
		 btn_search = new JButton("검색");
		panel.add(btn_search);
		
		JLabel lblNewLabel_1 = new JLabel("                    ");
		panel.add(lblNewLabel_1);
		
		 btn_write = new JButton("글 쓰기");
		btn_write.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btn_write);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_2 = new JLabel("1");
		panel_1.add(lblNewLabel_2);
		
		btn_read = new JButton("글보기");
		btn_read.setHorizontalAlignment(SwingConstants.RIGHT);
		btn_read.addActionListener(this);
		panel_1.add(btn_read);
		
		btn_refresh = new JButton("새로고침");
		btn_refresh.addActionListener(this);
		panel_1.add(btn_refresh);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		String columnName[]= {"게시글","작성자","게시번호","작성일","조회수"};
		model = new DefaultTableModel(columnName,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.setFont(new Font("굴림", Font.PLAIN, 15));
		table.getColumnModel().getColumn(0).setPreferredWidth(800);  //JTable 의 컬럼 길이 조절
		table.setRowHeight(22);

		scrollPane.setViewportView(table);
		btn_search.addActionListener(this);
		btn_write.addActionListener(this);

		addview();
		setVisible(true);
		
	}
		
		public void addview() {
			BoardDAO dao = new BoardDAO();
			model = (DefaultTableModel) table.getModel();
			Vector<BoardVO> vec = dao.ViewBoard();
			
			for(BoardVO vo:vec) {
				Object[] objlist = {vo.getContentname(),vo.getContent(),vo.getBoardno()};
				model.addRow(objlist);

//				if(model.getRowCount()>a*15) {
//					a+=1;
//					panel_1.add(new JLabel (a+""));
//					
//				}
				
			}
			
			
		}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		BoardDAO dao = new BoardDAO();
		JButton btn = (JButton) e.getSource();
		
		if(btn == btn_read) {
			BoardVO vo = new BoardVO();
			int rownum = table.getSelectedRow();
			Object value = table.getValueAt(rownum, 2);
			
			String a = value.toString();
			System.out.println(a);
			
			int boardno = Integer.parseInt(a);
			BoardViewContent bvc = new BoardViewContent();
			bvc.textfill(boardno);
			bvc.show();
			
		}
		
		if(btn==btn_search) {
			Vector<BoardVO> vec = new Vector<>();
			
//				model = (DefaultTableModel) table.getModel();
			
			
			if(cbox.getSelectedItem().equals("제목")) {
				String c = "contentname";
				String s = txt_search.getText();
				model.setNumRows(0);
			vec=dao.Search(c, s);
			for(BoardVO vo:vec) {
				Object[] objlist = {vo.getContentname(),vo.getContent(),vo.getBoardno()};
				model.addRow(objlist);
			}
			
			
			
			}else if(cbox.getSelectedItem().equals("글내용")) {
				String c = "content";
				String s = txt_search.getText();
				model.setNumRows(0);
			vec=dao.Search(c, s);
			for(BoardVO vo:vec) {
				Object[] objlist = {vo.getContentname(),vo.getContent(),vo.getBoardno()};
				model.addRow(objlist);
			}
			}
			
			
		}
		
		if(btn==btn_write) {
			BoardWrite bw = new BoardWrite();
			bw.show();
		}
		if(btn==btn_refresh) {
			refresh();
		}
		
		
	}
	
	public void refresh() {
		Vector<BoardVO> vec = new Vector<>();
		model.setNumRows(0);
		BoardDAO dao = new BoardDAO();
		
		vec=dao.refresh();
		for(BoardVO vo:vec) {
			Object[] objlist = {vo.getContentname(),vo.getContent(),vo.getBoardno()};
			model.addRow(objlist);
		}
		
	}
	
	
}
