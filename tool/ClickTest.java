package tool;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClickTest extends JFrame {
	private JButton doClickButton;
	private JPanel panel;

	public static void main(String[] args) {
		ClickTest mainFrame = new ClickTest();
		mainFrame.setTitle("doClick");
	}

	public ClickTest() {
		super();
		this.setVisible(true);
		this.setBounds(10, 10, 400, 200);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(135, 206, 250));
		panel.setBounds(10, 10, 380, 150);
		this.add(panel);
		panel.setLayout(null);

		doClickButton = new JButton("stop");
		doClickButton.setBounds(90, 50, 200, 50);
		panel.add(doClickButton);
		
		doClickButton.doClick(0);
		
		doClickButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println("我想停下来");
				// doClickButton.doClick(10000);
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("我想停下来");
						try {
							Thread.currentThread().sleep(3000L);
						} catch (InterruptedException e) {
							System.out.println("doClick interrupted");
						} finally {
							run();
						}
					}

				}).start();
			}
		});
	}
}