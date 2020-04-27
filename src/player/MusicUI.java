package player;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class MusicUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton playbtn,stopbtn,nextbtn,randombtn,prbtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicUI frame = new MusicUI();
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
	public MusicUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 142, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 129, 186);
		contentPane.add(panel);
		panel.setLayout(null);
		
		playbtn = new JButton("play");
		playbtn.setBounds(15, 18, 97, 23);
		panel.add(playbtn);
		
		stopbtn = new JButton("stop");
		stopbtn.setBounds(15, 51, 97, 23);
		panel.add(stopbtn);
		
		nextbtn = new JButton("next");
		nextbtn.setBounds(15, 84, 97, 23);
		panel.add(nextbtn);
		
		randombtn = new JButton("random");
		randombtn.setBounds(15, 150, 97, 23);
		panel.add(randombtn);
		
		prbtn = new JButton("previous");
		prbtn.setBounds(15, 117, 97, 23);
		panel.add(prbtn);
		
		playbtn.addActionListener(this);
		nextbtn.addActionListener(this);
		prbtn.addActionListener(this);
		randombtn.addActionListener(this);
		stopbtn.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		haMelGomPotFunc ha = new haMelGomPotFunc();
		int a=0;
		
		
		
	}
}






















