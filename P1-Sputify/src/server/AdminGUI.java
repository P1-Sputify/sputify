/**
 * 
 */
package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Nethakaaru
 * 
 */
public class AdminGUI extends JFrame implements ActionListener {

	private Controller controller;
	private JTextArea txtArea = new JTextArea();
	private JTextField IPandPort = new JTextField("Server created on IP: " + getIP() + " and port: 57005");
	private JButton toggleServerbtn= new JButton("Turn on server");
	//A boolean that is false if the server is off and true if the server is on.
	private boolean isServerOn=false;

	public AdminGUI(Controller controller) {
		this.controller = controller;
		setupGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void setupGUI() {
		setBounds(100, 100, 800, 300);
		setLayout(new BorderLayout());

		add(txtArea, BorderLayout.CENTER);
		add(IPandPort, BorderLayout.NORTH);
		add(toggleServerbtn,BorderLayout.WEST);
		IPandPort.setEditable(false);
		txtArea.setEditable(false);
	}

	public static void main(String[] args) {
		AdminGUI gui = new AdminGUI(null);

	}

	/**
	 * A method used as a console with information.
	 * 
	 * @param inStr
	 *            the info we wish to add to the "console".
	 */
	public void addToTextArea(String inStr) {
		txtArea.setEditable(true);
		txtArea.append(inStr);
		txtArea.setEditable(false);
	}

	public void setIPandPort(String inStr) {
		IPandPort.setEditable(true);
		IPandPort.setText(inStr);
		IPandPort.setEditable(false);
	}
	public String getIP()  {
		InetAddress host;
		try {
			host = InetAddress.getLocalHost();
			return host.getHostAddress();
		} catch (UnknownHostException e) {
		
			addToTextArea(e.getMessage());
		}
		return "UNKNOWN";
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==toggleServerbtn){
//			if(isServerOn)
//				controller.turnoffserver();
//			else 
//				controller.turnonserver();
//		}
		
	}
}
