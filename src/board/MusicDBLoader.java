package board;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;

public class MusicDBLoader extends JFrame implements ActionListener, MouseListener{

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JButton btn_DBsearch, btn_download;
	private Vector<MusicVO> vecList;
	private JTable DBTable;
	private DefaultTableModel model;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicDBLoader DBLoader = new MusicDBLoader();
					DBLoader.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public MusicDBLoader() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 424, 252);
		contentPane.add(scrollPane);
		
		String columnName[] = {"title"};
		model = new DefaultTableModel(columnName,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		DBTable = new JTable(model);
		DBTable.addMouseListener(this);
		scrollPane.setViewportView(DBTable);
		
		btn_DBsearch = new JButton("탐색하기");
		btn_DBsearch.addActionListener(this);
		btn_DBsearch.setBounds(435, 3, 97, 23);
		contentPane.add(btn_DBsearch);
		
		btn_download = new JButton("다운로드");
		btn_download.addActionListener(this);
		btn_download.setBounds(435, 36, 97, 23);
		contentPane.add(btn_download);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_DBsearch) {
			MusicDAO dao = new MusicDAO();
			Vector<MusicVO> vecList = dao.getMusicList(0);
			model.addRow(vecList);
			JTable DB_Table = new JTable(model);
			
			scrollPane.setViewportView(list);
		}else if(e.getSource()==btn_download){
			System.out.println(list.getSelected);
		}
		
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
