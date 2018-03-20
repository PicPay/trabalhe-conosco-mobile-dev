package br.com.everaldocardosodearaujo.picpay.App;

import br.com.everaldocardosodearaujo.picpay.Repository.CreditCardRepository;

/**
 * Created by E. Cardoso de Araújo on 15/03/2018.
 */

public class SessionApp {
    public static String DB_NAME = "PICPAY_DB";
    public static int DB_VERSION = 1;

    public static final String MASK_DATE_MONTH_YEAR = "##/####";
    public static final String MASK_CCV = "###";
    public static final String MASK_NUMBER_CREDIT_CARD = "####.####.####.####";

    public static CreditCardRepository TB_CREDIT_CARD;
}
