package main;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import tool.Point;

final class LeftClick implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		final String[] buttonData = e.getSource().toString().substring(20).split(",", 5);
		final String[] buttonSize = buttonData[3].split("x");
		int buttonCol = Integer.parseInt(buttonData[1]);
		int buttonRow = Integer.parseInt(buttonData[2]);
		buttonCol = buttonCol / Integer.parseInt(buttonSize[0]);
		buttonRow = buttonRow / Integer.parseInt(buttonSize[1]);
		// System.out.println(e.getSource().toString());
		// System.out.println(buttonRow);
		// System.out.println(buttonCol);
		// System.out.println(statusMap[buttonRow][buttonCol]);

		// statusMap[buttonRow][buttonCol] = 0;
		System.out.println("CurrentPoint:" + GameUI.statusMap[buttonRow][buttonCol]);

		// 放置第一个雷，检测是不是右键标记的状态
		if (GameUI.clickCount == 0 && GameUI.markMap[buttonRow][buttonCol] != 1) {
			GameUI.firstPoint = new Point(buttonCol, buttonRow);
			setMine(GameUI.firstPoint);
			
			// 如果一开始就按到空格，自动展开
			if(GameUI.statusMap[buttonRow][buttonCol] == 0) {
				autoRevealEmptyBlock(buttonCol, buttonRow);
			}
		}
		if (GameUI.markMap[buttonRow][buttonCol] != 1) {
			GameUI.clickCount++;

			// 对点击点以及其周围点判断
			judgeThisBlock(buttonCol, buttonRow);

		} else {
			// 如果已被右键标记
			GameUI.markMap[buttonRow][buttonCol] = 0;
			final ImageIcon ico = new ImageIcon(GameUI.class.getResource("/res/dirt_huaji.jpg"));
			ico.setImage(ico.getImage().getScaledInstance(GameUI.blockSize,GameUI. blockSize, Image.SCALE_SMOOTH));
			GameUI.buttonSet[buttonRow][buttonCol].setIcon(ico);
			GameUI.markChance++;
		}
	}

	private void judgeThisBlock(int buttonCol, int buttonRow) {

		// 如果炸了
		if (GameUI.statusMap[buttonRow][buttonCol] == 9) {
			for (int r = 0; r < GameUI.ttlRow; r++) {
				for (int c = 0; c < GameUI.ttlCol; c++) {
					// 把其余所有没有点击的地雷灰度图显示出来，以示公平
					if (GameUI.statusMap[r][c] == 9) {
						final ImageIcon ico2 = new ImageIcon(GameUI.class.getResource("/res/unrevealed_+1s.jpg"));
						ico2.setImage(ico2.getImage().getScaledInstance(GameUI.blockSize,GameUI. blockSize, Image.SCALE_SMOOTH));
						GameUI.buttonSet[r][c].setIcon(ico2);
					}
					// 锁死所有按钮，游戏结束
					if (!GameUI.debug) {
						GameUI.buttonSet[r][c].setEnabled(false);
					}
				}
			}
			// 标出你踩的雷，boom
			final ImageIcon ico = new ImageIcon(GameUI.class.getResource("/res/+1s.jpg"));
			ico.setImage(ico.getImage().getScaledInstance(GameUI.blockSize,GameUI. blockSize, Image.SCALE_SMOOTH));
			GameUI.buttonSet[buttonRow][buttonCol].setIcon(ico);
			// System.out.println("YOU LOSE SUCKER!");
			System.out.println("很惭愧，只踩了一个微小的地雷");
			
			PopUpResult.result.setText("很惭愧，只踩了一个微小的地雷");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						PopUpResult window = new PopUpResult();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		// 如果没有炸
		if (GameUI.statusMap[buttonRow][buttonCol] == -1) {
			final ImageIcon ico = new ImageIcon(GameUI.class.getResource("/res/empty.jpg"));
			ico.setImage(ico.getImage().getScaledInstance(GameUI.blockSize,GameUI. blockSize, Image.SCALE_SMOOTH));
			GameUI.buttonSet[buttonRow][buttonCol].setIcon(ico);
			GameUI.buttonSet[buttonRow][buttonCol].setEnabled(false);
			GameUI.statusMap[buttonRow][buttonCol] = 0;

			// 判断九宫格内是否有炸
			final int surroundingMines = judgeSurroundingIs(buttonCol, buttonRow, 9);

			System.out.println("surroundingMines" + surroundingMines);
			GameUI.statusMap[buttonRow][buttonCol] = surroundingMines;

			if (surroundingMines > 0) {
				final ImageIcon num = new ImageIcon(
						GameUI.class.getResource("/res/number/" + surroundingMines + ".jpg"));
				num.setImage(num.getImage().getScaledInstance(GameUI.blockSize,GameUI. blockSize, Image.SCALE_SMOOTH));
				GameUI.buttonSet[buttonRow][buttonCol].setIcon(num);
				GameUI.buttonSet[buttonRow][buttonCol].setEnabled(false);
			}

			// 自动展开到最近有数字的区域
			autoRevealEmptyBlock(buttonCol, buttonRow);
		}

		judgeWin();
	}

	private void judgeWin() {
		// 判断胜利
		int safeMine = 0;
		int remainingBlank = 0;
		for (int r = 0; r < GameUI.ttlRow; r++) {
			for (int c = 0; c < GameUI.ttlCol; c++) {
				if (GameUI.statusMap[r][c] == 9 && GameUI.markMap[r][c] == 1) {
					safeMine++;
				}
				if (GameUI.statusMap[r][c] == -1) {
					remainingBlank++;
				}
			}
		}
//		System.out.println("safeMine" + safeMine);
//		System.out.println("remainingBlank" + remainingBlank);

		if (safeMine == GameUI.mineQTY) {
			for (int r = 0; r < GameUI.ttlRow; r++) {
				for (int c = 0; c < GameUI.ttlCol; c++) {
					// 锁死所有按钮，游戏结束
					if (!GameUI.debug) {
						GameUI.buttonSet[r][c].setEnabled(false);
					}
				}
			}
			System.out.println("你给我搞的这个游戏啊，一颗赛艇！");
			
			PopUpResult.result.setText("你给我搞的这个游戏啊，一颗赛艇！");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						PopUpResult window = new PopUpResult();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private void autoRevealEmptyBlock(int buttonCol, int buttonRow) {
		if (judgeSurroundingIs(buttonCol, buttonRow, 9) == 0) {
			reveal3x3Block(buttonCol, buttonRow);
		}
	}

	private void reveal3x3Block(int buttonCol, int buttonRow) {
		int c = buttonCol;
		int r = buttonRow;
		// 判断中心
		if (buttonRow != 0 && buttonRow != GameUI.ttlRow - 1 && buttonCol != 0 && buttonCol != GameUI.ttlCol - 1) {
			GameUI.buttonSet[buttonRow - 1][buttonCol - 1].doClick(0);
			GameUI.buttonSet[buttonRow - 1][buttonCol].doClick(0);
			GameUI.buttonSet[buttonRow - 1][buttonCol + 1].doClick(0);
			GameUI.buttonSet[buttonRow][buttonCol - 1].doClick(0);
			GameUI.buttonSet[buttonRow][buttonCol + 1].doClick(0);
			GameUI.buttonSet[buttonRow + 1][buttonCol - 1].doClick(0);
			GameUI.buttonSet[buttonRow + 1][buttonCol].doClick(0);
			GameUI.buttonSet[buttonRow + 1][buttonCol + 1].doClick(0);
		}
		// 判断四角
		// 左上角
		if (buttonRow == 0 && buttonCol == 0) {
			GameUI.buttonSet[buttonRow][buttonCol + 1].doClick(0);
			GameUI.buttonSet[buttonRow + 1][buttonCol].doClick(0);
			GameUI.buttonSet[buttonRow + 1][buttonCol + 1].doClick(0);
		}
		// 右上角
		if (buttonRow == 0 && buttonCol == GameUI.ttlCol - 1) {
			GameUI.buttonSet[buttonRow][buttonCol - 1].doClick(0);
			GameUI.buttonSet[buttonRow + 1][buttonCol - 1].doClick(0);
			GameUI.buttonSet[buttonRow + 1][buttonCol].doClick(0);
		}
		// 左下角
		if (buttonRow == GameUI.ttlRow - 1 && buttonCol == 0) {
			GameUI.buttonSet[buttonRow - 1][buttonCol + 1].doClick(0);
			GameUI.buttonSet[buttonRow - 1][buttonCol].doClick(0);
			GameUI.buttonSet[buttonRow][buttonCol + 1].doClick(0);
		}
		// 右下角
		if (buttonRow == GameUI.ttlRow - 1 && buttonCol == GameUI.ttlCol - 1) {
			GameUI.buttonSet[buttonRow - 1][buttonCol - 1].doClick(0);
			GameUI.buttonSet[buttonRow - 1][buttonCol].doClick(0);
			GameUI.buttonSet[buttonRow][buttonCol - 1].doClick(0);
		}
	}

	private int judgeSurroundingIs(int buttonCol, int buttonRow, int target) {
		int surroundingMines = 0;

		// 判断九宫格内是否有炸（雷）(中心)
		if (buttonRow != 0 && buttonRow != GameUI.ttlRow - 1 && buttonCol != 0 && buttonCol != GameUI.ttlCol - 1) {
			if (GameUI.statusMap[buttonRow - 1][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol + 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol + 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol + 1] == target) {
				surroundingMines++;
			}
		}

		// 判断九宫格内是否有炸（雷）(四角)
		// 左上角
		if (buttonRow == 0 && buttonCol == 0) {
			if (GameUI.statusMap[buttonRow][buttonCol + 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol + 1] == target) {
				surroundingMines++;
			}
		}
		// 右上角
		if (buttonRow == 0 && buttonCol == GameUI.ttlCol - 1) {
			if (GameUI.statusMap[buttonRow][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol - 1] == target) {
				surroundingMines++;
			}
		}
		// 左下角
		if (buttonRow == GameUI.ttlRow - 1 && buttonCol == 0) {
			if (GameUI.statusMap[buttonRow][buttonCol + 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol + 1] == target) {
				surroundingMines++;
			}
		}
		// 右下角
		if (buttonRow == GameUI.ttlRow - 1 && buttonCol == GameUI.ttlCol - 1) {
			if (GameUI.statusMap[buttonRow][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol - 1] == target) {
				surroundingMines++;
			}
		}

		// 判断九宫格内是否有炸（雷）(边缘)
		// 上边
		if (buttonRow == 0 && buttonCol != 0 && buttonCol != GameUI.ttlCol - 1) {
			if (GameUI.statusMap[buttonRow][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol + 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol + 1] == target) {
				surroundingMines++;
			}
		}
		// 下边
		if (buttonRow == GameUI.ttlRow - 1 && buttonCol != 0 && buttonCol != GameUI.ttlCol - 1) {
			if (GameUI.statusMap[buttonRow][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol + 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol + 1] == target) {
				surroundingMines++;
			}
		}
		// 左边
		if (buttonCol == 0 && buttonRow != 0 && buttonRow != GameUI.ttlRow - 1) {
			if (GameUI.statusMap[buttonRow - 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol + 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol + 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol + 1] == target) {
				surroundingMines++;
			}
		}
		// 右边
		if (buttonCol == GameUI.ttlCol - 1 && buttonRow != 0 && buttonRow != GameUI.ttlRow - 1) {
			if (GameUI.statusMap[buttonRow - 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow - 1][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow][buttonCol - 1] == target) {
				surroundingMines++;
			}
			if (GameUI.statusMap[buttonRow + 1][buttonCol - 1] == target) {
				surroundingMines++;
			}
		}
		return surroundingMines;
	}

	private void setMine(Point firstpoint) {
		final HashSet<Point> mineLocationSet = new HashSet<>();
		while (true) {
			final Point mineLocation = new Point((int) (Math.random() * GameUI.ttlCol),
					(int) (Math.random() * GameUI.ttlRow));

			if (mineLocation.equals(firstpoint)) {
				continue;
			}

			mineLocationSet.add(mineLocation);

			if (mineLocationSet.size() == GameUI.mineQTY) {
				break;
			}
		}

		for (final Point mineLocation : mineLocationSet) {
			GameUI.statusMap[(int) mineLocation.y][(int) mineLocation.x] = 9;
		}

		if (GameUI.debug) {
			System.out.println("Mine:" + mineLocationSet);
		}
	}
}