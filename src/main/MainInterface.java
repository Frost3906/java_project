package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.imageio.stream.FileImageInputStream;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.cj.jdbc.Blob;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JScrollPane;


public class MainInterface extends JFrame implements ActionListener,ItemListener{

	private JPanel contentPane;
	private JButton btn_upload, btn_open;
	private JPanel panel_1;
	private JTextField textField;
	private JButton btnNewButton_2;
	private Player play;
	private File songFile;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_3;
	private JToggleButton btn_play;
	private Thread thread = new Thread();


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
		setBounds(100, 100, 460, 395);
		setTitle("Music Player");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 0, 0);
		contentPane.add(panel);
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(12, 10, 428, 70);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(12, 5, 251, 23);
		panel_1.add(textField);
		textField.setColumns(10);
		
		btnNewButton_2 = new JButton("◁");
		btnNewButton_2.setBounds(12, 36, 45, 23);
		panel_1.add(btnNewButton_2);
		
		btn_play = new JToggleButton();
		btn_play.setText("PLAY");
		btn_play.addActionListener(this); 
		btn_play.setIcon(null);
		btn_play.setBounds(66, 36, 140, 23);
		panel_1.add(btn_play);
		
		btnNewButton = new JButton("▷");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(218, 36, 45, 23);
		panel_1.add(btnNewButton);
		
		btn_upload = new JButton("UpLoad");
		btn_upload.setBounds(332, 37, 83, 21);
		btn_upload.addActionListener(this);
		panel_1.add(btn_upload);
		
		btnNewButton_1 = new JButton("∞");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 15));
		btnNewButton_1.setBounds(275, 36, 45, 23);
		panel_1.add(btnNewButton_1);
		
		btn_open = new JButton("Open File");
		btn_open.addActionListener(this);
		btn_open.setBounds(275, 4, 140, 23);
		panel_1.add(btn_open);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 92, 428, 255);
		contentPane.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		
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
			
				dao.upload(vo);
				 
			 }else {//취소 버튼 클릭한 경우
				 return;
			 }
		}else if(e.getSource()==btn_open) {
			JFileChooser choo = getChooser();
			 
			int retVal = choo.showOpenDialog(this);
			 
			if(retVal==0) {//열기 버튼 클릭한 경우
				songFile= choo.getSelectedFile();
				textField.setText(songFile.getName());
			
			}
		
		}else if(e.getSource()==btn_play){
			
			try {
				thread.start();
				play = new Player(new FileInputStream(songFile));
				play.play();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (JavaLayerException e1) {
				e1.printStackTrace();
			}
		
		}
			
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
	
		if(e.getStateChange()==1){
//			try {
////				play = new Player(new FileInputStream(songFile));
////				play.play();
//			} catch (FileNotFoundException e1) {
//				e1.printStackTrace();
//			} catch (JavaLayerException e1) {
//				e1.printStackTrace();
//			}
		}else {
			play.close();
		}
	}
}



























