/**
 * 
 */
package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A class that functions as a simple gui to see if anything goes wrong.
 * @author Nethakaaru
 * 
 */
public class AdminGUI extends JFrame implements ActionListener {

	private Server server;
	private JTextArea txtArea = new JTextArea();
	private JTextField IPandPort;
	private JButton toggleServerbtn = new JButton("Server start");
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
		setBounds(10, 10, 1000, 700);
		setLayout(new BorderLayout());
		IPandPort = new JTextField("Server created on IP: "
				+ server.getIP() + " and port: 57005");
		
		add(IPandPort, BorderLayout.NORTH);
		add(toggleServerbtn, BorderLayout.WEST);
		add(txtArea, BorderLayout.CENTER);
		IPandPort.setEditable(false);
		txtArea.setEditable(false);
	}

	

	/**
	 * A method used as a console with information.
	 * 
	 * @param inStr
	 *            the info we wish to add to the "console".
	 */
	public void appendText(String inStr) {
		txtArea.setEditable(true);
		txtArea.append(inStr + "\n");
		txtArea.setEditable(false);
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
