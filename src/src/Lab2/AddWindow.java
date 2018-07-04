package src.Lab2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.api.jdbc.Statement;

import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class AddWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private int counter = 1;
	private JPanel panel;
	private String name_table;
	private Connection con;
	private Vector<JTextField> textVect;
	private boolean cens = false;

	public AddWindow(java.awt.Frame paret,boolean modal,Connection con, String name_table) throws SQLException {
		super(paret,modal);
		this.con = con;
		this.name_table=name_table;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				panel = new JPanel();
				scrollPane.setViewportView(panel);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[]{47, 370, 0};
				gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
				gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				panel.setLayout(gbl_panel);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						cens=true;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						cens=false;
						setVisible(false);
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		start_init();
	}
	public void start_init() throws SQLException {
		System.out.println(name_table);
		java.sql.Statement myStat= (Statement) con.createStatement();
		ResultSet myRs = myStat.executeQuery("SELECT * FROM "+name_table+";");
		for(int i =1;i <= myRs.getMetaData().getColumnCount() ;i++){
			{
				JLabel lblName = new JLabel(myRs.getMetaData().getColumnName(i));
				GridBagConstraints gbc_lblName = new GridBagConstraints();
				gbc_lblName.insets = new Insets(0, 0, 0, 5);
				gbc_lblName.anchor = GridBagConstraints.EAST;
				gbc_lblName.gridx = 0;
				gbc_lblName.gridy = counter;
				panel.add(lblName, gbc_lblName);
			}
			{
				textField = new JTextField();
				textField.setName(myRs.getMetaData().getColumnName(i));
				
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = 1;
				gbc_textField.gridy = counter;
				panel.add(textField, gbc_textField);
				textField.setColumns(10);
				
			}
			counter++;
		}
	}
	public String[][] returnParam() {
		String[][] asd= new String[1][1];
		String[] sub = new String[2];
		sub[0]=null;
		asd[0]=sub;
		int count = 0;
		int count2 = 0;
		Component[] children = panel.getComponents();
		for (int i=0;i<children.length;i++){
		    if ((children[i] instanceof JTextField) && !(children[i] instanceof JLabel)){
		    	sub = new String[2];
		        String text = ((JTextField)children[i]).getText();
		        if(text.equals("") )continue;
		        if(asd.length == count) {
					asd = Arrays.copyOf(asd, count + 1);
				}
		        sub[0] =((JTextField)children[i]).getName();
		        sub[1] = text;
		        asd[count]=sub;
		        count++;
		    }
		}
		return asd;
	}
	public String add() throws SQLException {
		String[][] res= returnParam();
		Statement myStat= (Statement) con.createStatement();
		if(cens) {
		if(res[0][0] != null) {
		String param="";
		String value="";
		for(String[] p:res) {
			param+=p[0]+",";
			value+="'"+p[1]+"',";
			}
		param=param.substring(0, param.length()-1);
		value=value.substring(0, value.length()-1);
		myStat.execute("insert into "+name_table+" ("+param+") values ("+value+")");
		return "insert into "+name_table.toString()+" ("+param+") values ("+value+")";
		}
		myStat.execute("insert into "+name_table+" () values ()");
		cens=false;
		return "insert into "+name_table.toString()+" () values ()";
		}
		return null;
		}
	public String remove() throws SQLException {
		String[][] res= returnParam();
		Statement myStat= (Statement) con.createStatement();
		if(cens) {
		if(res[0][0] != null) {
		String param="";
		String value="";
		for(String[] p:res) {
			param+=p[0]+"='"+p[1]+"'and ";
			//value+="'"+p[1]+"',";
			}
		param=param.substring(0, param.length()-4);
		//value=value.substring(0, value.length()-1);
		
		myStat.execute("DELETE FROM "+name_table.toString()+" WHERE "+param);
		return "DELETE FROM "+name_table.toString()+" WHERE "+param;
		}
		myStat.execute("DELETE FROM "+name_table.toString());
		cens=false;
		return "DELETE FROM "+name_table.toString();
	}
		return null;
	}
	public JPanel getPanel() {
		return panel;
	}
}
