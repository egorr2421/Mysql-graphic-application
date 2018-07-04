package src.Lab2;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.mysql.cj.api.jdbc.Statement;
import com.sun.xml.internal.bind.v2.runtime.Name;


import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Mainview extends Frame{

	private JFrame frame;
	private JButton btnNewButton;
	private DefaultTableModel model;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private init in = new init(this,true);
	private Connection conect=null;
	private JTabbedPane tabbedPane;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTable table;
	private JTable table_1;
	private Mainview p = this;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainview window = new Mainview();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mainview() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 73, 73, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				AddWindow addWin=null;
				try {
					addWin = new AddWindow(p , true, conect, tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				addWin.setVisible(true);
				try {
					addWin.start_init();
					System.out.println(addWin.remove());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LoaderTable Ltable = new LoaderTable(conect, tabbedPane);
				Ltable.CreateTab();
			}
		});
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
			}
		});
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 5;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		scrollPane = new JScrollPane();
		tabbedPane.addTab("New tab", null, scrollPane, null);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		
		scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("s", null, scrollPane_1, null);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		btnNewButton_1 = new JButton("add");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				AddWindow addWin=null;
				try {
					addWin = new AddWindow(p , true, conect, tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				addWin.setVisible(true);
				try {
					addWin.start_init();
					System.out.println(addWin.add());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LoaderTable Ltable = new LoaderTable(conect, tabbedPane);
				Ltable.CreateTab();
				}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 1;
		frame.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		
		btnNewButton_2 = new JButton("Init");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Sd");
				in.setVisible(true);
				 
				if(in.ret() == null) {
					System.out.println("null");
					return;
				}else {
					conect=in.ret();
					System.out.println("con");
				
				}
				Statement myStat=null;
				try {
					 myStat= (Statement) conect.createStatement();
				} catch (SQLException e1) {
				}
				
				LoaderTable Ltable = new LoaderTable(conect, tabbedPane);
				Ltable.CreateTab();
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_2.gridx = 2;
		gbc_btnNewButton_2.gridy = 1;
		frame.getContentPane().add(btnNewButton_2, gbc_btnNewButton_2);
		
		btnNewButton_3 = new JButton("Edit");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddWindow addWin=null;
				try {
					addWin = new AddWindow(p , true, conect, tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				addWin.setVisible(true);
				EdWindows edwin = null;
				try {
					addWin.start_init();
					//System.out.println(addWin.returnParam());
					edwin = new EdWindows(addWin.returnParam(),p , true, conect, tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
					for(String[] p :addWin.returnParam()) {
						for(String s: p) {
							System.out.println(s);
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				edwin.setVisible(true);
				try {
					System.out.println(edwin.edit());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				LoaderTable Ltable = new LoaderTable(conect, tabbedPane);
				Ltable.CreateTab();
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_3.gridx = 3;
		gbc_btnNewButton_3.gridy = 1;
		frame.getContentPane().add(btnNewButton_3, gbc_btnNewButton_3);
		
		btnNewButton_4 = new JButton("Last con");
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				File file = new File("save.xml");
				DocumentBuilder buider= null;
				try {
					 buider = factory.newDocumentBuilder();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Document document = null;
				try {
					document=buider.parse(file);
				} catch (SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Element el =  document.getDocumentElement();
				System.out.println(el.getAttribute("url"));
				System.out.println(el.getAttribute("username"));
				System.out.println(el.getAttribute("password"));
				in.initPar(el.getAttribute("url"), el.getAttribute("password"), el.getAttribute("username"));
				conect=in.ret();
				if(conect ==null) {
					System.out.println("null");
					return;
				}else {
					System.out.println("con");
				
				}
				Statement myStat=null;
				try {
					 myStat= (Statement) conect.createStatement();
				} catch (SQLException e1) {
				}
				
				LoaderTable Ltable = new LoaderTable(conect, tabbedPane);
				Ltable.CreateTab();
			}
		});
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.gridx = 4;
		gbc_btnNewButton_4.gridy = 1;
		frame.getContentPane().add(btnNewButton_4, gbc_btnNewButton_4);
	}

	public JTable getTable() {
		return table;
	}
	public JButton getBtnNewButton() {
		return btnNewButton;
	}

}
