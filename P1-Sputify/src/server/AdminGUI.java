/**
 * 
 */
package server;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A class that functions as a simple gui to see if anything goes wrong.
 * @author Nethakaaru
 * 
 */
public class AdminGUI extends JFrame// implements ActionListener 
{

	private Server server;
	private JTextArea txtArea = new JTextArea();
	private JTextField IPandPort;
	private JButton toggleServerbtn = new JButton("Stop server");
	private JScrollPane scrollPane= new JScrollPane(txtArea);
	private boolean isServerOn = false;

	public AdminGUI(Server server) {
		this.server = server;
		setupGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void setupGUI() {
		setBounds(100, 100, 800, 300);
		setLayout(new BorderLayout());
		IPandPort = new JTextField("Server created on IP: "
				+ server.getIP() + " and port: 57005");
		add(scrollPane, BorderLayout.CENTER);
		add(IPandPort, BorderLayout.NORTH);
//		add(toggleServerbtn, BorderLayout.WEST);
	//	toggleServerbtn.addActionListener(this);
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



//	public void actionPerformed(ActionEvent e) {
//		 if(e.getSource()==toggleServerbtn){
//			 if(isServerOn){
//				 server.serverStop();
//				 isServerOn = false;
//				 toggleServerbtn.setText("Start server");
//			 } else{
//				 server.serverStart();
//				 isServerOn = true;
//				 toggleServerbtn.setText("Stop server");
//		 	 }
//		 }
//	}
//	
}
