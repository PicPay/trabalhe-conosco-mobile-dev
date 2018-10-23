package stolato.com.br.paypalpayment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    public ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        logo = (ImageView) findViewById(R.id.logo);
        start();
    }

    public void start(){
        Thread thread = new Thread(){
            public void run(){
                try {
                    Animation a = new AlphaAnimation(0.00f, 1.00f);
                    a.setDuration(750);
                    logo.setAnimation(a);
                    sleep(750);
                    Intent intent;
                    intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
