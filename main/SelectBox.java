package main;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

final class SelectBox implements ItemListener {
	/**
	 * 
	 */
	private final GameUI gameUI;
	private final JComboBox comboBox;

	SelectBox(GameUI gameUI, JComboBox comboBox) {
		this.gameUI = gameUI;
		this.comboBox = comboBox;
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO 自动生成的方法存根
		if (comboBox.getSelectedIndex() == 0) {
			GameUI.mineQTY = 5;
			GameUI.clickCount = 0;
			GameUI.ttlCol = 10;
			GameUI.ttlRow = 10;
			GameUI.blockSize = 30;

			this.gameUI.mineBox.setText(Integer.toString(GameUI.mineQTY));
			this.gameUI.colBox.setText(Integer.toString(GameUI.ttlCol));
			this.gameUI.rowBox.setText(Integer.toString(GameUI.ttlRow));
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			GameUI.reset.doClick();
		} else if (comboBox.getSelectedIndex() == 1) {
			GameUI.mineQTY = 10;
			GameUI.clickCount = 0;
			GameUI.ttlCol = 10;
			GameUI.ttlRow = 10;
			GameUI.blockSize = 30;

			this.gameUI.mineBox.setText(Integer.toString(GameUI.mineQTY));
			this.gameUI.colBox.setText(Integer.toString(GameUI.ttlCol));
			this.gameUI.rowBox.setText(Integer.toString(GameUI.ttlRow));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			GameUI.reset.doClick();
		} else if (comboBox.getSelectedIndex() == 2) {
			GameUI.mineQTY = 50;
			GameUI.clickCount = 0;
			GameUI.ttlCol = 20;
			GameUI.ttlRow = 10;
			
			GameUI.blockSize = 25;
			this.gameUI.mineBox.setText(Integer.toString(GameUI.mineQTY));
			this.gameUI.colBox.setText(Integer.toString(GameUI.ttlCol));
			this.gameUI.rowBox.setText(Integer.toString(GameUI.ttlRow));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			GameUI.reset.doClick();
		}
	}
}