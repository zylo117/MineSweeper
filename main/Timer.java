package main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tool.Point;
import tool.Time;

public class Timer {
	@SuppressWarnings("deprecation")
	public static void countSecond() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Thread thread = new Thread();
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				while (true) {
					if (GameUI.ifReset) {
						GameUI.ttlSecond = 0;
					}else if(GameUI.ifFinish) {
						
					}
					else {
						Date now;
						long diff = 0;
						Time.getTime();
						try {
							now = df.parse(tool.Time.datetime_hyphen);
							diff = now.getTime() - GameUI.firstTime.getTime();
						} catch (ParseException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						GameUI.ttlSecond = (int) (diff / 1000);
					}
					GameUI.lblTime.setText("    +" + Integer.toString(GameUI.ttlSecond) + "s");
				}
			}
		});
		thread.start();

	}
}
