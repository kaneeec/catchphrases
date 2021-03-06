package cz.pikadorama.framework.format;

import static android.text.format.DateUtils.FORMAT_ABBREV_ALL;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_TIME;
import static android.text.format.DateUtils.FORMAT_SHOW_WEEKDAY;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.text.format.DateUtils;

/**
 * Created by Tomas on 11.8.2015.
 */
public class DateTimeFormatter {

	private final Context context;
	private final int flags;

	private DateTimeFormatter(Context context, int flags) {
		this.context = context;
		this.flags = flags;
	}

	public static DateTimeFormatter showTime(Context context) {
		return new DateTimeFormatter(context, FORMAT_SHOW_TIME);
	}

	public static DateTimeFormatter showDate(Context context) {
		return new DateTimeFormatter(context, FORMAT_SHOW_DATE | FORMAT_ABBREV_ALL);
	}

	public static DateTimeFormatter showDateTime(Context context) {
		return new DateTimeFormatter(context, FORMAT_SHOW_DATE | FORMAT_ABBREV_ALL | FORMAT_SHOW_TIME);
	}

	public DateTimeFormatter showWeekDay() {
		return new DateTimeFormatter(context, flags | FORMAT_SHOW_WEEKDAY);
	}

	public DateTimeFormatter showYearAlways() {
		return new DateTimeFormatter(context, flags | FORMAT_SHOW_YEAR);
	}

	public String format(Date date) {
		return DateUtils.formatDateTime(context, date.getTime(), flags);
	}

	public String format(Calendar calendar) {
		return format(calendar.getTime());
	}
}
