package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.stream.FileImageInputStream;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.Blob;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import javazoom.jl.decoder.*;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainInterface extends JFrame implements ActionListener,MouseListener,ItemListener{
	
	private JPanel contentPane;
	private JButton btn_upload, btn_open,btn_pre,btn_next,btn_del,btn_stop,btn_pause;
	private JToggleButton btn_play;
	private JPanel playArea;
	private JTextField textField;
	private Player play;
	private File songFile;
	private Thread thread = new Thread();
	private JTable table;
	private DefaultTableModel model;
	private JFileChooser choo;
	private HaMelGomPot ha;
	private String musicName;
	private ButtonGroup gl;
	private ArrayList<File> MuList;
	int a = 0;
	

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					MainInterface frame = new MainInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainInterface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 282, 259);
		setTitle("Music Player");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		playArea = new JPanel();
		playArea.setBounds(12, 10, 240, 125);
		contentPane.add(playArea);
		playArea.setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(0, 58, 232, 23);
		playArea.add(textField);
		textField.setColumns(10);
		
		btn_pre = new JButton("");
		btn_pre.setIcon(new ImageIcon(MainInterface.class.getResource("/main/prebtn_s.png")));
		btn_pre.setFont(btn_pre.getFont().deriveFont(btn_pre.getFont().getSize() - 1f));
		btn_pre.setBounds(50, 10, 45, 38);
		playArea.add(btn_pre);
		
		btn_play = new JToggleButton();
//		btn_play.addActionListener(this);
		btn_play.addItemListener(this);
		btn_play.setIcon(new ImageIcon(MainInterface.class.getResource("/main/play_p.png")));
		btn_play.setBounds(96, 10, 45, 38);
		playArea.add(btn_play);
		
		btn_next = new JButton("");
		btn_next.setIcon(new ImageIcon(MainInterface.class.getResource("/main/nextbtn_s.png")));
		btn_next.setFont(btn_next.getFont().deriveFont(btn_next.getFont().getSize() - 1f));
		btn_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_next.setBounds(145, 10, 45, 40);
		playArea.add(btn_next);
		
		btn_upload = new JButton("UpLoad");
		btn_upload.setBounds(160, 92, 75, 21);
		btn_upload.addActionListener(this);
		playArea.add(btn_upload);
		
		btn_open = new JButton("Open");
		btn_open.addActionListener(this);
		btn_open.setBounds(0, 91, 68, 23);
		playArea.add(btn_open);
		
		btn_del = new JButton("Delete");
		btn_del.setBounds(80, 91, 68, 23);
		playArea.add(btn_del);
		
		btn_stop = new JButton("");
		btn_stop.setIcon(new ImageIcon(MainInterface.class.getResource("/main/stop_s.png")));
		btn_stop.setBounds(2, 10, 45, 38);
		playArea.add(btn_stop);
		
		btn_pause = new JButton("");
		btn_pause.setIcon(new ImageIcon(MainInterface.class.getResource("/main/pausebtn_s.png")));
		btn_pause.setBounds(190, 9, 45, 40);
		playArea.add(btn_pause);
		
		btn_play.setBorderPainted(false);
		btn_next.setBorderPainted(false);
		btn_pre.setBorderPainted(false);
		btn_stop.setBorderPainted(false);
		btn_pause.setBorderPainted(false);
		
		btn_play.setFocusPainted(false);
		btn_next.setFocusPainted(false);
		btn_pre.setFocusPainted(false);
		btn_stop.setFocusPainted(false);
		btn_pause.setFocusPainted(false);
		
		btn_play.setContentAreaFilled(false);
		btn_next.setContentAreaFilled(false);
		btn_pre.setContentAreaFilled(false);
		btn_stop.setContentAreaFilled(false);
		btn_pause.setContentAreaFilled(false);
		
		btn_play.addMouseListener(this);
		btn_next.addMouseListener(this);
		btn_pre.addMouseListener(this);
		btn_stop.addMouseListener(this);
		btn_pause.addMouseListener(this);
		
		ha = new HaMelGomPot();
		MuList = new ArrayList<File>();
		ImageIcon pl_n = new ImageIcon("play_p");
		ImageIcon pl_p = new ImageIcon("play_s");
		ImageIcon pl_r = new ImageIcon("play_n");
		
		btn_play.setPressedIcon(pl_p);
		btn_play.setRolloverIcon(pl_r);
	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 145, 240, 65);
		contentPane.add(scrollPane);
		
		table = new JTable();
		String columnName[]= {"Music","Time"};
	
		model = new DefaultTableModel(columnName,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(model);
		table.setFont(new Font("굴림", Font.PLAIN, 15));
		table.getColumnModel().getColumn(0).setPreferredWidth(300);  //JTable 의 컬럼 길이 조절
		scrollPane.setViewportView(table);
		
		
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
		
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("mp3 파일(*.mp3)","mp3"));
		
		chooser.setSelectedFile(new File("*.mp3"));
		
		
		return chooser;
	}
	
	public String getFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(this);
		File f=chooser.getSelectedFile();
		return f.getPath();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_upload) {
			MusicVO vo = new MusicVO();
			MusicDAO dao = new MusicDAO();
			JFileChooser choo = getChooser();
			 
			int retVal = choo.showOpenDialog(this);
			 
			if(retVal==0) {//열기 버튼 클릭한 경우
				File file = choo.getSelectedFile();
				Blob blob = new Blob(toByteArray(file.getPath()),null);
				vo.setBlob(blob);
				vo.setTitle(file.getName());

				dao.upload(vo);
				 
			 }else {//취소 버튼 클릭한 경우
				 return;
			 }
			
		}else if(e.getSource()==btn_open) {
				//열기 버튼 클릭한 경우
				String file = getFile();	
				musicName = file;
				textField.setText(file);
				
				
//				songFile = choo.getSelectedFile();
//				textField.setText(songFile.getName());
				 
//				Object[] objlist = {songFile.getName()};
//				model.addRow(objlist);		
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn_stop) {//멈춤
			ha.stop();
		}else if(btn==btn_pause) { //일시정지
			
			//일시멈춤 후 재시작을 위한 코드
			HaMelGomPot.stateCode = HaMelGomPot.STATE_SUSPENDED;
			ha.suspend();
		}else if(btn==btn_next) {
//			a = objlist.length + 1;
			ha.stop();
			ha.open(musicName);
			ha.start();
			ha.stateCode = ha.STATE_INIT;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == 1) {
			ha.stop();
			
			ha.open(musicName);
			ha.start();
			ha.stateCode = ha.STATE_INIT;
			
		}else {
			
			ha.resume();
		}
	}
		
}

































