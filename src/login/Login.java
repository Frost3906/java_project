package login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Login extends JFrame implements ActionListener{
	private FirstPanel main;
	private JPanel contentPane;
	private final JLabel lb_id = new JLabel("ID");
	private final JLabel lb_pw = new JLabel("PASSWORD");
	private final JTextField txt_id = new JTextField();
	private final JTextField txt_pw = new JTextField();
	private final JButton btn_login;
	private final JButton btn_reg;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		contentPane.add(lb_id);
		contentPane.add(txt_id);
		txt_id.setColumns(10);
		contentPane.add(lb_pw);
		contentPane.add(txt_pw);
		txt_pw.setColumns(10);
		btn_login = new JButton("로그인");
		btn_reg = new JButton("회원가입");
		contentPane.add(btn_reg);
		contentPane.add(btn_login);
		btn_login.addActionListener(this);
		btn_reg.addActionListener(this);
		setVisible(true);
		
	}


	public void setMain(FirstPanel main) {
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btn_reg) {
			System.out.println("버튼 눌러짐");
		}
		if(e.getSource()==btn_login) {
			LoginDAO dao = new LoginDAO();
			LoginVO vo = new LoginVO();
			vo=dao.login(txt_id.getText(), txt_pw.getText());
			if(vo.getName()!=null) {
				System.out.println("로그인 성공");
			}else {
				System.out.println("로그인 실패");
			}
			
		if(e.getSource()==btn_login) {
			
		}else if(e.getSource()==btn_reg) {
			
			main.showRegFrm();
		
		}
		
	}

	}
}

