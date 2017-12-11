package main;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import tool.MyButtonUI;
import tool.Point;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

import java.sql.Time;
import java.util.Timer;

import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GameUI {

	public static JButton reset = new JButton("Reset");
	public static int ttlRow = 10;
	public static int ttlCol = 10;
	public static int mineQTY = 5; // 建议不超过总数的10分之1
	public static boolean debug = false;
	public static JButton[][] buttonSet = new JButton[ttlRow][ttlCol];
	public static int clickCount = 0;
	public static Point firstPoint;
	public static JButton currentButton;
	public static boolean initFinished = false;
	public static boolean ifDeployed = false;
	public static boolean ifReset = false;
	public static int blockSize = 30;

	public static int[][] statusMap = new int[999][999];
	/**
	 * 状态图示意 
	 * -1，未点击，空，安全 
	 * 0，已点击，空，安全 
	 * 1~8，已点击，附近有1~8个雷 
	 * 9，雷。点击就炸了 
	 * 10,右键点击标记雷
	 */

	public static int markChance = mineQTY;
	public static int[][] markMap = new int[999][999];
	/**
	 * 状态图示意 
	 * 0，未标记 
	 * 1，已标记
	 */

	JFrame frame;
	JTextField rowBox;
	JTextField colBox;
	JTextField mineBox;

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
	 * 
	 * @wbp.parser.entryPoint
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setBounds(100, 100, blockSize * ttlCol + 10, blockSize * ttlRow + 130);

		// 设置行列数

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setBounds(0, 0, blockSize * ttlCol + 10, blockSize * ttlRow + 130);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel function = new JPanel();
		panel.add(function, BorderLayout.NORTH);
		function.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel_2 = new JPanel();
		function.add(panel_2);

		JLabel lblDifficulty = new JLabel("Difficulty");
		panel_2.add(lblDifficulty);

		JPanel panel_3 = new JPanel();
		function.add(panel_3);

		JLabel lblRows = new JLabel("Rows");
		panel_3.add(lblRows);

		rowBox = new JTextField();
		panel_3.add(rowBox);
		rowBox.setColumns(2);
		rowBox.setText(String.valueOf(ttlRow));

		JLabel lblCols = new JLabel("Cols");
		panel_3.add(lblCols);

		colBox = new JTextField();
		panel_3.add(colBox);
		colBox.setColumns(2);
		colBox.setText(String.valueOf(ttlCol));

		JLabel lblMines = new JLabel("Mines");
		panel_3.add(lblMines);

		mineBox = new JTextField();
		panel_3.add(mineBox);
		mineBox.setColumns(2);
		mineBox.setText(String.valueOf(mineQTY));

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, blockSize * ttlCol, blockSize * ttlRow);
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(ttlRow, ttlCol, 0, 0));

		JPanel statusBar = new JPanel();
		panel.add(statusBar, BorderLayout.SOUTH);

		JLabel lblTip = new JLabel("Fuck You");
		statusBar.add(lblTip);

		JComboBox comboBox = new JComboBox();
		panel_2.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Easy", "Medium", "Hard", "Custom" }));
		comboBox.addItemListener(new SelectBox(this, comboBox));
		
		panel_2.add(reset);
		reset.addActionListener(new ResetGame(this, panel_1, panel));

		// 初始化
		init(panel_1);

	}

	void init(JPanel panel_1) {
		// 开始初始化
		for (int r = 0; r < ttlRow; r++) {
			for (int c = 0; c < ttlCol; c++) {
				statusMap[r][c] = -1;

				buttonSet[r][c] = new JButton();

				panel_1.add(buttonSet[r][c]);
				buttonSet[r][c].setUI(new MyButtonUI());
				final ImageIcon ico = new ImageIcon(GameUI.class.getResource("/res/dirt_huaji.jpg"));
				ico.setImage(ico.getImage().getScaledInstance(blockSize, blockSize, Image.SCALE_SMOOTH));
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
