package com.minji.librarys.uitls;

import java.util.Calendar;

public class SystemTime {


    /**
     * 获取系统时间
     */
    public static String getTimer() {

        long currentTimeMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

//       Calendar c = Calendar.getInstance();
//
//        int year = c.get(Calendar.YEAR);
//
//        int month = c.get(Calendar.MONTH);
//
//        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//
//        int minute = c.get(Calendar.MINUTE);


        return year + "/" + month + "/" + date + "/" + hour + ":" + minute;
    }

    public static String getTimerWeek() {

        long currentTimeMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return "星期" + week;
    }

}
