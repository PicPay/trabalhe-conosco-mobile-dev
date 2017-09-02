package br.com.dalcim.picpay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Toast;

import br.com.dalcim.picpay.utils.DialogUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author Wiliam
 * @since 27/08/2017
 */
public abstract class BaseActivity extends AppCompatActivity{
    private ProgressDialog progressDialog;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStop() {
        if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        super.onStop();
    }

    public void showLoadDialog() {
        showLoadDialog(true);
    }

    protected void showLoadDialog(boolean lockOrientation){
        if(lockOrientation)  lockOrientation();

        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("");
            progressDialog.setMessage(this.getResources().getString(R.string.load));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }

        progressDialog.show();
    }

    public void hideLoadDialog() {
        hideLoadDialog(true);
    }

    protected void hideLoadDialog(boolean liberaOrientacao){
        if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        if(liberaOrientacao) unlockChangeOrientation();
    }

    protected void lockOrientation(){

        switch (((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_90:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case Surface.ROTATION_180:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                break;
            case Surface.ROTATION_270:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                break;
            default :
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void unlockChangeOrientation(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    protected void showMessage(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    protected void showErro(Exception e, String s){
        showMessage(s);
        Log.e("LOG_PICPAY", s, e);
    }

    protected void logMessage(String s){
        Log.i("LOG_PICPAY", s);
    }

    protected void showMessage(@StringRes int resString) {
        Toast.makeText(this, resString, Toast.LENGTH_SHORT).show();
    }

    protected void showConfirmDialog(String title, String message){
        DialogUtils.showConfirmDialog(this, title, message);
    }
}
