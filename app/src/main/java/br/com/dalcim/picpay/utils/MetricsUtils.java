package br.com.dalcim.picpay.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * @author Wiliam
 * @since 27/08/2017
 */

public class MetricsUtils {
    public static float toDp(Context context, int px){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
    }
}
