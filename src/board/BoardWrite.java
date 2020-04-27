package board;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import login.LoginVO;
import ui.RoundedButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.FlowLayout;

public class BoardWrite extends JFrame implements ActionListener{

	private JPanel contentPane;
	private BoardPanel board;
	private JTextField txt_main;
	private JButton btn_back;
	private JButton btn_write;
	private JTextArea txt_content;
	private JLabel la_id;
	private static LoginVO vo;
	private int result;
	private JLabel lblNewLabel_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardWrite frame = new BoardWrite(vo);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BoardWrite(LoginVO vo) {
		this.vo=vo;
		la_id  = new JLabel(vo.getId());
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		setTitle("게시글 작성");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("제목");
		panel.add(lblNewLabel);
		
		txt_main = new JTextField();
		panel.add(txt_main);
		txt_main.setColumns(10);
		
		lblNewLabel_1 = new JLabel("          작성자 : ");
		panel.add(lblNewLabel_1);
		
		panel.add(la_id);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		btn_write = new RoundedButton("게시하기");
		panel_1.add(btn_write);
		
		btn_back = new RoundedButton("돌아가기");
		panel_1.add(btn_back);
		
		 txt_content = new JTextArea();
		 txt_content.setLineWrap(true);
		 txt_content.setWrapStyleWord(true);
		contentPane.add(txt_content, BorderLayout.CENTER);
		btn_back.addActionListener(this);
		btn_write.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		
		
		
		
		
		if(btn == btn_write) {
			BoardVO bo = new BoardVO();
			BoardDAO dao = new BoardDAO();
			bo.setContentname(txt_main.getText());
			bo.setContent(txt_content.getText());
			bo.setWriter(la_id.getText());
			result = dao.write(bo);
			
			if(result !=0) {
				JOptionPane.showMessageDialog(this, "글이 등록되었습니다.", "게시글 작성",JOptionPane.INFORMATION_MESSAGE);

				dispose();
			}else {
				JOptionPane.showMessageDialog(this, "글등록이 실패하였습니다.", "게시글 작성",JOptionPane.WARNING_MESSAGE);
				
			}

			
		}
		
		if(btn == btn_back) {
			dispose();
		}
		
		
	}
	

	


}
