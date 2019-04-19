package in.kestone.eventbuddy.common;

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
            if (currentDate.compareTo( dateFormatC.parse( fromDate ) ) >= 0 && currentDate.compareTo( dateFormatC.parse( toDate ) ) <= 0) {
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
            if (currentTime.compareTo( timeFormat.parse( fromTime ) ) >= 0 && currentTime.compareTo( timeFormat.parse( toTime ) ) <= 1) {
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

