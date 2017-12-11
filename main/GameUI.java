package main;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import tool.MyButtonUI;
import tool.Point;

public class GameUI {

	public static int ttlRow = 10;
	public static int ttlCol = 10;
	public static int mineQTY = 10; // 建议不超过总数的10分之1
	public static boolean debug = true;
	public static JButton[][] buttonSet = new JButton[ttlRow][ttlCol];
	public static int clickCount = 0;
	public static Point firstPoint;
	public static JButton currentButton;
	public static boolean initFinished = false;
	public static boolean ifDeployed = false;

	public static int[][] statusMap = new int[999][999];
	/**
	 * 状态图示意 
	 * -1，未点击，空，安全 
	 * 0，已点击/未点击，空，安全 
	 * 1~8，已点击，附近有1~8个雷 
	 * 9，点击，雷。炸了
	 * 10,右键点击标记雷
	 */

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				final GameUI window = new GameUI();
				window.frame.setVisible(true);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 30 * ttlCol, 30 * ttlRow);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 设置行列数
		frame.getContentPane().setLayout(new GridLayout(ttlRow, ttlCol, 0, 0));

		for (int r = 0; r < ttlRow; r++) {
			for (int c = 0; c < ttlCol; c++) {
				statusMap[r][c] = -1;

				buttonSet[r][c] = new JButton();

				frame.getContentPane().add(buttonSet[r][c]);

				buttonSet[r][c].setUI(new MyButtonUI());
				final ImageIcon ico = new ImageIcon(System.getProperty("user.dir") + "\\res\\dirt_huaji.jpg");
				ico.setImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
				buttonSet[r][c].setIcon(ico);

				// 对左键响应
				buttonSet[r][c].addActionListener(new LeftClick());

				// 对右键响应
				buttonSet[r][c].addMouseListener(new RightClick());
			}
		}
		initFinished = true;

	}
}
