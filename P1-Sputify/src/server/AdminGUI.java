/**
 * 
 */
package server;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A class that functions as a simple gui to see if anything goes wrong.
 * @author Nethakaaru
 * 
 */
public class AdminGUI extends JFrame implements ActionListener {

	private Server server;
	private JTextArea taServerMessage = new JTextArea();
	private JTextField IPandPort;
	private JButton toggleServerbtn = new JButton("Server start");
	private JButton showTracksbtn = new JButton("Show Tracks data");
	private JButton showUsersbtn = new JButton("Show Users data");
	private JButton addTrackbtn = new JButton("Add Track");
	private JButton addUserbtn = new JButton("Add User");
	private JPanel pnlMessages = new JPanel();
	private JPanel pnlServer = new JPanel();
	private JPanel pnlDataButtons = new JPanel();
	private JPanel pnlData = new JPanel();
	private JTextArea taData = new JTextArea();
	private boolean isServerOn = false;

	public AdminGUI(Server server) {
		this.server = server;
		setupGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		toggleServerbtn.addActionListener(this);
	}

	public void setupGUI() {
		setLocation(NORMAL, NORMAL);
		setSize(new Dimension(1200, 820));
		setResizable(false);
		setLayout(new GridLayout(4, 1));
		
		IPandPort = new JTextField("Server created on IP: "
				+ server.getIP() + " and port: 57005");
		
		IPandPort.setEditable(false);
		taServerMessage.setEditable(false);

		pnlMessages.setLayout(new GridLayout(1, 2));
		pnlMessages.add(IPandPort);
		pnlMessages.add(toggleServerbtn);
		
		pnlServer.setLayout(new GridLayout(1, 1));
		pnlServer.add(taServerMessage);
		
		pnlDataButtons.setLayout(new GridLayout(2, 2));
		pnlDataButtons.add(showTracksbtn);
		pnlDataButtons.add(showUsersbtn);
		pnlDataButtons.add(addTrackbtn);
		pnlDataButtons.add(addUserbtn);
		
		pnlData.setLayout(new GridLayout(1, 1));
		pnlData.add(taData);
		
		add(pnlMessages);
		add(pnlServer);
		add(pnlDataButtons);
		add(pnlData);
		
	}

	

	/**
	 * A method used as a console with information.
	 * 
	 * @param inStr
	 *            the info we wish to add to the "console".
	 */
	public void appendText(String inStr) {
		taServerMessage.setEditable(true);
		taServerMessage.append(inStr + "\n");
		taServerMessage.setEditable(false);
	}

//	/**
//	 * A method that edits the ip and port text field.
//	 * 
//	 * @param inStr
//	 *            what the ip and port text field should say.
//	 */
//	public void setIPandPort(String inStr) {
//		IPandPort.setEditable(true);
//		IPandPort.setText(inStr);
//		IPandPort.setEditable(false);
//	}



	public void actionPerformed(ActionEvent e) {
		 if(e.getSource()==toggleServerbtn){
			 if(isServerOn){
				 server.serverStop();
				 isServerOn = false;
				 toggleServerbtn.setText("Start server");
			 } else{
				 server.serverStart();
				 isServerOn = true;
				 toggleServerbtn.setText("Stop server");
		 	 }
		 }
	}
	
}
