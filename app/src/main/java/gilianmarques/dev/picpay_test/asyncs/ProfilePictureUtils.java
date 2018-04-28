package gilianmarques.dev.picpay_test.asyncs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.utils.MyApp;

public class ProfilePictureUtils {
    private final String bitmapID;
    private final String mCachePath;
    private final String photoUrl;
//    private static final String TAG = ProfilePictureUtils.class.getSimpleName();

    public ProfilePictureUtils(Contact mContact) {
        mCachePath = MyApp.getContext().getCacheDir().getAbsolutePath().concat(File.separator);
        bitmapID = String.valueOf(mContact.getId()).concat(".profpic");
        photoUrl = mContact.getPhoto();
    }

    public void saveProfilePic(@NonNull final SaveCallback mSaveCallback) {
        if (!isPicCached()) {

            ProfilePicDownloader.Callback mCallback = new ProfilePicDownloader.Callback() {
                @Override
                public void result(Bitmap mBitmap) {
                    if (mBitmap == null) return;
                    try {
                        saveCachePic(mBitmap);
                        mSaveCallback.done();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            /*  Executo essa Asynck em conjunto com as outras para melhorar a performance. Nos testes
                a resposta foi 500mls mais rapida  */
            new ProfilePicDownloader(mCallback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, photoUrl);

        } else mSaveCallback.done();

    }

    @Nullable
    public Drawable getProfilePic() {
        try {
            return getCachePic();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveCachePic(Bitmap mBitmap) throws IOException {
        FileOutputStream mOutputStream = null;
        try {
            mOutputStream = new FileOutputStream(new File(mCachePath, bitmapID));
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, mOutputStream);
            //Log.i(TAG, "saveCachePic: Bitmap salvo em: " + mCachePath.concat(bitmapID));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mOutputStream != null) mOutputStream.close();
        }
    }

    private boolean isPicCached() {
        return new File(mCachePath, bitmapID).exists();
    }

    private BitmapDrawable getCachePic() throws IOException {

        File mFile = new File(mCachePath, bitmapID);
        FileInputStream mFileInputStream = new FileInputStream(mFile);
        Bitmap mBitmap = BitmapFactory.decodeStream(mFileInputStream);
        mFileInputStream.close();
        return new BitmapDrawable(MyApp.getContext().getResources(), mBitmap);
    }

    public static void getPicAsync(Contact mContact, Callback mCallback) {
        new AsyncProfilePic(mContact, mCallback).execute();
    }

    private static class ProfilePicDownloader extends AsyncTask<String, Void, Bitmap> {

        private final Callback mCallback;

        ProfilePicDownloader(Callback mCallback) {
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
            mCallback.result(bitmap);
            super.onPostExecute(bitmap);
        }


        /**
         * @param bitmap o bitmap quadrado do servidor
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

        interface Callback {
            void result(@Nullable Bitmap mBitmap);
        }
    }

    /**
     * Obtem a foto de perfil do contato de forma asincrona
     */
    private static class AsyncProfilePic extends AsyncTask<Void, Void, Drawable> {
        private final Contact mContact;
        private final Callback mCallback;

        private AsyncProfilePic(Contact mContact, Callback mCallback) {
            this.mContact = mContact;
            this.mCallback = mCallback;
        }


        @Override
        protected Drawable doInBackground(Void... voids) {
            /*crio uma nova instancia pra poder acessar os metodos e variaveis n estaticos*/
            return new ProfilePictureUtils(mContact).getProfilePic();
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            mCallback.result(drawable);
            super.onPostExecute(drawable);

        }
    }

    public interface SaveCallback {
        void done();
    }

    public interface Callback {
        void result(Drawable mDrawable);
    }

}
