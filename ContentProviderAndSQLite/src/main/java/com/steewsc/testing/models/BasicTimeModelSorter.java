package com.steewsc.testing.models;

import com.steewsc.testing.db.Config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by
 * Stevica Trajanovic
 * stevica@shiftplanning.com
 * on 12/25/13.
 */
public class BasicTimeModelSorter implements Comparator<Object>, Comparable<Object> {

    private String start;

    public BasicTimeModelSorter(){

    }

    @Override
    public int compareTo(Object object) {
        return ( start ).compareTo( ((BasicTimeModel)object).getStart() );
    }

    @Override
    public int compare(Object object1, Object object2) {
        String start1 = ((BasicTimeModel)object1).getStart();
        String start2 = ((BasicTimeModel)object2).getStart();
        int result = getDateTime(start1) - getDateTime(start2);
        return result;
    }

    public static int getDateTime(String _date) {
        int result = 0;
        java.util.Date date = null;
        Calendar cal = Calendar.getInstance();
        try {
            if( hasTime(_date) ){
                date = new SimpleDateFormat(Config.dateFormatAPI + " " + Config.timeFormat).parse(_date);
            }else{
                date = new SimpleDateFormat(Config.dateFormatAPI + " " + Config.timeFormat).parse(_date + " 00:00:00");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            cal.setTime(date);
        }catch(Exception e){
            System.out.print("getDateTime => " + _date);
            e.printStackTrace();
        }
        result = (new Long(cal.getTimeInMillis() / 1000)).intValue();

        return result;
    }

    public static boolean hasTime(String _date) {
        boolean result = false;

        if (_date == null) {
            return false;
        }
        if (_date.contains(":")) {
            if (_date.split(":").length > 1) {
                result = true;
            }
        }
        return result;
    }
}
