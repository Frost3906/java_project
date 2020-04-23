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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class BoardWrite extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField txt_main;
	private JButton btn_back;
	private JButton btn_write;
	private JTextArea txt_content;
	private JLabel la_id = new JLabel("testid");
	private LoginVO vo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardWrite frame = new BoardWrite();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BoardWrite() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setTitle("게시글 작성");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("제목");
		panel.add(lblNewLabel);
		
		txt_main = new JTextField();
		panel.add(txt_main);
		txt_main.setColumns(10);
		
		panel.add(la_id);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		btn_write = new JButton("게시하기");
		panel_1.add(btn_write);
		
		btn_back = new JButton("돌아가기");
		panel_1.add(btn_back);
		
		 txt_content = new JTextArea();
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
			int result = dao.write(bo);
			
			if(result !=0) {
				JOptionPane.showMessageDialog(this, "글이 등록되었습니다.", "게시글 작성",JOptionPane.INFORMATION_MESSAGE);
				Board board = new Board();
				board.refresh();
				board.getvo(vo);
				dispose();
			}else {
				JOptionPane.showMessageDialog(this, "글등록이 실패하였습니다.", "게시글 작성",JOptionPane.WARNING_MESSAGE);
				
			}

			
		}
		
		if(btn == btn_back) {
			Board board = new Board();
			board.refresh();
			board.getvo(vo);
			dispose();
		}
		
		
	}
	
	public void getvo(LoginVO vo) {
		this.vo = vo;
		la_id.setText(vo.getId());
		
	}

}
