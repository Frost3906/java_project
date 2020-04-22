package login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPasswordField;



public class Login extends JFrame implements ActionListener{
	private FirstPanel main;
	private JPanel contentPane;
	private  JLabel lb_id = new JLabel("ID");
	private  JLabel lb_pw = new JLabel("PASSWORD");
	private  JTextField txt_id = new JTextField();
	private  JButton btn_login;
	private  JButton btn_reg;
	private final JPasswordField txt_pw = new JPasswordField();

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
		btn_login = new JButton("로그인");
		btn_reg = new JButton("회원가입");
		btn_login.addActionListener(this);
		btn_reg.addActionListener(this);
		
		contentPane.add(txt_pw);
		contentPane.add(btn_reg);
		contentPane.add(btn_login);
		setVisible(true);
		
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
				main.showMainI();
				
			}else {
				JOptionPane.showMessageDialog(this, "실패");
			}
			
		
		}else if(e.getSource()==btn_reg) {
			
			main.showRegFrm();
		
		}
		
	}

}


