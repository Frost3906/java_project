package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

import javax.swing.JButton;
import javax.swing.JFileChooser;


public class MainInterface extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JFileChooser chooser;
	private JButton btn_open;


	public static void main(String[] args) {
		

	
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainInterface frame = new MainInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public MainInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		btn_open = new JButton("찾아보기");
		btn_open.addActionListener(this);
		contentPane.add(btn_open, BorderLayout.WEST);
		
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
		if(e.getSource()==btn_open) {
			MusicVO vo = new MusicVO();
			MusicDAO dao = new MusicDAO();
			byte[] returnval = null;
			 JFileChooser choo = getChooser();
			 
			 int retVal = choo.showOpenDialog(this);
			 
			 if(retVal==0) {//열기 버튼 클릭한 경우
				 File file = choo.getSelectedFile();
				 vo.setTitle(file.getName());
				 
				 
		
				Blob blob = new Blob(toByteArray(file.getPath()),null);
				vo.setBlob(blob);
			
			int result = dao.upload(vo);
				 
			 }else {//취소 버튼 클릭한 경우
				 return;
			 }
		}
	}
	
	
	
		
	
	
	
}
