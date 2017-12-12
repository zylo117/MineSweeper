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
import java.util.Date;
import java.util.Timer;

import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class GameUI {

	public static JLabel lblTip = new JLabel("Excited");
	public static JLabel lblTime = new JLabel("    Time");
	public static Date firstTime;
	public static int ttlSecond = 0;
	public static Thread tips = new Thread();
	public static JButton reset = new JButton("Reset");
	public static int ttlRow = 10;
	public static int ttlCol = 10;
	public static int mineQTY = 5; // 建议不超过总数的10分之1
	public static boolean debug = false;
	public static JButton[][] buttonSet = new JButton[ttlRow][ttlCol];
	public static int clickCount = 0;
	public static Point firstPoint = new Point(-1, -1);
	public static JButton currentButton;
	public static boolean initFinished = false;
	public static boolean ifDeployed = false;
	public static boolean ifReset = false, ifFinish = false;
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

	public static JFrame frame;
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
		frame.setTitle("\u819C\u6CD5\u626B\u96F7");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GameUI.class.getResource("/res/ico.png")));
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
		lblDifficulty.setFont(new Font("Calibri", Font.PLAIN, 12));
		panel_2.add(lblDifficulty);

		JPanel panel_3 = new JPanel();
		function.add(panel_3);

		JLabel lblRows = new JLabel("Rows");
		lblRows.setFont(new Font("Calibri", Font.PLAIN, 12));
		panel_3.add(lblRows);

		rowBox = new JTextField();
		rowBox.setFont(new Font("Calibri", Font.PLAIN, 12));
		panel_3.add(rowBox);
		rowBox.setColumns(2);
		rowBox.setText(String.valueOf(ttlRow));

		JLabel lblCols = new JLabel("Cols");
		lblCols.setFont(new Font("Calibri", Font.PLAIN, 12));
		panel_3.add(lblCols);

		colBox = new JTextField();
		colBox.setFont(new Font("Calibri", Font.PLAIN, 12));
		panel_3.add(colBox);
		colBox.setColumns(2);
		colBox.setText(String.valueOf(ttlCol));

		JLabel lblMines = new JLabel("Mines");
		lblMines.setFont(new Font("Calibri", Font.PLAIN, 12));
		panel_3.add(lblMines);

		mineBox = new JTextField();
		mineBox.setFont(new Font("Calibri", Font.PLAIN, 12));
		panel_3.add(mineBox);
		mineBox.setColumns(2);
		mineBox.setText(String.valueOf(mineQTY));

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, blockSize * ttlCol, blockSize * ttlRow);
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(ttlRow, ttlCol, 0, 0));

		JPanel statusBar = new JPanel();
		FlowLayout flowLayout = (FlowLayout) statusBar.getLayout();
		flowLayout.setVgap(2);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(statusBar, BorderLayout.SOUTH);
		
		JLabel laji = new JLabel("");
		laji.setIcon(new ImageIcon(GameUI.class.getResource("/res/ico.png")));
		laji.setHorizontalAlignment(SwingConstants.CENTER);
		statusBar.add(laji);
		lblTip.setFont(new Font("微软雅黑", Font.PLAIN, 11));

		statusBar.add(lblTip);
		dailyMojic(lblTip);

		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Calibri", Font.PLAIN, 12));
		panel_2.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Easy", "Medium", "Hard", "Custom" }));
		comboBox.addItemListener(new SelectBox(this, comboBox));
		reset.setFont(new Font("Calibri", Font.PLAIN, 12));
		
		panel_2.add(reset);
		lblTime.setFont(new Font("Calibri", Font.PLAIN, 12));
		
		panel_2.add(lblTime);

		reset.addActionListener(new ResetGame(this, panel_1, panel));

		// 初始化
		init(panel_1);
	}

	private void dailyMojic(JLabel lblTip) {
		tips = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				while(true) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				lblTip.setText("我绝对不知道，我怎么就玩起了这个游戏");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				lblTip.setText("闷声大发财，一个格都不踩，这是坠吼滴");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				lblTip.setText("这次排雷任务，得到了全军几百名将军的一致通过");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				lblTip.setText("你们啊，不要总想着搞个大爆炸，说我踩到地雷了");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				lblTip.setText("识得唔识得玩啊");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				}
			}
		});
		
		tips.start();
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
