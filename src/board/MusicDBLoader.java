package board;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.mysql.cj.jdbc.Blob;

import ui.RoundedButton;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.Frame;

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
		setTitle("다운로드");
		setBounds(100, 100, 553, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 240, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 424, 252);
		scrollPane.getViewport().setBackground(new Color(245, 255, 250));
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
		
		btn_DBsearch = new RoundedButton("탐색하기");
		btn_DBsearch.setFont(new Font("굴림", Font.BOLD, 15));
		btn_DBsearch.addActionListener(this);
		btn_DBsearch.setBounds(435, 201, 97, 23);
		btn_DBsearch.setBackground(new Color(70, 130, 180));
		btn_DBsearch.setForeground(new Color(255, 240, 245));
		contentPane.add(btn_DBsearch);
		
		btn_download = new RoundedButton("다운로드");
		btn_download.setFont(new Font("굴림", Font.BOLD, 15));
		btn_download.addActionListener(this);
		btn_download.setBounds(435, 234, 97, 23);
		btn_download.setBackground(new Color(70, 130, 180));
		btn_download.setForeground(new Color(255, 240, 245));
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
