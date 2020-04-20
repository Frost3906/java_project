package login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Register extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField txt_regid;
	private JTextField txt_regpw;
	private JTextField txt_regname;
	private JTextField txt_regmail;


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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lb_regid = new JLabel("ID");
		contentPane.add(lb_regid);
		
		txt_regid = new JTextField();
		contentPane.add(txt_regid);
		txt_regid.setColumns(10);
		
		JLabel lb_regpw = new JLabel("PASSWORD");
		contentPane.add(lb_regpw);
		
		txt_regpw = new JTextField();
		contentPane.add(txt_regpw);
		txt_regpw.setColumns(10);
		
		JLabel lb_regname = new JLabel("이름");
		contentPane.add(lb_regname);
		
		txt_regname = new JTextField();
		contentPane.add(txt_regname);
		txt_regname.setColumns(10);
		
		JLabel lb_regmail = new JLabel("이메일");
		contentPane.add(lb_regmail);
		
		txt_regmail = new JTextField();
		contentPane.add(txt_regmail);
		txt_regmail.setColumns(10);
		
		JButton btn_regcheck = new JButton("완료");
		contentPane.add(btn_regcheck);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
