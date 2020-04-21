package board;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class BoardViewContent extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btn_cancel;
	private JTextField txt_main;
	private JTextField txt_writer;
	private JTextArea txt_content;
	private JButton btn_rewrite;
	private JLabel la_boardno;
	private JPanel panel_1;
	private JButton btn_endwrite = new JButton("수정 완료");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardViewContent frame = new BoardViewContent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BoardViewContent() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setTitle("게시판");
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("글제목 : ");
		panel.add(lblNewLabel);
		
		txt_main = new JTextField();
		panel.add(txt_main);
		txt_main.setColumns(32);
		
		JLabel lblNewLabel_4 = new JLabel("                               ");
		panel.add(lblNewLabel_4);
		
		 la_boardno = new JLabel("New label");
		panel.add(la_boardno);
		
		 panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		 btn_cancel = new JButton("뒤로가기");
		 btn_cancel.addActionListener(this);
		
		JLabel lblNewLabel_2 = new JLabel("작성자 : ");
		panel_1.add(lblNewLabel_2);
		
		txt_writer = new JTextField();
		txt_writer.setColumns(23);
		panel_1.add(txt_writer);
		panel_1.add(btn_cancel);
		
		JLabel lblNewLabel_3 = new JLabel("                         ");
		panel_1.add(lblNewLabel_3);
		
		 btn_rewrite = new JButton("글 수정");
		panel_1.add(btn_rewrite);
		btn_rewrite.addActionListener(this);
		
		JLabel lblNewLabel_1 = new JLabel("글내용");
		contentPane.add(lblNewLabel_1, BorderLayout.WEST);
		
		 txt_content = new JTextArea();
		contentPane.add(txt_content, BorderLayout.CENTER);
		txt_content.setEditable(false);
		txt_main.setEditable(false);
		btn_endwrite.addActionListener(this);
	}
	
	public void textfill(int boardno) {
		BoardVO vo = new BoardVO();
		BoardDAO dao = new BoardDAO();
		vo=dao.view(boardno);
		txt_main.setText(vo.getContentname());
		txt_content.setText(vo.getContent());
		la_boardno.setText(vo.getBoardno()+"");
//		txt_writer.setText(vo2.getId());
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if(btn == btn_cancel) {
		dispose();
		}
		
		if(btn==btn_rewrite) {
			txt_content.setEditable(true);
			txt_main.setEditable(true);
		btn_rewrite.setVisible(false);
		panel_1.add(btn_endwrite);
		}
		
		if(btn==btn_endwrite) {
			BoardVO vo = new BoardVO();
			BoardDAO dao = new BoardDAO();
		vo.setContent(txt_content.getText());
		vo.setContentname(txt_main.getText());
		vo.setBoardno(Integer.parseInt(la_boardno.getText()));
		int result = dao.modify(vo);
		if(result !=0) {
			JOptionPane.showMessageDialog(this, "글이 수정되었습니다.", "게시글 수정",JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this, "수정이 실패하였습니다.", "게시글 수정",JOptionPane.WARNING_MESSAGE);
		}
		btn_rewrite.setVisible(true);
		btn_endwrite.setVisible(false);
		}
		
	}

}
