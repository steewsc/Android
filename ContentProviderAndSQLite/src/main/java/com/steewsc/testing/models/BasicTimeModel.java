package com.steewsc.testing.models;


/**
 * Created by
 * Stevica Trajanovic
 * stevica@shiftplanning.com
 * on 12/23/13.
 */
public abstract class BasicTimeModel extends BasicModel{
    public abstract String getStart();
    public abstract String getEnd();
    public abstract String getName();
    public abstract String getDescription();

    public static final int SHIFT = 0;
    public static final int AVAILABILITY = 1;
    public static final int EVENT = 2;
    public static final int LEAVE = 3;
    public static final int TIMECLOCK = 4;
    public static final int HOLIDAY = 5;

    /**
     * @return int:<br>
     *      0 - SHIFT<br>
     *      1 - AVAILABILITY<br>
     *      2 - EVENT<br>
     *      3 - LEAVE<br>
     *      4 - TIMECLOCK<br>
     *      5 - HOLIDAY<br>
     */
    public abstract int getItemType();

    public String getStartDate(){
        return getStart();
    }


}
