package com.steewsc.testing.db;

import android.net.Uri;

/**
 * Created by Steewsc on 1.2.14..
 */
public class Tables {
    public static final String STR_CONTENT_URI =
           "content://com.steewsc.testing/";
    public static final Uri CONTENT_URI = Uri.parse(STR_CONTENT_URI);

    public interface Employees {
        public static final String TABLE_NAME = "employees";
        public static final Uri CONTENT_URI = Uri.parse(STR_CONTENT_URI + TABLE_NAME );
        public static final String ID = "_id";
        public static final String EMPLOYEE_ID = "employee_id";
        public static final String COMPANY_ID = "company_id";
        public static final String OBJECT = "object";
        public static final String DEFAULT_SORT = "employee_id";
        public static final String[] PROJECTION = new String[]{
            ID, EMPLOYEE_ID, COMPANY_ID, OBJECT
        };

        public static final String TABLE_CREATE = "create table if not exists "
                + TABLE_NAME + "("
                + ID + " integer primary key autoincrement, "
                + EMPLOYEE_ID + " long, "
                + COMPANY_ID + " long, "
                + OBJECT + " text"
                + ");";
    }

    public interface Shifts {
        public static final String TABLE_NAME = "shifts";
        public static final Uri CONTENT_URI = Uri.parse(STR_CONTENT_URI + TABLE_NAME );
        public static final String ID = "_id";
        public static final String SHIFT_ID = "shift_id";
        public static final String COMPANY_ID = "company_id";
        public static final String LOCATION_ID = "location_id";
        public static final String POSITION_ID = "postion_id";
        public static final String OBJECT = "object";
        public static final String DEFAULT_SORT = "shift_id";
        public static final String[] PROJECTION = new String[]{
                ID, SHIFT_ID, COMPANY_ID, OBJECT
        };

        public static final String TABLE_CREATE = "create table if not exists "
                + TABLE_NAME + "("
                + ID + " integer primary key autoincrement, "
                + SHIFT_ID + " long, "
                + COMPANY_ID + " long, "
                + LOCATION_ID + " long, "
                + POSITION_ID + " long, "
                + OBJECT + " text"
                + ");";
    }
}
