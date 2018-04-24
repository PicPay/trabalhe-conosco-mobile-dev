package gilianmarques.dev.picpay_test.asyncs;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
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
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.utils.MyApp;

public class ProfilePictureUtils {
    private static String bitmapID, mCachePath, photoUrl;

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
            new ProfilePicDownloader(mCallback).execute(photoUrl);
        } else mSaveCallback.done();

    }

    private void saveCachePic(Bitmap mBitmap) throws IOException {
        FileOutputStream mOutputStream = null;
        try {
            mOutputStream = new FileOutputStream(new File(mCachePath, bitmapID));
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, mOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mOutputStream != null) mOutputStream.close();
        }
    }

    private boolean isPicCached() {
        return new File(mCachePath, bitmapID).exists();
    }

    private static BitmapDrawable getCachePic() {

        File mFile = new File(mCachePath, bitmapID);
        Bitmap mBitmap;
        try {
            mBitmap = BitmapFactory.decodeStream(new FileInputStream(mFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return new BitmapDrawable(MyApp.getContext().getResources(), mBitmap);
    }

    public void loadProfilePicture(final ImageView ivProfileImage) {
        new ProfilePictureLoader(ivProfileImage).execute();
    }

    public interface SaveCallback {
        void done();
    }

    private static class ProfilePicDownloader extends AsyncTask<String, Void, Bitmap> {

        private Callback mCallback;

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

        public interface Callback {
            void result(@Nullable Bitmap mBitmap);
        }
    }

    private static class ProfilePictureLoader extends AsyncTask<Void, Void, Drawable> {
        @SuppressLint("StaticFieldLeak")
        private ImageView ivProfileImage;

        ProfilePictureLoader(ImageView ivProfileImage) {
            this.ivProfileImage = ivProfileImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ivProfileImage.setImageResource(R.drawable.vec_place_holder);
        }

        @Override
        protected Drawable doInBackground(Void... voids) {
            return getCachePic();
        }


        @Override
        protected void onPostExecute(Drawable mDrawable) {
            if (mDrawable != null) ivProfileImage.setImageDrawable(mDrawable);
            super.onPostExecute(mDrawable);
        }


    }


}
