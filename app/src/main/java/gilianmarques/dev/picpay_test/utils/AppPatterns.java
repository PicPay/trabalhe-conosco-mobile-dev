package gilianmarques.dev.picpay_test.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

public class AppPatterns {
    public static final int ERROR = -1, SUCCESS = -2;


    public static void vibrate(int type) {
        if (type != ERROR && type != SUCCESS) return;

        final Vibrator mVibrator = (Vibrator) MyApp.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (mVibrator == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (type == SUCCESS)
                mVibrator.vibrate(VibrationEffect.createWaveform(new long[]{70, 70, 70}, VibrationEffect.DEFAULT_AMPLITUDE));
            else
                mVibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

        } else {
            //depreciado  na  API 26


            if (type == SUCCESS)

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 2; i++) {
                            mVibrator.vibrate(500);
                            try {
                                Thread.sleep(45);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();


            else mVibrator.vibrate(500);
        }
    }


    /**
     * @param mActivity uma instancia de activity para  rodar o metodo na {@link android.support.annotation.UiThread}
     *                  evitando um possivel erro ( Can't toast on a thread that has not called Looper.prepare())
     * @param message   a mensagem a ser exibida
     * @param type      o tipo de vibração que varia entre ERRO e SUCESSO
     *                  <p>
     *                  notifica o usuariosobre o resultado de uma açãoque pode ser bem ou mal sucedida
     */
    public static void notifyUser(final Activity mActivity, final String message, final int type) {

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
                vibrate(type);
            }
        });
    }

    public static float convertDpToPx(final float dp) {
        return dp * MyApp.getContext().getResources().getDisplayMetrics().density;
    }

    public static String convertCurrency(@NonNull String amount) {

        try {
            Currency mCurrency = Currency.getInstance(Locale.getDefault());
            NumberFormat mFormater = NumberFormat.getCurrencyInstance(Locale.getDefault());
            mFormater.setCurrency(mCurrency);
            return mFormater.format(new BigDecimal(amount));
        } catch (Exception e) {
            e.printStackTrace();
            return amount;
        }
    }

    public static BigDecimal toDecimal(@NonNull String amount){
        try {
            amount = amount.replaceAll("[^\\d.,]", "");
            final NumberFormat mFormater = NumberFormat.getNumberInstance(Locale.getDefault());
            if (mFormater instanceof DecimalFormat) ((DecimalFormat) mFormater).setParseBigDecimal(true);
            return new BigDecimal(mFormater.parse(amount).toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return new BigDecimal("0");
        }
    }



}
