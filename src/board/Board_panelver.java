package board;

import java.awt.BorderLayout;

import java.awt.Cursor;
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
import login.Login;
import login.LoginVO;

import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import ui.CircleButton;
import ui.RoundedButton;

import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;


public class Board_panelver extends JFrame implements ActionListener,MouseListener{

	private ImageIcon icon;
	private JPanel contentPane, playerPanel;
	private BoardPanel boardPanel;
	private JTextField textField;
	private JButton btn_downloader, btn_upload, btn_ListOpen, btn_ListClose, btn_pre, btn_next, btn_open, btn_del, btn_restart;
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
	private Cursor cursor;
	Image image;
	
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
		
		setTitle("WaterMelone");
		
		icon = new ImageIcon(Board_panelver.class.getResource("/image/intro_board.jpg"));
		
		voo=vo;
		
		cursor = new Cursor(cursor.HAND_CURSOR);
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
					
		playerPanel.setBackground(new Color(220, 220, 220));
		playerPanel.setLayout(null);
		playerPanel.setBounds(12, 10, 669, 95);
		contentPane.add(playerPanel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(24, 16, 232, 23);
		playerPanel.add(textField);
		
		btn_pre = new JButton("");
		btn_pre.setSelectedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_pre.png")));
		btn_pre.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_pre_p.png")));
		btn_pre.setBounds(287, 35, 39, 38);
		btn_pre.addActionListener(this);
		playerPanel.add(btn_pre);
		
		btn_next = new JButton("");
		btn_next.setSelectedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_next.png")));
		btn_next.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_next_p.png")));
		btn_next.setBounds(541, 35, 39, 38);
		btn_next.addActionListener(this);
		playerPanel.add(btn_next);
		
		btn_ListOpen = new RoundedButton(">>");
		btn_ListOpen.setForeground(new Color(255, 69, 0));
		btn_ListOpen.setBackground(new Color(255, 248, 220));
		btn_ListOpen.addActionListener(this); 	
		btn_ListOpen.setBounds(612, 0, 57, 23);
		playerPanel.add(btn_ListOpen);
		
		
		btn_ListClose = new RoundedButton("<<");
		btn_ListClose.setForeground(new Color(255, 69, 0));
		btn_ListClose.setBackground(new Color(255, 248, 220));
		btn_ListClose.addActionListener(this);
		
		btn_ListClose.setBounds(612, 0, 57, 23);
		playerPanel.add(btn_ListClose);
		
		btn_open = new JButton("");
		btn_open.setSelectedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_open2.png")));
		btn_open.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_open2_p.png")));
		btn_open.addActionListener(this);
		btn_open.setBounds(629, 24, 40, 38);
		playerPanel.add(btn_open);
		
		btn_del = new JButton("");
		btn_del.setSelectedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_del.png")));
		btn_del.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_del_p.png")));
		btn_del.addActionListener(this);
		btn_del.setBounds(630, 57, 39, 38);
		playerPanel.add(btn_del);
		
		btn_pause = new JButton("");
		btn_pause.setSelectedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_pause.png")));
		btn_pause.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_pause_p.png")));

		btn_pause.setBounds(440, 35, 39, 38);
		btn_pause.addActionListener(this);
		playerPanel.add(btn_pause);
		
		btn_stop = new JButton("");
		btn_stop.setSelectedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_stop.png")));
		btn_stop.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_stop_p.png")));
		btn_stop.addActionListener(this);
		btn_stop.setBounds(338, 35, 39, 38);
		playerPanel.add(btn_stop);
		
		btn_restart = new JButton("Restart");
		btn_restart.setSelectedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_restrat.png")));
		btn_restart.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_restrat_p.png")));
		btn_restart.setText("");
		btn_restart.setBounds(491, 35, 39, 38);
		playerPanel.add(btn_restart);
		
		btn_upload = new JButton("");
		btn_upload.setSelectedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_upload_p.png")));
		btn_upload.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_upload.png")));
		btn_upload.setBounds(34, 49, 38, 39);
		btn_upload.addActionListener(this);
		playerPanel.add(btn_upload);
		
		btn_play_n = new JButton("");
		btn_play_n.setBounds(389, 35, 39, 38);
		playerPanel.add(btn_play_n);
		btn_play_n.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_play_p.png")));
		btn_play_n.addActionListener(this);
		btn_ListClose.setVisible(false);
		
		btn_downloader = new JButton("");
		btn_downloader.setSelectedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_download_p.png")));
		btn_downloader.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_download.png")));
		btn_downloader.setBounds(84, 49, 38, 39);
		btn_downloader.addActionListener(this);
		playerPanel.add(btn_downloader);
		
		
		JScrollPane listPane = new JScrollPane();
		listPane.setBounds(699, 10, 125, 512);
		listPane.getViewport().setBackground(new Color(255, 240, 245));
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
		
		btn_play_n.setBorderPainted(false);
		btn_next.setBorderPainted(false);
		btn_pre.setBorderPainted(false);
		btn_stop.setBorderPainted(false);
		btn_pause.setBorderPainted(false);
		btn_restart.setBorderPainted(false);
		btn_upload.setBorderPainted(false);
		btn_del.setBorderPainted(false);
		btn_open.setBorderPainted(false);
		btn_downloader.setBorderPainted(false);
		
		btn_play_n.setFocusPainted(false);
		btn_next.setFocusPainted(false);
		btn_pre.setFocusPainted(false);
		btn_stop.setFocusPainted(false);
		btn_pause.setFocusPainted(false);
		btn_restart.setFocusPainted(false);
		btn_upload.setFocusPainted(false);
		btn_del.setFocusPainted(false);
		btn_open.setFocusPainted(false);
		btn_downloader.setFocusPainted(false);
			
		btn_play_n.setContentAreaFilled(false);
		btn_next.setContentAreaFilled(false);
		btn_pre.setContentAreaFilled(false);
		btn_stop.setContentAreaFilled(false);
		btn_pause.setContentAreaFilled(false);
		btn_restart.setContentAreaFilled(false);
		btn_upload.setContentAreaFilled(false);
		btn_del.setContentAreaFilled(false);
		btn_open.setContentAreaFilled(false);
		btn_downloader.setContentAreaFilled(false);
		
//		btn_play_n.setRolloverIcon(new ImageIcon(Board_panelver.class.getResource("/image/play_t.png"))); //마우스 오버
//		btn_next.setRolloverIcon(new ImageIcon(Board_panelver.class.getResource("/image/nextbtn_r.png"))); 
//		btn_pre.setRolloverIcon(new ImageIcon(Board_panelver.class.getResource("/image/prebtn_r.png"))); 
//		btn_stop.setRolloverIcon(new ImageIcon(Board_panelver.class.getResource("/image/stop_r.png"))); 
//		btn_pause.setRolloverIcon(new ImageIcon(Board_panelver.class.getResource("/image/pausebtn_r.png"))); 
//		btn_restart.setRolloverIcon(new ImageIcon(Board_panelver.class.getResource("/image/restart_r.png"))); 
		
		btn_play_n.setPressedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_play.png"))); //마우스 클릭 시
		btn_next.setPressedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_next.png"))); 
		btn_pre.setPressedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_pre.png"))); 
		btn_stop.setPressedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_stop.png"))); 
		btn_pause.setPressedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_pause.png")));
		btn_restart.setPressedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_restrat.png")));
		btn_upload.setPressedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_upload_p.png")));
		btn_del.setPressedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_del.png")));
		btn_open.setPressedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_open2.png")));
		btn_downloader.setPressedIcon(new ImageIcon(Board_panelver.class.getResource("/image/recbtn_download_p.png")));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Board_panelver.class.getResource("/image/음표 연두색.gif")));
		lblNewLabel.setBounds(0, -1, 669, 96);
		playerPanel.add(lblNewLabel);
				
		btn_stop.addMouseListener(this);
		btn_pause.addMouseListener(this);
		btn_play_n.addMouseListener(this);
		btn_next.addMouseListener(this);
		btn_pre.addMouseListener(this);
		btn_restart.addMouseListener(this);
		btn_upload.addMouseListener(this);
		btn_del.addMouseListener(this);
		btn_open.addMouseListener(this);
		btn_downloader.addMouseListener(this);
		
		
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
		JFileChooser chooser = new JFileChooser("C:");
		chooser.setAcceptAllFileFilterUsed(false);
		
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("mp3 file(*.mp3)","mp3"));
		
		chooser.setSelectedFile(new File("*.mp3"));
		
		
		return chooser;
	}
	
	public File[] getFile() {
		JFileChooser chooser = new JFileChooser("C:");
		chooser.setMultiSelectionEnabled(true); //파일 다중 선택
		chooser.showOpenDialog(this);
		
		File[] filelist = chooser.getSelectedFiles();
		
		//단일 선택
//		File f=chooser.getSelectedFile();	
			
		// 여러 파일 읽기
		
//		File[] filelist = f.listFiles();
//		
//		for(File file : filelist) {
//			if(file.isFile());
//		}
		
		return filelist;	
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
		}
		else if(e.getSource()==btn_del) {
			System.out.println("del");
			pos=table.getSelectedRow();
			model.removeRow(pos);

		}else if(e.getSource()==btn_upload) {
			MusicVO vo = new MusicVO();
			MusicDAO mdao = new MusicDAO();
			JFileChooser choo = getChooser();
			 
			int retVal = choo.showOpenDialog(this);
			 
			if(retVal==0) {//열기 버튼을 눌렀을때
				File file = choo.getSelectedFile();
				Blob blob = new Blob(toByteArray(file.getPath()),null);
				vo.setBlob(blob);
				vo.setTitle(file.getName());

				mdao.upload(vo);
				JOptionPane.showMessageDialog(this, "업로드 성공");
			 }else {//닫기 버튼을 눌렀을때
				 return;
			 }
			
		}else if(e.getSource()==btn_downloader) {
			MusicDBLoader loader = new MusicDBLoader();
			loader.show();
		}else if(e.getSource()==btn_open) {
			
				File[] file = getFile();	

				if(file!=null) {
					//테이블 초기화
					model.setRowCount(0);
				}
				//사용자가 선택한 파일명 보여주기
//				textField.setText(file.getName());
				
				
				for(File f:file) {
					//사용자가 선택한 파일 Vector 담기
					songfile.add(f);		
					MuList.add(f.getPath());					
				}
				
				//재생할 음악리스트명(파일경로 필요) 
//				musicName = file.getPath();
								

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
		}else if(btn==btn_next) {	// 다음 곡 	
			System.out.println("다음 곡" + MuList.size());
			pos += 1;
	
			if(pos == MuList.size()) {
				pos = 0;
			}
			table.changeSelection(pos, 0, false, false);
			ha.stop();
			play(MuList.get(pos));
			
			//리스트에서 사용자가 넘긴 음악파일명 보여주기 
			textField.setText(songfile.get(pos).getName().toString());
			

//			else if(pos == MuList.size()){
//				//pos=MuList.size()-1;
//				pos = 0;
//				System.out.println("다음곡 : "+MuList.get(pos)+" 위치 : "+pos);
//			}
//			play(MuList.get(pos));		
			
		}else if(btn==btn_pre) { 
			System.out.println("이전 곡");
			pos -= 1;
			if(pos == -1) {   // 첫번째 곡일때 이전 버튼 처리
				pos = 0;
				
			//테이블도 변화
			table.changeSelection(pos, 0, false, false);
			ha.stop();
			play(MuList.get(pos));
			
			//리스트에서 사용자가 선택한 음악파일명 보여주기 
			textField.setText(songfile.get(pos).getName().toString());

//			}else if(pos < MuList.size()) { //
//				pos -= 1;
//				System.out.println("이전곡 : "+MuList.get(pos)+" 위치 : "+pos);
//			}			
//			play(MuList.get(pos));
		}
	}
}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount()==2) {
		// 사용자가 선택한 파일 인덱스
		pos=table.getSelectedRow();
		
		//리스트에서 사용자가 선택한 음악파일명 보여주기 
		textField.setText(songfile.get(pos).getName().toString());
	
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
		
//		  btn_next.addMouseListener(this);
//		  btn_pre.addMouseListener(this);
//		  btn_restart.addMouseListener(this);
		if(e.getSource()==btn_stop) {
			btn_stop.setCursor(cursor);
		}
		if(e.getSource()==btn_pause) {
			btn_pause.setCursor(cursor);
		}
		if(e.getSource()==btn_play_n) {
			btn_play_n.setCursor(cursor);
		}
		if(e.getSource()==btn_next) {
			btn_next.setCursor(cursor);
		}
		if(e.getSource()==btn_pre) {
			btn_pre.setCursor(cursor);
		}
		if(e.getSource()==btn_restart) {
			btn_restart.setCursor(cursor);
		}
		if(e.getSource()==btn_del) {
			btn_del.setCursor(cursor);
		}
		if(e.getSource()==btn_upload) {
			btn_upload.setCursor(cursor);
		}
		if(e.getSource()==btn_downloader) {
			btn_downloader.setCursor(cursor);
		}
		if(e.getSource()==btn_open) {
			btn_open.setCursor(cursor);
		}
		
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














		
	

