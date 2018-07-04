package src.Lab2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.api.jdbc.Statement;

//import com.mysql.cj.api.jdbc.Statement;

import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;




public class EdWindows extends JDialog {
	
	private String[][] parameters;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JComboBox comboBox;
	private String name_table;
	private Connection con;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public EdWindows(String[][] parameters,java.awt.Frame paret,boolean modal,Connection con, String name_table) throws SQLException {
		super(paret,modal);
		this.con = con;
		this.name_table=name_table;
		this.parameters = parameters;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			comboBox = new JComboBox();
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 0, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 0;
			gbc_comboBox.gridy = 0;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		{
			textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy = 0;
			contentPanel.add(textField, gbc_textField);
			textField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
					
						
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		start_init();
	}

	public JComboBox getComboBox() {
		return comboBox;
	}
	public void start_init() throws SQLException {
		comboBox.setEditable(true);
		Statement myStat= (Statement) con.createStatement();
		ResultSet myRs = myStat.executeQuery("SELECT * FROM "+name_table+";");
		for(int i =1;i <= myRs.getMetaData().getColumnCount() ;i++){
			comboBox.addItem(myRs.getMetaData().getColumnName(i));
		}
	}
	public String[] returnParam() {
		String[] asd = new String[2];
		asd[0]=(String) comboBox.getItemAt(comboBox.getSelectedIndex());
		asd[1]=(String) textField.getText();
		return asd;
	}
	public String edit() throws SQLException {
		Statement myStat= (Statement) con.createStatement();
		if(parameters[0][0]!=null) {
			String param="";
			for(String[] p:parameters) {
				param+=p[0]+"='"+p[1]+"'and ";
				//value+="'"+p[1]+"',";
				}
			param=param.substring(0, param.length()-4);
			myStat.execute("UPDATE "+name_table+" SET "+comboBox.getItemAt(comboBox.getSelectedIndex())+" = '"+textField.getText()+"' WHERE "+param);
			return "UPDATE "+name_table+" SET "+comboBox.getItemAt(comboBox.getSelectedIndex())+" = '"+textField.getText()+"' WHERE "+param;
		}
		return null;
	}
}
