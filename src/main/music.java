package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class music extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnPlay,btnPause,btnStop;
	private JButton btnFile;
	
	private HaMelGomPot pot;
	
	//현재 재생할 곡 
	private String musicName;
	private JButton btnRestart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					music frame = new music();
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
	public music() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnFile = new JButton("파일선택");
		contentPane.add(btnFile);
		
		btnPlay = new JButton("play");
		contentPane.add(btnPlay);
		
		btnPause = new JButton("일시정지");
		contentPane.add(btnPause);
		
		btnStop = new JButton("멈춤");
		contentPane.add(btnStop);
		
		btnRestart = new JButton("재시작");
		contentPane.add(btnRestart);
		
		
		//player 초기화
		pot = new HaMelGomPot();
		
		btnFile.addActionListener(this);
		btnPlay.addActionListener(this);
		btnPause.addActionListener(this);
		btnStop.addActionListener(this);
		btnRestart.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if(btn==btnPlay) { //플레이
			//일시 정지 후 다시 재생 버튼을 누를 때 기존의 스레드 중지
			pot.stop();
			
			pot.open(musicName);			
			pot.start();
			//일시멈춤 후 재시작 버튼이 아니라 플레이 버튼을 누르는 경우
			HaMelGomPot.stateCode = HaMelGomPot.STATE_INIT;
		}else if(btn==btnStop) {//멈춤
			pot.stop();			
		}else if(btn==btnFile) { //음악 파일 선택
			String file=getFile();	
			musicName = file;
			textField.setText(file); 
		}else if(btn==btnPause) { //일시정지
			
			//일시멈춤 후 재시작을 위한 코드
			HaMelGomPot.stateCode = HaMelGomPot.STATE_SUSPENDED;
			pot.suspend();
			
		}else if(btn==btnRestart) { //일시정지 후 재시작
			pot.resume();
		}
		
	}//actionPerformed

	
	public String getFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(this);
		File f=chooser.getSelectedFile();
		return f.getPath();
	}
}
