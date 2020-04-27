package board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.Blob;

import board.HaMelGomPot;
import login.LoginVO;

import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import main.*;

import javax.swing.JList;

public class Board_panelver extends JFrame implements ActionListener,MouseListener{

	private ImageIcon icon;
	private JPanel contentPane, playerPanel;
	private BoardPanel boardPanel;
	private JTextField textField;
	private JButton btn_ListOpen, btn_ListClose, btn_pre, btn_next, btn_open, btn_del, btn_upload;
	private JToggleButton btn_play;
	private ArrayList<String> MuList;
	private DefaultTableModel model;
	private String musicName;
	private HaMelGomPot ha;
	int a = 0;
	
	private static LoginVO voo;
	private JButton btn_stop;
	private JButton btn_pause;
	private JButton btn_play_n;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Board_panelver frame = new Board_panelver(voo);
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
	public Board_panelver(LoginVO vo) {
		setTitle("Music Player");
		
		icon = new ImageIcon(Board_panelver.class.getResource("intro_board.jpg"));
		
		voo=vo;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 700, 560);
		
		contentPane = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				Dimension d = getSize();
				g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
			}
		};
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// =============================================================
		
		/*보드 패널 생성*/
		boardPanel = new BoardPanel(voo);
		contentPane.add(boardPanel);
		
		playerPanel = new JPanel();
		playerPanel.setLayout(null);
		playerPanel.setBounds(12, 10, 669, 95);
		contentPane.add(playerPanel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(12, 5, 251, 23);
		playerPanel.add(textField);
		
		btn_pre = new JButton("◁");
		btn_pre.setBounds(275, 5, 45, 23);
		btn_pre.addActionListener(this);
		playerPanel.add(btn_pre);
		

		btn_play = new JToggleButton();
		btn_play.setText("PLAY / STOP");
		btn_play.setBounds(136, 28, 127, 23);
		playerPanel.add(btn_play);
		
		btn_next = new JButton("▷");
		btn_next.setBounds(394, 5, 45, 23);
		btn_next.addActionListener(this);
		playerPanel.add(btn_next);
		
		btn_ListOpen = new JButton(">>");
		btn_ListOpen.addActionListener(this); 	
		btn_ListOpen.setBounds(611, 5, 57, 23);
		playerPanel.add(btn_ListOpen);
		
		
		btn_ListClose = new JButton("<<");
		btn_ListClose.addActionListener(this);
		
		btn_ListClose.setBounds(611, 5, 57, 23);
		playerPanel.add(btn_ListClose);
		
		btn_open = new JButton("Open");
		btn_open.addActionListener(this);
		btn_open.setBounds(451, 5, 71, 23);
		playerPanel.add(btn_open);
		
		btn_del = new JButton("Delete");
		btn_del.addActionListener(this);
		btn_del.setBounds(454, 28, 67, 23);
		playerPanel.add(btn_del);
		
		btn_pause = new JButton("pause");

		btn_pause.setBounds(320, 52, 73, 23);
		btn_pause.addActionListener(this);
		playerPanel.add(btn_pause);
		
		btn_stop = new JButton("stop");
		btn_stop.addActionListener(this);
		btn_stop.setBounds(320, 28, 73, 23);
		playerPanel.add(btn_stop);
		
		btn_play_n = new JButton("play_n");
		btn_play_n.setBounds(320, 5, 73, 23);
		playerPanel.add(btn_play_n);
		btn_play_n.addActionListener(this);
		btn_ListClose.setVisible(false);
		
		JScrollPane listPane = new JScrollPane();
		listPane.setBounds(695, 10, 125, 512);
		contentPane.add(listPane);
		
		btn_upload = new JButton("UpLoad");
		listPane.setColumnHeaderView(btn_upload);
		
		model =  new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JList list = new JList();
		listPane.setViewportView(list);
		
		ha =  new HaMelGomPot();
		
		
	}
	
	private static byte[] toByteArray(String filePath) {
		byte[] returnValue = null;
		
		
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				FileInputStream fis = new FileInputStream(filePath)) {
			
			
			byte[] buf = new byte[1024];
			int read = 0;
			
			while((read=fis.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, read);
			}
			
			returnValue = baos.toByteArray();
		}catch (Exception e) {
			e.printStackTrace();
		
		}
	
		return returnValue;
	}

	private JFileChooser getChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("mp3 ����(*.mp3)","mp3"));
		
		chooser.setSelectedFile(new File("*.mp3"));
		
		
		return chooser;
	}
	
	public String getFile() {
		JFileChooser chooser = new JFileChooser("C:");
		chooser.showOpenDialog(this);
		
		File f=chooser.getSelectedFile();
		
		String path = "";
		String filename = "";
		
		path = f.getParentFile().toString();
		filename = f.getName(); //�대�留� 異�異�
		
		int idx = filename.lastIndexOf(".");  //���μ�� ��嫄�
		String _fileName = filename.substring(0,idx);
		
		return f.getName();	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		BoardDAO dao = new BoardDAO();
		JButton btn = (JButton) e.getSource();
		
		if(e.getSource()==btn_ListOpen) {		
			setSize(840, 560);
			btn_ListOpen.setVisible(false);
			btn_ListClose.setVisible(true);
		}
		else if(e.getSource()==btn_ListClose) {
			setSize(700, 560);
			btn_ListOpen.setVisible(true);
			btn_ListClose.setVisible(false);
		}else if(e.getSource()==btn_upload) {
			MusicVO vo = new MusicVO();
			MusicDAO mdao = new MusicDAO();
			JFileChooser choo = getChooser();
			 
			int retVal = choo.showOpenDialog(this);
			 
			if(retVal==0) {//�닿린 踰��� �대┃�� 寃쎌��
				File file = choo.getSelectedFile();
				Blob blob = new Blob(toByteArray(file.getPath()),null);
				vo.setBlob(blob);
				vo.setTitle(file.getName());

				mdao.upload(vo);
				 
			 }else {//痍⑥�� 踰��� �대┃�� 寃쎌��dfcrex
				 return;
			 }
			
		}else if(e.getSource()==btn_open) {
				//�닿린 踰��� �대┃�� 寃쎌��
				model.setRowCount(0);
			
				String file = getFile();	
				musicName = file;
				textField.setText(file);
				
				MuList.add(file);
				model.addRow(new Vector<String>(MuList));
				
				for(String value : MuList) {
					System.out.println(value); 
					}

//				songFile = choo.getSelectedFile();
//				textField.setText(songFile.getName());
				 
//				Object[] objlist = {songFile.getName()};
//				model.addRow(objlist);		
			
		}else if(btn == btn_play_n) {
			ha.stop();
			
			ha.open(musicName);
			ha.start();
			ha.stateCode = ha.STATE_INIT;
			
		}else {
			ha.resume();
		}
		
		if(btn==btn_stop) {
			ha.stop();
		}else if(btn==btn_pause) { 
			HaMelGomPot.stateCode = HaMelGomPot.STATE_SUSPENDED;
			ha.suspend();
		}else if(btn==btn_next) {
			a = MuList.size() + 1;
			ha.stop();
			ha.open(musicName);
			ha.start();
			ha.stateCode = ha.STATE_INIT;
			System.out.println("�ㅼ�� 怨�");
		}else if(btn==btn_pre) {
			a = MuList.size() - 1;
			ha.stop();
			ha.open(musicName);
			ha.start();
			ha.stateCode = ha.STATE_INIT;
		}else if(btn==btn_del) {
			MuList.remove(textField.getName());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
		
	

