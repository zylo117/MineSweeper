package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Window.Type;

public class popUpResult {

	public static JLabel result = new JLabel("还是要提高自己的姿势水平啊");
	public JDialog frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					popUpResult window = new popUpResult();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public popUpResult() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog(GameUI.frame, "人生的经验");
		frame.setType(Type.POPUP);
		frame.setBounds(50, 200, 400, 100);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setAlwaysOnTop(true);
		result.setHorizontalAlignment(SwingConstants.CENTER);
		
		frame.getContentPane().add(result, BorderLayout.CENTER);
		frame.getContentPane().add(result);
	}

}
