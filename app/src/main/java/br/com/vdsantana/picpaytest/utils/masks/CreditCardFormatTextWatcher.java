package br.com.vdsantana.picpaytest.utils.masks;

/**
 * Created by vd_sa on 01/04/2018.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ReplacementSpan;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Format a credit card number with padding every 4 digits. Optionally cut to specified maxLength
 */
public class CreditCardFormatTextWatcher implements TextWatcher {
    public static final int NO_MAX_LENGTH = -1;
    private int maxLength = NO_MAX_LENGTH;
    private int paddingPx;
    private boolean internalStopFormatFlag;

    /**
     * Create a credit card formatter with no max length and a padding specified in pixels
     *
     * @param paddingPx padding in pixels unit
     */
    public CreditCardFormatTextWatcher(int paddingPx) {
        setPaddingPx(paddingPx);
    }

    /**
     * Create a credit card formatter with no max length and a padding of 1 em (depends on text size).
     * <p>
     * The padding is not automatically updated if the text size or typeface are changed in the textview).
     *
     * @param textView the widget you want to format
     */
    public CreditCardFormatTextWatcher(@NonNull TextView textView) {
        setPaddingEm(textView, 1f);
    }

    /**
     * Create a credit card formatter with no max length and a padding specified in em (depends on text size).
     * <p>
     * The padding is not automatically updated if the text size or typeface are changed in the textview).
     *
     * @param textView  the widget you want to format
     * @param paddingEm padding in em unit (character size unit)
     */
    public CreditCardFormatTextWatcher(@NonNull TextView textView, float paddingEm) {
        setPaddingEm(textView, paddingEm);
    }

    /**
     * Create a credit card formatter with no max length and a padding specified in SP Unit (depends on the scale applied to text).
     *
     * @param context   any Context
     * @param paddingSp the padding in SP unit
     */
    public CreditCardFormatTextWatcher(@NonNull Context context, float paddingSp) {
        setPaddingSp(context, paddingSp);
    }

    /**
     * Change the padding, do not take effect until next text change
     *
     * @param paddingPx padding in pixels unit
     */
    public void setPaddingPx(int paddingPx) {
        this.paddingPx = paddingPx;
    }

    /**
     * Change the padding, do not take effect until next text change
     *
     * @param textView the widget you want to format
     * @param em       padding in em unit (character size unit)
     */
    public void setPaddingEm(@NonNull TextView textView, float em) {
        float emSize = textView.getPaint().measureText("x");
        setPaddingPx((int) (em * emSize));
    }

    /**
     * Change the padding, do not take effect until next text change
     *
     * @param context   any Context
     * @param paddingSp the padding in SP unit
     */
    public void setPaddingSp(@NonNull Context context, float paddingSp) {
        setPaddingPx((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, paddingSp, context.getResources().getDisplayMetrics()));
    }

    /**
     * Change maxLength of the credit card number, does not take effect until next text change
     *
     * @param maxLength new max length
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (internalStopFormatFlag) {
            return;
        }
        internalStopFormatFlag = true;
        formatCardNumber(s, paddingPx, maxLength);
        internalStopFormatFlag = false;
    }

    /**
     * Format the provided widget card number (useful if you want to reformat it after changing padding)
     *
     * @param textView the widget containing the credit card number
     */
    public void formatCardNumber(@NonNull TextView textView) {
        afterTextChanged(textView.getEditableText());
    }

    public static void formatCardNumber(@NonNull Editable ccNumber, int paddingPx) {
        formatCardNumber(ccNumber, paddingPx, NO_MAX_LENGTH);
    }

    public static void formatCardNumber(@NonNull Editable ccNumber, int paddingPx, int maxLength) {
        int textLength = ccNumber.length();
        // first remove any previous span
        PaddingRightSpan[] spans = ccNumber.getSpans(0, ccNumber.length(), PaddingRightSpan.class);
        for (int i = 0; i < spans.length; i++) {
            ccNumber.removeSpan(spans[i]);
        }
        // then truncate to max length
        if (maxLength > 0 && textLength > maxLength - 1) {
            ccNumber.replace(maxLength, textLength, "");
        }
        // finally add margin spans
        for (int i = 1; i <= ((textLength - 1) / 4); i++) {
            int end = i * 4;
            int start = end - 1;
            PaddingRightSpan marginSPan = new PaddingRightSpan(paddingPx);
            ccNumber.setSpan(marginSPan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public static class PaddingRightSpan extends ReplacementSpan {

        private int mPadding;

        public PaddingRightSpan(int padding) {
            mPadding = padding;
        }

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            float[] widths = new float[end - start];
            paint.getTextWidths(text, start, end, widths);
            int sum = mPadding;
            for (int i = 0; i < widths.length; i++) {
                sum += widths[i];
            }
            return sum;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
            canvas.drawText(text, start, end, x, y, paint);
        }

    }
}
