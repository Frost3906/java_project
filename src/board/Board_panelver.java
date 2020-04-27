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
import javax.swing.DefaultListModel;
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
	private JButton btn_ListOpen, btn_ListClose, btn_pre, btn_next, btn_open, btn_del, btn_upload, btn_restart;
	private JToggleButton btn_play;
	private ArrayList<String> MuList;
	private Vector<File> songfile;
	private DefaultTableModel model;
	private String musicName;
	private HaMelGomPot ha;
	int a = 0;
	
	private static LoginVO voo;
	private JButton btn_stop;
	private JButton btn_pause;
	private JButton btn_play_n;
	private JTable table;
	
	
	//리스트에서 선택한 파일 위치
	private int pos;
	
	
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
	//	listvo=new ListVO();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(700,300,700,560);
		
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
		btn_ListOpen.setBounds(612, 1, 57, 23);
		playerPanel.add(btn_ListOpen);
		
		
		btn_ListClose = new JButton("<<");
		btn_ListClose.addActionListener(this);
		
		btn_ListClose.setBounds(612, 1, 57, 23);
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
		
		btn_restart = new JButton("Restart");
		btn_restart.setBounds(425, 52, 97, 23);
		playerPanel.add(btn_restart);
		
		btn_upload = new JButton("UpLoad");
		btn_upload.setBounds(546, 28, 122, 23);
		playerPanel.add(btn_upload);
		btn_play_n.addActionListener(this);
		btn_ListClose.setVisible(false);
		
		JScrollPane listPane = new JScrollPane();
		listPane.setBounds(699, 10, 125, 512);
		contentPane.add(listPane);

		String columnName[]= {"Music"};
		
		model = new DefaultTableModel(columnName,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(model);
		table.addMouseListener(this);
		listPane.setViewportView(table);
		
		
		ha =  new HaMelGomPot();
		MuList = new ArrayList();
		songfile = new Vector<>();
		
		btn_restart.addActionListener(this);
		
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
	
	public File getFile() {
		JFileChooser chooser = new JFileChooser("D:\\Billboard Hot 100");
		chooser.showOpenDialog(this);
		
		File f=chooser.getSelectedFile();	
		
		return f;	
		}

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
			
				File file = getFile();	

				if(file!=null) {
					//테이블 초기화
					model.setRowCount(0);
				}
				//사용자가 선택한 파일명 보여주기
				textField.setText(file.getName());
				
				//사용자가 선택한 파일 Vector 담기
				songfile.add(file);			
				//재생할 음악리스트명(파일경로 필요)
				musicName = file.getPath();
				MuList.add(musicName);
				
				//Vector 에 담긴 file을 파일명과 경로명을 포함한 파일명으로 분리 추출
				for(File f:songfile) {
					//  보여줄 음악리스트 명
					Vector<String> vec=new Vector<>();
					vec.add(f.getName());
					model.addRow(vec);
				}				
			
		}else if(btn == btn_play_n) { //플레이
			play(MuList.get(pos));			
		}else if(btn==btn_restart){ //재시작
			ha.resume();			
		}
		
		if(btn==btn_stop) {
			ha.stop();
		}else if(btn==btn_pause) { 
			HaMelGomPot.stateCode = HaMelGomPot.STATE_SUSPENDED;
			ha.suspend();
		}else if(btn==btn_next) {		
			System.out.println("다음 곡" + MuList.size());
			if(pos < MuList.size()) {
				pos+=1;
			}else if(pos == MuList.size()){
				//pos=MuList.size()-1;
				pos = 0;
				System.out.println("다음곡 : "+MuList.get(pos)+" 위치 : "+pos);
			}
			play(MuList.get(pos));			
		}else if(btn==btn_pre) {
			System.out.println("이전 곡");
			if(pos==0) {   // 첫번째 곡일때 이전 버튼 처리
				pos = 0;
			}else if(pos < MuList.size()) { //
				pos -= 1;
				System.out.println("이전곡 : "+MuList.get(pos)+" 위치 : "+pos);
			}			
			play(MuList.get(pos));			
		}else if(btn==btn_del) {
			MuList.remove(textField.getName());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount()==2) {
		// 사용자가 선택한 파일 인덱스
		pos=table.getSelectedRow();
		
		//리스트에서 사용자가 선택한 음악파일명 보여주기 
		textField.setText(songfile.get(pos).toString());
	
		//사용자가 선택한 파일 플레이
		play(MuList.get(pos));
		
		}
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
	
	public void play(String musicName) {		
		ha.stop();		
		ha.open(musicName);
		ha.start();
		HaMelGomPot.stateCode = HaMelGomPot.STATE_INIT;
		
	}
}














		
	

