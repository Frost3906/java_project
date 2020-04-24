package board;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import login.LoginVO;

import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTextArea;

public class Board_panelver extends JFrame implements ActionListener{

	private JPanel contentPane, playerPanel;
	private BoardPanel boardPanel;
	private JTextField textField;
	private JButton btn_ListOpen, btn_ListClose, btn_pre, btn_next, btn_loop, btn_open, btn_del, btn_upload;
	private JButton btn_write, btn_search, btn_read, btn_refresh;
	private JToggleButton btn_play;
	private JTextField textField_1;
	private JTable table;
	private JTextField txt_search;
	private DefaultTableModel model;
	
	private JComboBox cbox;
	private JLabel la_id;
	private LoginVO voo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Board_panelver frame = new Board_panelver();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Board_panelver() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 595);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*보드 패널 생성*/
		boardPanel = new BoardPanel();
		boardPanel.setLoginID(voo);
		contentPane.add(boardPanel);
		
		playerPanel = new JPanel();
		playerPanel.setLayout(null);
		playerPanel.setBounds(12, 10, 884, 34);
		contentPane.add(playerPanel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(12, 5, 251, 23);
		playerPanel.add(textField);
		
		btn_pre = new JButton("◁");
		btn_pre.setBounds(275, 5, 45, 23);
		playerPanel.add(btn_pre);
		
		btn_play = new JToggleButton();
		btn_play.setText("PLAY / STOP");
		btn_play.setBounds(332, 5, 140, 23);
		playerPanel.add(btn_play);
		
		btn_next = new JButton("▷");
		btn_next.setBounds(484, 5, 45, 23);
		playerPanel.add(btn_next);
		
		btn_loop = new JButton("∞");
		btn_loop.setFont(new Font("굴림", Font.PLAIN, 15));
		btn_loop.setBounds(541, 5, 45, 23);
		playerPanel.add(btn_loop);
		
		btn_ListOpen = new JButton(">>");
		btn_ListOpen.addActionListener(this); 	
		btn_ListOpen.setBounds(815, 5, 57, 23);
		playerPanel.add(btn_ListOpen);
		
		
		btn_ListClose = new JButton("<<");
		btn_ListClose.addActionListener(this);
		
		btn_ListClose.setBounds(815, 5, 57, 23);
		playerPanel.add(btn_ListClose);
		
		btn_open = new JButton("Open");
		btn_open.setBounds(598, 5, 68, 23);
		playerPanel.add(btn_open);
		
		btn_del = new JButton("Delete");
		btn_del.setBounds(680, 5, 123, 23);
		playerPanel.add(btn_del);
		btn_ListClose.setVisible(false);
		
		JScrollPane listPane = new JScrollPane();
		listPane.setBounds(905, 10, 125, 540);
		contentPane.add(listPane);
		
		btn_upload = new JButton("UpLoad");
		listPane.setColumnHeaderView(btn_upload);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		BoardDAO dao = new BoardDAO();
		
	
		
		if(e.getSource()==btn_ListOpen) {		
			setSize(1056, 600);
			btn_ListOpen.setVisible(false);
			btn_ListClose.setVisible(true);
		}
		else if(e.getSource()==btn_ListClose) {
			setSize(910, 600);
			btn_ListOpen.setVisible(true);
			btn_ListClose.setVisible(false);
		}
		
	}
	public void getvo(LoginVO vo) {
		this.voo = vo;
		
	}


	
	
}
