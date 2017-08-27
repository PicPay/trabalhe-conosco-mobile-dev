package com.ghrc.picpay.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class CreditCardNumberFormattingTextWatcher implements TextWatcher {
    /** Character for card number section separator. */
    private static final String SEPARATOR = " ";

    /**
     * Whether to format the credit card number. If true, spaces will be inserted
     * automatically between each group of 4 digits in the credit card number as the user types.
     * This is set to false if the user types a dash or deletes one of the auto-inserted spaces.
     */
    private boolean mFormattingEnabled = true;

    /**
     * Whether the change was caused by ourselves.
     * This is set true when we are manipulating the text of EditText,
     * and all callback functions should check this boolean to avoid infinite recursion.
     */
    private boolean mSelfChange = false;

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mSelfChange || !mFormattingEnabled) return;
        // If user enters non-digit characters, do not format.
        if (count > 0 && hasDashOrSpace(s, start, count)) {
            mFormattingEnabled = false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mSelfChange || !mFormattingEnabled) return;
        // If user deletes non-digit characters, do not format.
        if (count > 0 && hasDashOrSpace(s, start, count)) {
            mFormattingEnabled = false;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mSelfChange) return;
        mSelfChange = true;

        if (mFormattingEnabled) {
            removeSeparators(s);
            // If number is too long, do not format it and remove all
            // previous separators.
            if (s.length() > 16) {
                mFormattingEnabled = false;
            } else {
                insertSeparators(s);
            }
        }
        // If user clears the input, re-enable formatting
        if (s.length() == 0) mFormattingEnabled = true;

        mSelfChange = false;
    }

    public static void removeSeparators(Editable s) {
        int index = TextUtils.indexOf(s, SEPARATOR);
        while (index >= 0) {
            s.delete(index, index + 1);
            index = TextUtils.indexOf(s, SEPARATOR, index + 1);
        }
    }

    public static void insertSeparators(Editable s) {
        final int[] positions = {4, 9, 14 };
        for (int i : positions) {
            if (s.length() > i) {
                s.insert(i, SEPARATOR);
            }
        }
    }

    public static boolean hasDashOrSpace(final CharSequence s, final int start,
                                         final int count) {
        return TextUtils.indexOf(s, " ", start, start + count) != -1
                || TextUtils.indexOf(s, "-", start, start + count) != -1;
    }
}
