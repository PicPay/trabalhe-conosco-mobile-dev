package br.com.dalcim.picpay;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import br.com.dalcim.picpay.utils.DialogUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

    protected void showLoadDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("");
            progressDialog.setMessage(this.getResources().getString(R.string.load));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }

        progressDialog.show();
    }

    protected void hideLoadDialog(){
        if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
    }

    protected void showConfirmDialog(String title, String message){
        DialogUtils.showConfirmDialog(this, title, message);
    }
}
