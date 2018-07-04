package src.Lab2;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.api.jdbc.Statement;

public class LoaderTable {

	private Connection con;
	private JTabbedPane tabpan;
	
	public LoaderTable(Connection con, JTabbedPane tabpan) {
		this.con = con;
		this.tabpan = tabpan;
	}
	
	public ResultSet loadSql() throws SQLException {
		Statement myStat= (Statement) con.createStatement();
		ResultSet V = myStat.executeQuery("SELECT DATABASE()");
		V.next();
		ResultSet VektName = myStat.executeQuery("SELECT TABLE_NAME, COUNT(*) AS COLUMNS_COUNT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+V.getString(1)+"' GROUP BY TABLE_NAME ");
		return VektName;
	}
	public void CreateTab() {
		ResultSet LoadName = null;
		tabpan.removeAll();
		try {
			 LoadName = loadSql();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while(LoadName.next()) {
				JScrollPane scrol= (new JScrollPane());
				Statement myStat= (Statement) con.createStatement();
				tabpan.addTab(LoadName.getString(1), null,scrol, null);
				JTable table = new JTable();
				scrol.setViewportView(table);
				GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
				gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
				gbc_btnNewButton.gridx = 0;
				gbc_btnNewButton.gridy = 1;
				ResultSet myRs = myStat.executeQuery("SELECT * FROM "+LoadName.getString(1)+";");
				

				Vector<String> sd = new Vector<>();
				for(int i =1;i <= myRs.getMetaData().getColumnCount() ;i++){
					
					sd.add(myRs.getMetaData().getColumnName(i));
				}
				
				DefaultTableModel model = new DefaultTableModel();
				model.setColumnIdentifiers(sd);
				table.setModel(model);
				while(myRs.next()) {
					sd = new Vector<>();
					for(int i =1;i <= myRs.getMetaData().getColumnCount() ;i++){
					sd.add(myRs.getString(i));
					}
					model.addRow(sd);
					}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
