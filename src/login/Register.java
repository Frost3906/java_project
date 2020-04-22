package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;


public class Register extends JFrame implements ActionListener{

	private JPanel contentPane;
	private LoginDAO ldao;
	private JPanel panel;
	private JButton btn_regcheck;
	private JButton btn_regcancel;

	private JTextField txt_regid;
	private JTextField txt_regname;
	private JTextField txt_regmail;
	private JTextField textField;

	private final JPasswordField txt_regpw = new JPasswordField();

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
		
		ldao = new LoginDAO();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lb_regid = new JLabel("ID");
		lb_regid.setFont(new Font("굴림", Font.BOLD, 12));
		lb_regid.setBounds(165, 10, 13, 15);
		panel_2.add(lb_regid);
		
		txt_regid = new JTextField();
		txt_regid.setBounds(190, 7, 116, 21);
		panel_2.add(txt_regid);
		txt_regid.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lb_regpw = new JLabel("PASSWORD");
		lb_regpw.setFont(new Font("굴림", Font.BOLD, 12));
		lb_regpw.setBounds(103, 10, 75, 15);
		panel_4.add(lb_regpw);
		
	
		txt_regpw.setBounds(190, 7, 116, 21);
		panel_4.add(txt_regpw);
		
		textField = new JTextField();
		textField.setBounds(190, 7, 116, 21);
		panel_4.add(textField);
		textField.setColumns(10);
		txt_regpw.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lb_regname = new JLabel("이름");
		lb_regname.setFont(new Font("굴림", Font.BOLD, 12));
		lb_regname.setBounds(152, 10, 26, 15);
		panel_5.add(lb_regname);
		
		txt_regname = new JTextField();
		txt_regname.setBounds(190, 7, 116, 21);
		panel_5.add(txt_regname);
		txt_regname.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lb_regmail = new JLabel("이메일");
		lb_regmail.setFont(new Font("굴림", Font.BOLD, 12));
		lb_regmail.setBounds(139, 10, 39, 15);
		panel_3.add(lb_regmail);
		
		txt_regmail = new JTextField();
		txt_regmail.setBounds(190, 7, 116, 21);
		panel_3.add(txt_regmail);
		txt_regmail.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		btn_regcheck = new JButton("등록");
		btn_regcheck.setFont(new Font("굴림", Font.BOLD, 15));
		btn_regcheck.setBackground(new Color(255, 192, 203));
		panel_1.add(btn_regcheck);
		
		btn_regcancel = new JButton("취소");
		btn_regcancel.setFont(new Font("굴림", Font.BOLD, 15));
		btn_regcancel.setBackground(new Color(255, 192, 203));
		panel_1.add(btn_regcancel);
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setVgap(10);
		contentPane.add(panel_6, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("회원가입");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 17));
		panel_6.add(lblNewLabel);
		
		setVisible(true);
		btn_regcheck.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_regcheck) {
			LoginVO vo = new LoginVO();
		
			vo.setId(txt_regid.getText());
			vo.setPasswd(txt_regpw.getText());
			vo.setName(txt_regname.getText());
			vo.setEmail(txt_regmail.getText());
					
			//db에 입력하기
			int result = ldao.reg(vo);
			
			if(result > 0) {
				System.out.println();
				this.dispose();
				JOptionPane.showMessageDialog(this, "성공");				
			}else {
				JOptionPane.showMessageDialog(this, "실패");
				this.dispose();
			}
		}
	}
	
}