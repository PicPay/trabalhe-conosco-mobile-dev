package gilianmarques.dev.picpay_test.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import gilianmarques.dev.picpay_test.utils.MyApp;

public  class MyActivity extends AppCompatActivity {

    protected MyApp mMyApp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyApp = (MyApp)this.getApplicationContext();
    }
    protected void onResume() {
        super.onResume();
        mMyApp.setCurrentActivity(this);
    }
    protected void onPause() {
        clearReferences();
        super.onPause();
    }
    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    private void clearReferences(){
        Activity currActivity = mMyApp.getCurrentActivity();
        if (this.equals(currActivity))
            mMyApp.setCurrentActivity(null);
    }


}
