package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.everaldocardosodearaujo.picpay.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionActivity extends Activity {

    private CircleImageView idImg;
    private TextView idUserName;
    private TextView idName;
    private Button  idBtnPay;
    private TextView idTxvCardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        this.inflate();
    }

    private void inflate(){
        this.idImg = (CircleImageView) findViewById(R.id.idImg);
        this.idName = (TextView) findViewById(R.id.idName);
        this.idUserName = (TextView) findViewById(R.id.idUserName);
        this.idBtnPay = (Button) findViewById(R.id.idBtnPay);
        this.idTxvCardNumber = (TextView) findViewById(R.id.idTxvCardNumber);
    }
}
