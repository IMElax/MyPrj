package com.linchong.jira.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;
import java.util.Date;

public class WorkDayCalculateUtils {
    public static void main(String[] args) {
        Date date = getDate(new Date(), -7);
        Calendar calendar = Calendar.getInstance();
        System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd"));
    }

    public static Date getDate(Date currentDate, int days) {
        if (days == 0) {
            return currentDate;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int step = days < 0 ? -1 : 1;
        int i = 0;
        int daysAbs = Math.abs(days);
        while (i < daysAbs) {
            calendar.add(Calendar.DATE, step);
            i++;
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

                i--;
            }
        }

        return calendar.getTime();
    }
}
