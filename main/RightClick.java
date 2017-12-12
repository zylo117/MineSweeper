package main;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

final class RightClick extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == e.BUTTON3) {
			// int x = e.getX();
			// int y = e.getY();
			final String[] buttonData = e.getSource().toString().substring(20).split(",", 5);
			final String[] buttonSize = buttonData[3].split("x");
			int buttonCol = Integer.parseInt(buttonData[1]);
			int buttonRow = Integer.parseInt(buttonData[2]);
			buttonCol = buttonCol / Integer.parseInt(buttonSize[0]);
			buttonRow = buttonRow / Integer.parseInt(buttonSize[1]);

			// 标出你要标记的雷，不会boom，次数有限
			if (GameUI.markChance > 0 && (GameUI.statusMap[buttonRow][buttonCol] == -1 || GameUI.statusMap[buttonRow][buttonCol] == 9)) {
				final ImageIcon ico = new ImageIcon(GameUI.class.getResource("/res/flag.jpg"));
				ico.setImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
				GameUI.buttonSet[buttonRow][buttonCol].setIcon(ico);
				GameUI.markMap[buttonRow][buttonCol] = 1;
				GameUI.markChance--;
				GameUI.lblMark.setText("    Flag: " + Integer.toString(GameUI.markChance));

				// System.out.println(e.getSource().toString());
				// System.out.println(buttonRow);
				// System.out.println(buttonCol);
				System.out.println("MakePoint:" + GameUI.markMap[buttonRow][buttonCol]);
			}

			if(GameUI.statusMap[buttonRow][buttonCol] == -1 || GameUI.statusMap[buttonRow][buttonCol] == 9) {
				judgeWin();
			}

			// statusMap[buttonRow][buttonCol] = 0;
			// System.out.println(GameUI.statusMap[buttonRow][buttonCol]);
		}
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
		System.out.println("safeMine" + safeMine);
		System.out.println("remainingBlank" + remainingBlank);

		if (safeMine == GameUI.mineQTY) {
			for (int r = 0; r < GameUI.ttlRow; r++) {
				for (int c = 0; c < GameUI.ttlCol; c++) {
					// 锁死所有按钮，游戏结束
					if (!GameUI.debug) {
						GameUI.buttonSet[r][c].setEnabled(false);
					}
				}
			}
			
			GameUI.ifFinish = true;
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
}