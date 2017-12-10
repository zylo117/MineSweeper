package main;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.SwingConstants;

import javafx.beans.property.StringProperty;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import tool.Point;
import tool.RemoveDuplicateOffList;

public class GameUI {

	public static int ttlRow = 10;
	public static int ttlCol = 10;
	public static int mineQTY = 10; // 建议不超过总数的10分之1
	public static boolean debug = true;
	public static JButton[][] buttonSet = new JButton[ttlRow][ttlCol];
	// public static int[][] indexMap = new int[ttlRow][ttlCol];
	public static int clickCount = 0;
	public static Point firstPoint;
	public static JButton currentButton;
	public static Object currentStatus = "e";
	public static Object[][] statusMap = new Object[999][999];
	// 状态图示意
	// 1~8，已点击，附近有1~8个雷
	// 0，已点击，空，安全
	// x，点击，雷。炸了
	// m，未点击，雷
	// e，未点击，空，安全

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameUI window = new GameUI();
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

		for (int i = 0; i < statusMap.length; i++) {
			for (int j = 0; j < statusMap[0].length; j++) {
				statusMap[i][j] = currentStatus;
			}
		}

		// 设置行列数
		frame.getContentPane().setLayout(new GridLayout(ttlRow, ttlCol, 0, 0));

		for (int r = 0; r < ttlRow; r++) {
			for (int c = 0; c < ttlCol; c++) {
				// currentButton = buttonSet[r][c];

				buttonSet[r][c] = new JButton();

				statusMap[r][c] = "e";

				frame.getContentPane().add(buttonSet[r][c]);

				buttonSet[r][c].setUI(new MyButtonUI());
				ImageIcon ico = new ImageIcon(System.getProperty("user.dir") + "\\res\\dirt_huaji.jpg");
				ico.setImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
				buttonSet[r][c].setIcon(ico);

				buttonSet[r][c].addActionListener(new LeftClick());

			}
		}

	}
}
