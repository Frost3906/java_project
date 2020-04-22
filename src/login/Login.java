package login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import board.Board;
import main.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;



public class Login extends JFrame implements ActionListener{
	private FirstPanel main;
	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JPanel panel_2 = new JPanel();
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JButton btn_reg = new JButton("회원가입");
	private final JButton btn_login = new JButton("로그인");
	private final JLabel lb_id = new JLabel("아이디:");
	private final JTextField txt_id = new JTextField();
	private final JLabel lb_pw = new JLabel("비밀번호:");
	private final JTextField txt_pw = new JPasswordField();

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
		txt_pw.setBounds(196, 7, 116, 21);
		txt_pw.setColumns(10);
		txt_id.setBounds(196, 81, 116, 21);
		txt_id.setColumns(10);
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));	
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));	
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(2, 1, 0, 0));	
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		lb_id.setBounds(139, 84, 57, 15);	
		panel_3.add(lb_id);	
		panel_3.add(txt_id);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		lb_pw.setBounds(127, 10, 57, 15);	
		panel_2.add(lb_pw);	
		panel_2.add(txt_pw);		
		FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setVgap(10);
		panel.add(panel_4, BorderLayout.SOUTH);		
		btn_reg.setBackground(new Color(255, 192, 203));
		btn_reg.setFont(new Font("굴림", Font.BOLD, 12));
		panel_4.add(btn_reg);		
		btn_login.setBackground(new Color(255, 192, 203));
		btn_login.setFont(new Font("굴림", Font.BOLD, 12));
		panel_4.add(btn_login);
			
		setVisible(true);		
		btn_login.addActionListener(this);
		btn_reg.addActionListener(this);				
	}

	public void setMain(FirstPanel main) {
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_login) {
			System.out.println("로그인");
			LoginDAO dao = new LoginDAO();
			LoginVO vo = new LoginVO();
			vo = dao.login(txt_id.getText(), txt_pw.getText());
			if(vo.getName()!=null) {
				String a = txt_id.getText();
				Board board = new Board();
				board.getid(a);
				board.show();
				dispose();
//
//				main.showMainI();				
			}else {
				JOptionPane.showMessageDialog(this, "실패");
			}			
		
			}else if(e.getSource()==btn_reg) {
			
				main.showRegFrm();	
			}	
	}
}


