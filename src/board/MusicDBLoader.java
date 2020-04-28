package board;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.Blob;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class MusicDBLoader extends JFrame implements ActionListener, MouseListener{

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JButton btn_DBsearch, btn_download;
	private JTable DB_Table;
	private DefaultTableModel model;
	private Vector<MusicVO> vecList;
	private JFileChooser chooser;
	private Blob blob;


	
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
		
		DB_Table = new JTable(model);
		DB_Table.addMouseListener(this);
		scrollPane.setViewportView(DB_Table);
		
		btn_DBsearch = new JButton("탐색하기");
		btn_DBsearch.addActionListener(this);
		btn_DBsearch.setBounds(435, 3, 97, 23);
		contentPane.add(btn_DBsearch);
		
		btn_download = new JButton("다운로드");
		btn_download.addActionListener(this);
		btn_download.setBounds(435, 36, 97, 23);
		contentPane.add(btn_download);
		
	}
	private JFileChooser getChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("mp3 file(*.mp3)","mp3"));
		
		chooser.setSelectedFile(new File(vecList.get(DB_Table.getSelectedRow()).getTitle()));
		
		
		return chooser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_DBsearch) {
			model.setRowCount(0);
			MusicDAO dao = new MusicDAO();
			vecList = dao.getMusicList();
			for (MusicVO musicvo : vecList) {
				Object[] objList = {musicvo.getTitle(),musicvo.getBlob()};
				model.addRow(objList);
			}
			DB_Table = new JTable(model);
			
			scrollPane.setViewportView(DB_Table);
		}else if(e.getSource()==btn_download){
			blob= vecList.get(DB_Table.getSelectedRow()).getBlob();
			chooser = getChooser();
			int retVal = chooser.showSaveDialog(this);
			
			if(retVal==0) {
				File file = chooser.getSelectedFile();
				if(file.getPath().lastIndexOf(".")<0) {
					file = new File(file.getPath()+".mp3");
				}
				try (InputStream inputStream = blob.getBinaryStream();
					OutputStream outputStream = new FileOutputStream(file.getPath())
							){
					
		            int bytesRead = -1;
		            byte[] buffer = new byte[1024];
		            while ((bytesRead = inputStream.read(buffer)) != -1) {
		                outputStream.write(buffer, 0, bytesRead);
		            }

				} catch (Exception e2) {
					e2.printStackTrace();
				}
		
			}else return;
					

		
		}
		
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2) {
			blob= vecList.get(DB_Table.getSelectedRow()).getBlob();
			chooser = getChooser();
			int retVal = chooser.showSaveDialog(this);
			
			if(retVal==0) {
				File file = chooser.getSelectedFile();
				if(file.getPath().lastIndexOf(".")<0) {
					file = new File(file.getPath()+".mp3");
				}
				try (InputStream inputStream = blob.getBinaryStream();
					OutputStream outputStream = new FileOutputStream(file.getPath())
							){
					
		            int bytesRead = -1;
		            byte[] buffer = new byte[1024];
		            while ((bytesRead = inputStream.read(buffer)) != -1) {
		                outputStream.write(buffer, 0, bytesRead);
		            }

				} catch (Exception e2) {
					e2.printStackTrace();
				}
		
			}else return;
			}
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
