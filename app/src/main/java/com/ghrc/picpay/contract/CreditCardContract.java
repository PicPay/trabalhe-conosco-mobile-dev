package com.ghrc.picpay.contract;

import android.provider.BaseColumns;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class CreditCardContract {
    public CreditCardContract() {
    }
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_CREDIT_CARD =
            "CREATE TABLE " + CreditCardEntry.TABLE_NAME  + " (" +
                    CreditCardEntry._ID + " INTEGER PRIMARY KEY," +
                    CreditCardEntry.COLUMN_CARD_NUMBER + TEXT_TYPE + COMMA_SEP +
                    CreditCardEntry.COLUMN_CVV + TEXT_TYPE + COMMA_SEP+
                    CreditCardEntry.COLUMN_EXPIRY + TEXT_TYPE +
                    " )";

    public static class CreditCardEntry implements BaseColumns {
        public  static final String TABLE_NAME = "credit_card";
        public static final String COLUMN_CARD_NUMBER = "card_number";
        public static final String COLUMN_CVV = "cvv";
        public static final String COLUMN_EXPIRY = "expiry_date";
    }
}
