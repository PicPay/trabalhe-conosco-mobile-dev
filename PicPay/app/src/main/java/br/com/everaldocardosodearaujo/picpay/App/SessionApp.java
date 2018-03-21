package br.com.everaldocardosodearaujo.picpay.App;

import android.app.ProgressDialog;

import br.com.everaldocardosodearaujo.picpay.Repository.CreditCardRepository;

/**
 * Created by E. Cardoso de Araújo on 15/03/2018.
 */

public class SessionApp {
    public static ProgressDialog PG_DIALOG;

    public static String DB_NAME = "PICPAY_DB";
    public static int DB_VERSION = 1;

    public static final String MASK_DATE_MONTH_YEAR = "##/####";
    public static final String MASK_CCV = "###";
    public static final String MASK_NUMBER_CREDIT_CARD = "####.####.####.####";
    public static final String MASK_NUMBER = "####.##";

    public static CreditCardRepository TB_CREDIT_CARD;
}
