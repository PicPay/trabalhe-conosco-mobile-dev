package com.ghrc.picpay.contract;

import android.provider.BaseColumns;

/**
 * Created by Guilherme on 29/08/2017.
 */

public class TransactionContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String DATETIME_TYPE = " DATETIME";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_TRANSACTION =
            "CREATE TABLE " + TransactionContract.TransactionEntity.TABLE_NAME  + " (" +
                    TransactionEntity._ID + " INTEGER PRIMARY KEY," +
                    TransactionEntity.COLUMN_VALUE + REAL_TYPE + COMMA_SEP +
                    TransactionEntity.COLUMN_USER_DESTINATION_ID + INT_TYPE + COMMA_SEP +
                    TransactionEntity.COLUMN_DATE + DATETIME_TYPE + " ) ";
    public static class TransactionEntity implements BaseColumns {
        public  static final String TABLE_NAME = "transaction_history";
        public static final String  COLUMN_VALUE = "value";
        public static final String COLUMN_USER_DESTINATION_ID = "user_destination_id";
        public static final String COLUMN_DATE = "data";
    }
}
