package main;

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
			
			// 标出你要标记的雷，不会boom
			final ImageIcon ico = new ImageIcon(System.getProperty("user.dir") + "\\res\\flag.jpg");
			ico.setImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			GameUI.buttonSet[buttonRow][buttonCol].setIcon(ico);
			GameUI.markMap[buttonRow][buttonCol] = 1;
			
//			 System.out.println(e.getSource().toString());
//			 System.out.println(buttonRow);
//			 System.out.println(buttonCol);
			 System.out.println(GameUI.markMap[buttonRow][buttonCol]);
			

			// statusMap[buttonRow][buttonCol] = 0;
			// System.out.println(GameUI.statusMap[buttonRow][buttonCol]);
		}
	}
}