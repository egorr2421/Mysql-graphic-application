package src.Lab2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class init extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textUrl;
	private JTextField textName;
	private JTextField textPass;
	private boolean retu = false;
	private boolean pars = false;
	private String url;
	private String username;
	private String password;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			init dialog = new init(null,true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public init(java.awt.Frame paret,boolean modal) {
		super(paret,modal);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{63, 185, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblUrl = new JLabel("URL");
			GridBagConstraints gbc_lblUrl = new GridBagConstraints();
			gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
			gbc_lblUrl.anchor = GridBagConstraints.EAST;
			gbc_lblUrl.gridx = 0;
			gbc_lblUrl.gridy = 0;
			contentPanel.add(lblUrl, gbc_lblUrl);
		}
		{
			textUrl = new JTextField();
			textUrl.setText("jdbc:mysql://localhost:3306/mydbtest?serverTimezone=UTC");
			GridBagConstraints gbc_textUrl = new GridBagConstraints();
			gbc_textUrl.insets = new Insets(0, 0, 5, 0);
			gbc_textUrl.fill = GridBagConstraints.HORIZONTAL;
			gbc_textUrl.gridx = 1;
			gbc_textUrl.gridy = 0;
			contentPanel.add(textUrl, gbc_textUrl);
			textUrl.setColumns(10);
		}
		{
			JLabel lblName = new JLabel("Name");
			GridBagConstraints gbc_lblName = new GridBagConstraints();
			gbc_lblName.insets = new Insets(0, 0, 5, 5);
			gbc_lblName.anchor = GridBagConstraints.EAST;
			gbc_lblName.gridx = 0;
			gbc_lblName.gridy = 1;
			contentPanel.add(lblName, gbc_lblName);
		}
		{
			textName = new JTextField();
			textName.setText("root");
			GridBagConstraints gbc_textName = new GridBagConstraints();
			gbc_textName.insets = new Insets(0, 0, 5, 0);
			gbc_textName.fill = GridBagConstraints.HORIZONTAL;
			gbc_textName.gridx = 1;
			gbc_textName.gridy = 1;
			contentPanel.add(textName, gbc_textName);
			textName.setColumns(10);
		}
		{
			JLabel lblPasswd = new JLabel("Passwd");
			GridBagConstraints gbc_lblPasswd = new GridBagConstraints();
			gbc_lblPasswd.insets = new Insets(0, 0, 0, 5);
			gbc_lblPasswd.anchor = GridBagConstraints.EAST;
			gbc_lblPasswd.gridx = 0;
			gbc_lblPasswd.gridy = 2;
			contentPanel.add(lblPasswd, gbc_lblPasswd);
		}
		{
			textPass = new JTextField();
			textPass.setText("root");
			GridBagConstraints gbc_textPass = new GridBagConstraints();
			gbc_textPass.fill = GridBagConstraints.HORIZONTAL;
			gbc_textPass.gridx = 1;
			gbc_textPass.gridy = 2;
			contentPanel.add(textPass, gbc_textPass);
			textPass.setColumns(10);
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
						retu=true;
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
					public void mouseClicked(MouseEvent e) {
						retu=false;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public Connection ret() {

		if(retu) {
		try {

			 if(!pars) {
			 url = textUrl.getText();
			 username= textName.getText();
			 password = textPass.getText();
			 }else {
				 pars=!pars;
			 }
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element Conect = document.createElement("Conect");
			Conect.setAttribute("url",url);
			Conect.setAttribute("username", username);
			Conect.setAttribute("password", password);
			document.appendChild(Conect);
			
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			t.transform(new DOMSource(document), new StreamResult(new FileOutputStream("save.xml")));
			StreamResult console = new StreamResult(System.out);
			t.transform(source, console);
			System.out.println("sd");
			
			
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
			return conn;
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}
		}else {
			return null;
		}
		
	}
	public void initPar(String url,String passwd,String user) {
		this.username=user;
		this.password=passwd;
		this.url=url;
		retu = true;
		pars=true;
	}

}
