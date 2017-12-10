package main;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

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

		if (GameUI.statusMap[buttonRow][buttonCol].equals("m")) {
			// 标出你踩的雷，boom
			ImageIcon ico = new ImageIcon(System.getProperty("user.dir") + "\\res\\boom.jpg");
			ico.setImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			GameUI.buttonSet[buttonRow][buttonCol].setIcon(ico);
			for (int r = 0; r < GameUI.ttlRow; r++) {
				for (int c = 0; c < GameUI.ttlCol; c++) {
					// 锁死所有按钮，游戏结束
					GameUI.buttonSet[r][c].setEnabled(false);

					// 把其余所有没有点击的地雷灰度图显示出来，以示公平
					if (GameUI.statusMap[r][c] == "m" && r != buttonRow && c != buttonCol) {
						ImageIcon ico2 = new ImageIcon(System.getProperty("user.dir") + "\\res\\unrevealed_boom.jpg");
						ico2.setImage(ico2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
						GameUI.buttonSet[r][c].setIcon(ico2);
					}
				}
			}
			System.out.println("YOU LOSE SUCKER!");
		}

		if (GameUI.statusMap[buttonRow][buttonCol].equals("e")) {
			ImageIcon ico = new ImageIcon(System.getProperty("user.dir") + "\\res\\empty.jpg");
			ico.setImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			GameUI.buttonSet[buttonRow][buttonCol].setIcon(ico);
		}
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