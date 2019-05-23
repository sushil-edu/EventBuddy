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
            if (currentDate.compareTo( dateFormatC.parse( fromDate ) ) == 0 && currentDate.compareTo( dateFormatC.parse( toDate ) ) <=0) {
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

            Log.e("Date diff ", ""+currentDate.compareTo( dateFormatC.parse( fromDate ) )+" to ".concat( ""+currentDate.compareTo( dateFormatC.parse( toDate ) )));
            Log.e("Time diff",""+currentTime.compareTo( timeFormat.parse( fromTime )) +" to "+currentTime.compareTo( timeFormat.parse( toTime )  ));
            if (currentDate.compareTo( dateFormatC.parse( fromDate ) ) == 0 && currentDate.compareTo( dateFormatC.parse( toDate ) ) <=0) {
                if (currentTime.compareTo( timeFormat.parse( fromTime ) ) >= 0 && currentTime.compareTo( timeFormat.parse( toTime ) ) <= 1) {
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
        String strCurrentTime = timeFormat.format( new Date() );
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
}

