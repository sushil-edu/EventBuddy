package in.kestone.eventbuddy.common;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.kestone.eventbuddy.model.DateParser;

public class GenerateDates {
    static List<DateParser> dateParsers = new ArrayList<>();

    public static List<Date> getDates(String dateString1, String dateString2) {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df2 = new SimpleDateFormat("dd MMM yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static List<DateParser> getNetworkingDate(String startDate, String endDate) {
        List<Date> dates = getDates(startDate, endDate);
        dateParsers.clear();
        for (Date date : dates) {
            String fullDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
//            Log.e("Full date ", fullDate);
            String[] dt = fullDate.split(",");// for day , month date , year
            String[] dtM = dt[1].split(" ");
            DateParser dp = new DateParser();
            dp.setDay(dt[0].substring(0, 3));
            dp.setYear(dt[2]);
            dp.setMonth(dtM[2]);
            dp.setDate(dtM[1]);
            dp.setFullDate(dt[1] + " " + dt[2]);
            dateParsers.add(dp);

        }
        return dateParsers;
    }
}
