package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.RoundedButton;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Graphics;


public class Register extends JFrame implements ActionListener{
	
	private JScrollPane scrollPane;
	private ImageIcon icon;
	private LoginDAO ldao;
	private JTextField txt_regid;
	private JPasswordField txt_regpw;
	private JTextField txt_regname;
	private JTextField txt_regmail;
	private JButton btn_regcheck;
	private JButton btn_regcancel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Register() {
		setTitle("회원가입");

		setResizable(false);

		setBounds(700, 330, 450, 300);
		setLocationRelativeTo(null);
		
		ldao = new LoginDAO();
		
		icon = new ImageIcon(Register.class.getResource("/image/intro_register.jpg"));
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				Dimension d = getSize();
				g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
				setVisible(true);
			}
		};
		
		scrollPane = new JScrollPane(background);
		background.setLayout(null);
		
		JLabel lb_title = new JLabel("회원가입");
		lb_title.setFont(new Font("굴림", Font.BOLD, 20));
		lb_title.setBounds(182, 17, 88, 26);
		background.add(lb_title);
		
		JLabel lb_regid = new JLabel("Username");
		lb_regid.setBounds(132, 61, 64, 15);
		background.add(lb_regid);
		
		txt_regid = new JTextField();
		txt_regid.setBounds(215, 57, 116, 21);
		background.add(txt_regid);
		txt_regid.setColumns(10);
		
		JLabel lb_regpw = new JLabel("Password");
		lb_regpw.setBounds(133, 95, 67, 15);
		background.add(lb_regpw);
		
		txt_regpw = new JPasswordField();
		txt_regpw.setBounds(215, 90, 116, 21);
		background.add(txt_regpw);
		txt_regpw.setColumns(10);
		
		JLabel lb_regname = new JLabel("이름");
		lb_regname.setBounds(166, 129, 30, 15);
		background.add(lb_regname);
		
		txt_regname = new JTextField();
		txt_regname.setBounds(215, 124, 116, 21);
		background.add(txt_regname);
		txt_regname.setColumns(10);
		
		JLabel lb_regmail = new JLabel("이메일");
		lb_regmail.setBounds(154, 164, 41, 15);
		background.add(lb_regmail);
		
		txt_regmail = new JTextField();
		txt_regmail.setBounds(215, 158, 116, 21);
		background.add(txt_regmail);
		txt_regmail.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		
		btn_regcheck = new RoundedButton("등록");
		btn_regcheck.addActionListener(this);
		btn_regcheck.setForeground(new Color(255, 255, 255));
		btn_regcheck.setBackground(new Color(0, 51, 102));
		btn_regcheck.setFont(new Font("굴림", Font.BOLD, 15));
		btn_regcheck.setBounds(147, 211, 70, 26);
		background.add(btn_regcheck);
		
		btn_regcancel = new RoundedButton("취소");
		btn_regcancel.addActionListener(this);
		btn_regcancel.setForeground(new Color(255, 255, 255));
		btn_regcancel.setBackground(new Color(0, 51, 120));
		btn_regcancel.setFont(new Font("굴림", Font.BOLD, 15));
		btn_regcancel.setBounds(229, 211, 70, 26);
		background.add(btn_regcancel);
		
	
		setVisible(true);
		setContentPane(scrollPane);	
		
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_regcheck) {
			LoginVO vo = new LoginVO();
			StringBuilder message = new StringBuilder();
			
			if(!(txt_regid.getText().equals(""))) {
				vo.setId(txt_regid.getText());
			} else {
				message.append("Username");
			}
			if(!(txt_regpw.getText().equals(""))) {
				vo.setPasswd(txt_regpw.getText());
			} else {
				message.append("\nPassword");
			}
			if(!(txt_regname.getText().equals(""))) {
				vo.setName(txt_regname.getText());
			} else {
				message.append("\n이름");
			}
			if(!(txt_regmail.getText().equals(""))) {
				vo.setEmail(txt_regmail.getText());
			} else {
				message.append("\nEmail");
			}
			
			
			//db에 입력하기
			
			if(vo.getId()!=null && vo.getPasswd()!=null && vo.getName()!=null && vo.getEmail()!=null) {
				int result = ldao.reg(vo);
				if(result > 0) {
					JOptionPane.showMessageDialog(this, "성공");				
					this.dispose();
				}else {
					JOptionPane.showMessageDialog(this, "Username 또는 Email이 중복됩니다.");
				}
			}else {
				JOptionPane.showMessageDialog(this, "다음의 항목을 확인해 주세요.\n\n"+message);
			}
		}else {
			dispose();
		}
			
			
	}
}