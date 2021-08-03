package com.example.pharmachurch.data;

import android.provider.BaseColumns;

public class contantclass {
    private contantclass(){
    }
    public static final class patienttable implements BaseColumns
    {
            public static final String TABLE_NAME = "patient";

        public static final String COLUMN_PATIENT_CODE = BaseColumns._ID;
        public static final String COLUMN_PATIENT_NAME = "pat_name";

    }

    public static final class medicinetable implements BaseColumns
    {
        public static final String TABLE_NAME = "medicine";

        public static final String COLUMN_MEDICINE_NAME = "med_name";
        public static final String COLUMN_MEDICINE_DAY = "med_day";
        public static final String COLUMN_MEDICINE_DATE = "med_date";
        public static final String COLUMN_MEDICINE_TIME = "med_time";
        public static final String COLUMN_MEDICINE_PATID_FOR = "pat_code";
        public static final String COLUMN_MEDICINE_PATNAME_FOR = "pat_name";
    }
}
