package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import tool.Point;

final class ResetGame implements ActionListener {
	/**
	 * 
	 */
	private final GameUI gameUI;
	private final JPanel panel_1;
	private final JPanel panel;

	ResetGame(GameUI gameUI, JPanel panel_1, JPanel panel) {
		this.gameUI = gameUI;
		this.panel_1 = panel_1;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO 自动生成的方法存根
				panel_1.removeAll();
				GameUI.ifReset = true;
				GameUI.ifFinish = false;
				GameUI.statusMap = new int[999][999];
				GameUI.markMap = new int[999][999];
				GameUI.clickCount = 0;
				GameUI.firstPoint = new Point();
				GameUI.mineQTY = GameUI.markChance = Integer.parseInt(ResetGame.this.gameUI.mineBox.getText());
				GameUI.lblMark.setText("    Flag: " + Integer.toString(GameUI.markChance));
				GameUI.ttlCol = Integer.parseInt(ResetGame.this.gameUI.colBox.getText());
				GameUI.ttlRow = Integer.parseInt(ResetGame.this.gameUI.rowBox.getText());
				GameUI.buttonSet = new JButton[GameUI.ttlRow][GameUI.ttlCol];
				ResetGame.this.gameUI.frame.setBounds(100, 100, GameUI.blockSize * GameUI.ttlCol + 10, GameUI.blockSize * GameUI.ttlRow + 130);
				panel.setBounds(0, 0, GameUI.blockSize * GameUI.ttlCol + 10, GameUI.blockSize * GameUI.ttlRow + 130);
				ResetGame.this.gameUI.init(panel_1);
			}
		}).start();

	}
}