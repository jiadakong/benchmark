package github.seanlinwang.study.date;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateCompute {

	@Test
	public void testDateComputer() {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date todayZero = c.getTime();
		c.add(Calendar.DATE, -1);
		Date yesterdayZero = c.getTime();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date lastMonthStartDate = c.getTime();
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
		Date lastMonthEndDate = c.getTime();
		System.out.println(yesterdayZero);
		System.out.println(todayZero);
		System.out.println(lastMonthStartDate);
		System.out.println(lastMonthEndDate);
	}
}
