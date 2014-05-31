/**
 * 
 */
package server;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A class that functions as a simple gui to see if anything goes wrong.
 * @author Sebastian Aspegren.
 * 
 */
public class AdminGUI extends JFrame implements ActionListener {

	private Server server;
	//The text area that prints information about what happens. Basically a console.
	private JTextArea taServerMessage = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(taServerMessage);
	private JTextField IPandPort = new JTextField();
	private JButton toggleServerbtn = new JButton("Start server");
	private JButton showTracksbtn = new JButton("Show Tracks data");
	private JButton showUsersbtn = new JButton("Show Users data");
	private JPanel pnlMessages = new JPanel();
	private JPanel pnlServer = new JPanel();
	private JPanel pnlDataButtons = new JPanel();
	private JPanel pnlData = new JPanel();
	//The text area that displays information regarding users or tracks depending on what you click on.
	private JTextArea taData = new JTextArea();
	private JScrollPane taDatascrollPane = new JScrollPane(taData);
	private boolean isServerOn = false;
/**
 * The constructor for the gui. It sets up basic things like the visibility of the gui.
 * @param server	
 * 				 A reference to the server which we are going to use to call methods in the server.
 */
	public AdminGUI(Server server) {
		this.server = server;
		setupGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
/**
 * A method used in the constructor. It sets up everything regarding the gui. 
 * This is in a separate method to easier separate everything.
 */
	public void setupGUI() {
		setLocation(NORMAL, NORMAL);
		setSize(new Dimension(1200, 820));
		setResizable(false);
		setLayout(new GridLayout(4, 1));
		
		IPandPort.setText("Server created on IP: " + server.getIP() + " and port: 57005");
		
		IPandPort.setEditable(false);
		taServerMessage.setEditable(false);

		pnlMessages.setLayout(new GridLayout(1, 2));
		pnlMessages.add(IPandPort);
		pnlMessages.add(toggleServerbtn);
		
		pnlServer.setLayout(new GridLayout(1, 1));
		pnlServer.add(scrollPane);
		
		pnlDataButtons.setLayout(new GridLayout(1, 2));
		pnlDataButtons.add(showTracksbtn);
		pnlDataButtons.add(showUsersbtn);
		
		pnlData.setLayout(new GridLayout(1, 1));
		pnlData.add(taDatascrollPane);
		
		add(pnlMessages);
		add(pnlServer);
		add(pnlDataButtons);
		add(pnlData);
		
		toggleServerbtn.addActionListener(this);
		showTracksbtn.addActionListener(this);
		showUsersbtn.addActionListener(this);
		
	}
	

	/**
	 * A method used to write text to the text area taServerMessage.
	 * 
	 * @param inStr
	 *            the text we wish to add to the "console".
	 */
	public void appendText(String inStr) {
		taServerMessage.append(inStr + "\n");
		taServerMessage.setCaretPosition(taServerMessage.getDocument().getLength());
	}
	/**
	 * @return the text area taData.
	 */
	public JTextArea getTaData() {
		return taData;
	}

	/**
	 * @param taData the text area taData should become.
	 */
	public void setTaData(JTextArea taData) {
		this.taData = taData;
	}
	
/**
 * The actionPerformed method. You can turn on/off the server. show all tracks or users.
 */
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource()==toggleServerbtn){
			 if(isServerOn){
				 server.serverStop();
			 } else{
				 server.serverStart();
				 isServerOn = true;
				 toggleServerbtn.setText("Stäng server");
		 	 }
		 }
		 else if(e.getSource()==showTracksbtn) {
			 server.updateDataPanelWithTracks();
		 }
		 else if(e.getSource()==showUsersbtn) {
			 server.updateDataPanelWithUsers();
		 }
	}


}
