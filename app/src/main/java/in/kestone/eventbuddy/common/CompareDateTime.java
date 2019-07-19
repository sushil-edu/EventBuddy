package in.kestone.eventbuddy.common;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CompareDateTime {

    public static boolean compareDate(String fromDate, String toDate) {
        SimpleDateFormat dateFormatC = new SimpleDateFormat( "yyyy-MM-dd" );
        String strCurrentDate = dateFormatC.format( Calendar.getInstance().getTime() );
        Date currentDate;
        try {
            currentDate = dateFormatC.parse( strCurrentDate );

            Log.e("Date diff ", ""+currentDate.compareTo( dateFormatC.parse( fromDate ) )+" to ".concat( ""+currentDate.compareTo( dateFormatC.parse( toDate ) )));
            if (currentDate.compareTo( dateFormatC.parse( fromDate ) ) == 0 || currentDate.compareTo( dateFormatC.parse( toDate ) ) <=0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean funCompareDateTime(String fromDate, String toDate, String fromTime, String toTime) {
        SimpleDateFormat dateFormatC = new SimpleDateFormat( "yyyy-MM-dd" );
        SimpleDateFormat timeFormat = new SimpleDateFormat( "kk:mm" );
        String strCurrentDate = dateFormatC.format( Calendar.getInstance().getTime() );
        String strCurrentTime = timeFormat.format( new Date() );
        Date currentDate, currentTime;
        try {
            currentDate = dateFormatC.parse( strCurrentDate );
            currentTime = timeFormat.parse( strCurrentTime );

            Log.e("Date diff ", ""+fromDate.compareTo(String.valueOf(currentDate))+" to ".concat( ""+toDate.compareTo(String.valueOf(currentDate) )));
            Log.e("Time diff",""+fromTime.compareTo(String.valueOf(currentTime))+" to "+toTime.compareTo(String.valueOf(currentTime)));
//            if (currentDate.compareTo( dateFormatC.parse( fromDate ) ) == 0 && currentDate.compareTo( dateFormatC.parse( toDate ) ) <=0) {
            if (fromDate.compareTo(String.valueOf(currentDate))<= 0 && toDate.compareTo(String.valueOf(currentDate))>0) {
//                if (currentTime.compareTo( timeFormat.parse( fromTime ) ) >= 0 && currentTime.compareTo( timeFormat.parse( toTime ) ) <= 1) {
                if (fromTime.compareTo(String.valueOf(currentTime))<=0 && toTime.compareTo(String.valueOf(currentTime))>0) {
                    return true;
                }
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compareTime(String fromTime, String toTime, String selectedSlot) {
        SimpleDateFormat timeFormat = new SimpleDateFormat( "kk:mm" );
        Date currentTime;
        try {
            currentTime = timeFormat.parse( selectedSlot );
            Log.e("Time diff",""+currentTime.compareTo( timeFormat.parse( fromTime )) +" to "+currentTime.compareTo( timeFormat.parse( toTime )  ));
            if (currentTime.compareTo( timeFormat.parse( fromTime ) ) >= 0 && currentTime.compareTo( timeFormat.parse( toTime ) ) <= 0) {
                return true;
            } else {
                return false;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compareTime(String fromTime, String toTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat( "kk:mm" );
        String strCurrentTime = timeFormat.format( new Date() );
        Date currentTime;
        try {
            currentTime = timeFormat.parse( strCurrentTime );
            Log.e("Time diff",""+currentTime.compareTo( timeFormat.parse( fromTime )) +" to "+currentTime.compareTo( timeFormat.parse( toTime )  ));
            if (currentTime.compareTo( timeFormat.parse( fromTime ) ) >= 0 && currentTime.compareTo( timeFormat.parse( toTime ) ) <= 0) {
                return true;
            } else {
                return false;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compareDateTime(String fromDate, String toDate, String fromTime, String toTime) {
        SimpleDateFormat dateTimeFormatC = new SimpleDateFormat( "yyyy-MM-dd kk:mm" );
        String strCurrentDate = dateTimeFormatC.format( Calendar.getInstance().getTime() );
        Date currentDateTime, actualDateTimeFrom, actualDateTimeTo;
        try {
            currentDateTime=dateTimeFormatC.parse(strCurrentDate);
            actualDateTimeFrom = dateTimeFormatC.parse(String.valueOf(
                    new StringBuffer().append(fromDate).append(" ").append(fromTime)));
            actualDateTimeTo = dateTimeFormatC.parse(String.valueOf(
                    new StringBuffer().append(toDate).append(" ").append(toTime)));
//            Log.e("DateTime ", currentDateTime+" Actual date from "+ actualDateTimeFrom +" date to "+actualDateTimeTo);
//            Log.e("Date diff ", ""+actualDateTimeFrom.before(currentDateTime)
//                    +" to ".concat( ""+actualDateTimeTo.after(currentDateTime )));
                return actualDateTimeFrom.before(currentDateTime) && actualDateTimeTo.after(currentDateTime );

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}

