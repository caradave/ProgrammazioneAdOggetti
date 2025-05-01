package social;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class SocialGui extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;

	// The following components are declared public
	// in order to allow testing the user interface
	
	/**
	 * The code of the person to log in
	 */
	public JTextField id ;
	
	/**
	 * The button to perform login
	 */
	public JButton login ;
	
	/**
	 * The label that shall contain the info
	 * of the logged in person 
	 */
	public JLabel name ;
	
	/**
	 * The list of friends of the person
	 * that is logged in
	 */
	public JList<String> friends ;
	

	public SocialGui(Social m){
		setTitle("Socila");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLayout(new BorderLayout());
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout());
		
		JLabel idLabel = new JLabel("ID: ");
		JTextField searchField = new JTextField(20);
		JButton searchButton = new JButton("Search");
		
		searchPanel.add(idLabel);
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		JTextArea nameArea = new JTextArea();
		
		namePanel.add(nameArea);
		
		JTextArea resultArea = new JTextArea();
		resultArea.setEditable(false);
	
		JPanel navPanel = new JPanel();
		navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
		navPanel.add(searchPanel);
		navPanel.add(namePanel);
		
		add(navPanel,  BorderLayout.NORTH);
		add(new JScrollPane(resultArea), BorderLayout.CENTER);
		
		searchButton.addActionListener(e -> {
			String searchTerm = searchField.getText();
			String searchResult = null;
			try {
				searchResult = m.getPerson(searchTerm).split(" ")[1] + " " + m.getPerson(searchTerm).split(" ")[2];
						
			}catch(NoSuchCodeException e1) {
				JOptionPane.showMessageDialog(null,  "User code invalid", "login Error", JOptionPane.ERROR_MESSAGE);
			}
			nameArea.setText(searchResult);
		});
	
	
	}


}
