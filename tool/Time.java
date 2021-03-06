package tool;

import java.util.Calendar;
import java.util.TimeZone;

public class Time {
	// It's about "+1s"
	public static int year;
	public static int month;
	public static int day;
	public static String strMonth;
	public static String strDay;
	public static int hour;
	public static int minute;
	public static int second;
	public static String datetime_slash;
	public static String date_slash;
	public static String date_hyphen;
	public static String datetime_hyphen;

	public static void getTime() {
		final Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		year = c1.get(Calendar.YEAR);
		month = c1.get(Calendar.MONTH) + 1;
		day = c1.get(Calendar.DATE);
		hour = c1.get(Calendar.HOUR_OF_DAY);
		minute = c1.get(Calendar.MINUTE);
		second = c1.get(Calendar.SECOND);
		datetime_slash = year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second;
		date_slash = year + "/" + month + "/" + day;
		date_hyphen = year + "-" + month + "-" + day;
		datetime_hyphen = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

		if (month < 10)
			strMonth = "0" + month;
		else
			strMonth = Integer.toString(month);

		if (day < 10)
			strDay = "0" + day;
		else
			strDay = Integer.toString(day);
	}

	public static void waitFor(int msec) {
		// ��ʱ����
		try {
			Thread.currentThread();
			Thread.sleep(msec);// ���� ms
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getTime();
		System.out.println(date_hyphen);
	}
}
