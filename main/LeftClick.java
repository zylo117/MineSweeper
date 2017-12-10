package main;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import tool.Point;
import tool.RemoveDuplicateOffList;

final class LeftClick implements ActionListener {
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

		// statusMap[buttonRow][buttonCol] = 0;
		System.out.println(GameUI.statusMap[buttonRow][buttonCol]);
		GameUI.buttonSet[buttonRow][buttonCol].setEnabled(false);

		if (GameUI.clickCount == 0) {
			GameUI.firstPoint = new Point(buttonCol, buttonRow);
			setMine(GameUI.firstPoint);
		}
		GameUI.clickCount++;

		// 对点击点以及其周围点判断
		judgeOneBlock(buttonCol, buttonRow);
	}

	private void judgeOneBlock(int buttonCol, int buttonRow) {
		if (GameUI.statusMap[buttonRow][buttonCol].equals("m")) {

			for (int r = 0; r < GameUI.ttlRow; r++) {
				for (int c = 0; c < GameUI.ttlCol; c++) {
					// 把其余所有没有点击的地雷灰度图显示出来，以示公平
					if (GameUI.statusMap[r][c] == "m") {
						ImageIcon ico2 = new ImageIcon(
								System.getProperty("user.dir") + "\\res\\unrevealed_boom_laji.jpg");
						ico2.setImage(ico2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
						GameUI.buttonSet[r][c].setIcon(ico2);
					}
					// 锁死所有按钮，游戏结束
					if(!GameUI.debug)
						GameUI.buttonSet[r][c].setEnabled(false);
				}
			}
			// 标出你踩的雷，boom
			ImageIcon ico = new ImageIcon(System.getProperty("user.dir") + "\\res\\boom_laji.jpg");
			ico.setImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			GameUI.buttonSet[buttonRow][buttonCol].setIcon(ico);
			System.out.println("YOU LOSE SUCKER!");
		}

		if (GameUI.statusMap[buttonRow][buttonCol].equals("e")) {
			ImageIcon ico = new ImageIcon(System.getProperty("user.dir") + "\\res\\empty.jpg");
			ico.setImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			GameUI.buttonSet[buttonRow][buttonCol].setIcon(ico);

			// 把当前点改成已点击的状态0
			GameUI.statusMap[buttonRow][buttonCol] = 0;

			// 判断九宫格内是否有炸
			int surroundingMines = judgeSurroundingIs(buttonCol, buttonRow, "m");

			System.out.println(surroundingMines);

			ImageIcon num = new ImageIcon(
					System.getProperty("user.dir") + "\\res\\number\\" + surroundingMines + ".jpg");
			num.setImage(num.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			GameUI.buttonSet[buttonRow][buttonCol].setIcon(num);
			
			// 自动展开到最近有数字的区域
//			if(GameUI.statusMap[buttonRow - 1][buttonCol - 1].equals("e")) {
//				
//			}
		}
	}

	private int judgeSurroundingIs(int buttonCol, int buttonRow, String target) {
		int surroundingMines = 0;

		// 判断九宫格内是否有炸（雷）(中心)
		if (buttonRow != 0 && buttonRow != GameUI.ttlRow - 1 && buttonCol != 0 && buttonCol != GameUI.ttlCol - 1) {
			if (GameUI.statusMap[buttonRow - 1][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
		}

		// 判断九宫格内是否有炸（雷）(四角)
		// 左上角
		if (buttonRow == 0 && buttonCol == 0) {
			if (GameUI.statusMap[buttonRow][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
		}
		// 右上角
		if (buttonRow == 0 && buttonCol == GameUI.ttlCol - 1) {
			if (GameUI.statusMap[buttonRow][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
		}
		// 左下角
		if (buttonRow == GameUI.ttlRow - 1 && buttonCol == 0) {
			if (GameUI.statusMap[buttonRow][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
		}
		// 右下角
		if (buttonRow == GameUI.ttlRow - 1 && buttonCol == GameUI.ttlCol - 1) {
			if (GameUI.statusMap[buttonRow][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
		}

		// 判断九宫格内是否有炸（雷）(边缘)
		// 上边
		if (buttonRow == 0 && buttonCol != 0 && buttonCol != GameUI.ttlCol - 1) {
			if (GameUI.statusMap[buttonRow][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
		}
		// 下边
		if (buttonRow == GameUI.ttlRow - 1 && buttonCol != 0 && buttonCol != GameUI.ttlCol - 1) {
			if (GameUI.statusMap[buttonRow][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
		}
		// 左边
		if (buttonCol == 0 && buttonRow != 0 && buttonRow != GameUI.ttlRow - 1) {
			if (GameUI.statusMap[buttonRow - 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol + 1].equals(target)) {
				surroundingMines++;
			}
		}
		// 右边
		if (buttonCol == GameUI.ttlCol - 1 && buttonRow != 0 && buttonRow != GameUI.ttlRow - 1) {
			if (GameUI.statusMap[buttonRow - 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol - 1].equals(target)) {
				surroundingMines++;
			}
		}
		return surroundingMines;
	}

	private void setMine(Point firstpoint) {
		List<Point> mineLocationSet = new ArrayList<>();
		while (true) {
			mineLocationSet = new ArrayList<>();
			for (int i = 0; i < GameUI.mineQTY; i++) {
				Point mineLocation = new Point((int) (Math.random() * GameUI.ttlRow),
						(int) (Math.random() * GameUI.ttlCol));

				if (mineLocation.equals(firstpoint))
					break;

				mineLocationSet.add(mineLocation);
				GameUI.statusMap[(int) mineLocation.y][(int) mineLocation.x] = "m";
			}
			RemoveDuplicateOffList.remove(mineLocationSet);
			if (mineLocationSet.size() == GameUI.mineQTY)
				break;
		}
		if (GameUI.debug)
			System.out.println(mineLocationSet);
	}
}