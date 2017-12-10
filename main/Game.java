package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javafx.beans.property.StringProperty;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;

import tool.Point;
import tool.RemoveDuplicateOffList;

public class Game {

	public static int ttlRow = 10;
	public static int ttlCol = 10;
	public static int mineQTY = 5;
	public static boolean debug = false;
	public static JButton[][] buttonSet = new JButton[ttlRow][ttlCol];
	// public static int[][] indexMap = new int[ttlRow][ttlCol];
	public static boolean ifFirstClick = true;
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
					Game window = new Game();
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
	public Game() {
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

				if (debug)
					buttonSet[r][c] = new JButton(r + "," + c);
				else
					buttonSet[r][c] = new JButton();

				statusMap[r][c] = "e";

				frame.getContentPane().add(buttonSet[r][c]);

				buttonSet[r][c].addActionListener(new LeftClick());

			}
		}


		
	}

	private final class SetMine implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			List<Point> mineLocationSet = new ArrayList<>();
			while (true) {
				mineLocationSet = new ArrayList<>();
				for (int i = 0; i < mineQTY; i++) {
					Point mineLocation = new Point((int) (Math.random() * ttlRow), (int) (Math.random() * ttlCol));
					mineLocationSet.add(mineLocation);
					statusMap[(int) mineLocation.y][(int) mineLocation.x] = "m";
				}
				RemoveDuplicateOffList.remove(mineLocationSet);
				if (mineLocationSet.size() == mineQTY)
					break;
			}
		}
	}

	private final class LeftClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			String[] buttonData = e.getSource().toString().substring(20).split(",", 5);
			String[] buttonSize = buttonData[3].split("x");
			int buttonCol = Integer.parseInt(buttonData[1]);
			int buttonRow = Integer.parseInt(buttonData[2]);
			buttonCol = buttonCol / Integer.parseInt(buttonSize[0]);
			buttonRow = buttonRow / Integer.parseInt(buttonSize[1]);

			// System.out.println(e.getSource().toString());
			// System.out.println(buttonRow);
			// System.out.println(buttonCol);
			// System.out.println(statusMap[buttonRow][buttonCol]);
			
			if(ifFirstClick) {
				setMine();
			}
			
			statusMap[buttonRow][buttonCol] = 0;
			System.out.println(statusMap[buttonRow][buttonCol]);
			buttonSet[buttonRow][buttonCol].setEnabled(false);
		}

		private void setMine() {
			List<Point> mineLocationSet = new ArrayList<>();
			while (true) {
				mineLocationSet = new ArrayList<>();
				for (int i = 0; i < mineQTY; i++) {
					Point mineLocation = new Point((int) (Math.random() * ttlRow), (int) (Math.random() * ttlCol));
					mineLocationSet.add(mineLocation);
					statusMap[(int) mineLocation.y][(int) mineLocation.x] = "m";
				}
				RemoveDuplicateOffList.remove(mineLocationSet);
				if (mineLocationSet.size() == mineQTY)
					break;
			}
		}
	}
}
