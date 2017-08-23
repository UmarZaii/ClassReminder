package com.umarzaii.classreminder.Handler;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.YearMonth;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class JODADateHandler {

    private Date date;
    private DateTime dateTime;

    private final Integer JANUARY_DAYS = 31;
    private final Integer DECEMBER_MONTH = 12;

    public final Integer MONDAY = 1;
    public final Integer TUESDAY = 2;
    public final Integer WEDNESDAY = 3;
    public final Integer THURSDAY = 4;
    public final Integer FRIDAY = 5;
    public final Integer SATURDAY = 6;
    public final Integer SUNDAY = 7;

    public JODADateHandler(Activity activity) {
        JodaTimeAndroid.init(activity);

        date = new Date();
        dateTime = new DateTime(date);
    }

    public Integer getDayWeek() {
        return dateTime.getDayOfWeek();
    }

    public Integer getDayMonth() {
        return dateTime.getDayOfMonth();
    }

    public Integer getYear() {
        return dateTime.getYear();
    }

    public Integer getMonthOfYear() {
        return dateTime.getMonthOfYear();
    }

    public String getCurrentDate() {
        return dateTime.toString().substring(0,10);
    }

    @NonNull
    private Integer getTimeTableDay(Integer day1to7) {
        return (getDayMonth() - getDayWeek()) + day1to7;
    }

    public String getTimeTableDate(Integer dayOfWeek) {

        String targetDay = "";
        String targetMonth = "";
        String targetYear = "";

        if (checkMinDay(dayOfWeek) && checkMaxDay(dayOfWeek)) {
            targetYear = String.format("%04d", getYear());
            targetMonth = String.format("%02d", getMonthOfYear());
            targetDay = String.format("%02d", getTimeTableDay(dayOfWeek));

        } else if (!checkMinDay(dayOfWeek) && checkMinMonth() && checkMaxDay(dayOfWeek) && checkMaxMonth()) {
            targetYear = String.format("%04d", getYear());
            targetMonth = String.format("%02d", getMonthOfYear() - 1);
            targetDay = String.format("%02d", getTimeTableDay(dayOfWeek) + totalDayMonthBefore());

        } else if (!checkMinDay(dayOfWeek) && !checkMinMonth() && checkMaxDay(dayOfWeek) && checkMaxMonth()) {
            targetYear = String.format("%04d", getYear() - 1);
            targetMonth = String.format("%02d", DECEMBER_MONTH);
            targetDay = String.format("%02d", getTimeTableDay(dayOfWeek) + totalDayMonthBefore());

        } else if (checkMinDay(dayOfWeek) && checkMinMonth() && !checkMaxDay(dayOfWeek) && checkMaxMonth()){
            targetYear = String.format("%04d", getYear());
            targetMonth = String.format("%02d", getMonthOfYear() + 1);
            targetDay = String.format("%02d", getTimeTableDay(dayOfWeek) - totalDayMonth());

        } else if (checkMinDay(dayOfWeek) && checkMinMonth() && !checkMaxDay(dayOfWeek) && !checkMaxMonth()) {
            targetYear = String.format("%04d", getYear() + 1);
            targetMonth = String.format("%02d", getMonthOfYear() - 11);
            targetDay = String.format("%02d", getTimeTableDay(dayOfWeek) - totalDayMonth());
        }
        Log.d("FILTER",String.valueOf(checkMinDay(dayOfWeek)) + " " + String.valueOf(checkMinMonth())
                + " " + String.valueOf(checkMaxDay(dayOfWeek)) + " " + String.valueOf(checkMaxMonth()));
        return targetYear + "-" + targetMonth + "-" + targetDay;
    }

    public Integer totalDayMonth() {
        return dateTime.dayOfMonth().getMaximumValue();
    }

    public Integer totalDayMonthBefore() {
        DateTime tempDate;
        if (checkMinMonth()) {
            Integer monthBefore = getMonthOfYear() - 1;
            tempDate = new DateTime(getYear(),monthBefore,getDayMonth(),00,00,00);
            return tempDate.dayOfMonth().getMaximumValue();
        } else { //JANUARY = 1
            return JANUARY_DAYS;
        }
    }

    public boolean checkMinDay(Integer dayOfWeek) {
        if (getTimeTableDay(dayOfWeek) >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkMaxDay(Integer dayOfWeek) {
        if (getTimeTableDay(dayOfWeek) <= totalDayMonth()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkMinMonth() {
        if (getMonthOfYear() > 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkMaxMonth() {
        if (getMonthOfYear() < 12) {
            return true;
        } else {
            return false;
        }
    }

}
