package gilianmarques.dev.picpay_test.asyncs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import gilianmarques.dev.picpay_test.utils.MyApp;

public class RoundImageAsync extends AsyncTask<String, Void, Bitmap> {

    private Callback mCallback;

    public RoundImageAsync(Callback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            InputStream mInputStream = new BufferedInputStream(url.openStream());
            return cutBitmap(BitmapFactory.decodeStream(mInputStream));

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mCallback.result(new BitmapDrawable(MyApp.getContext().getResources(),bitmap));
        super.onPostExecute(bitmap);
    }


    /**
     * @param bitmap o bitmap quadrado do servidor
     *
     * @return um bitmap redondo
     */
    private Bitmap cutBitmap(Bitmap bitmap) {

        Bitmap mResultBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mResultBitmap);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return mResultBitmap;
    }

    public interface Callback {
        void result(Drawable mDrawable);
    }

}
