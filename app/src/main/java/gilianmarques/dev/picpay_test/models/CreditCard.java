package gilianmarques.dev.picpay_test.models;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;

import javax.crypto.SecretKey;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.utils.AESHelper;
import gilianmarques.dev.picpay_test.utils.MyApp;

public class CreditCard implements Serializable {
    private long number;
    private final long id;
    private int cvv, brandId;
    private String expireDate, ownerName;

    private static final int BRAND_AMEX = 0;
    private static final int BRAND_BCG = 1;
    private static final int BRAND_DINERS = 2;
    private static final int BRAND_DISCOVER = 3;
    private static final int BRAND_JCB = 4;
    private static final int BRAND_LASER = 5;
    private static final int BRAND_MAESTRO = 6;
    private static final int BRAND_MASTERCARD = 7;
    private static final int BRAND_SOLO = 8;
    private static final int BRAND_SWITCH = 9;
    private static final int BRAND_UNION_PAY = 10;
    private static final int BRAND_VISA = 11;

    public CreditCard() {
        /*para fins de teste isso servirá muito bem*/
        this.id = System.currentTimeMillis() + new Random().nextInt(10000);
    }

    private CreditCard(int id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    private int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public long getId() {
        return id;
    }

    /*------------------------------------------*/

    /**
     * @return uma {@link String} com o numero do cartão espaçado a cada 4 caracteres
     */
    public String getSpacedNumber() {
        String numbers = String.valueOf(getNumber());
        return numbers.replaceAll("....(?!$)", "$0 ");
    }


    public String getBrandName() {

        String[] mBrands = MyApp.getContext().getResources().getStringArray(R.array.accepted_brands);

        switch (getBrandId()) {
            case BRAND_AMEX:
                return mBrands[0];
            case BRAND_BCG:
                return mBrands[1];
            case BRAND_DINERS:
                return mBrands[2];
            case BRAND_DISCOVER:
                return mBrands[3];
            case BRAND_JCB:
                return mBrands[4];
            case BRAND_LASER:
                return mBrands[5];
            case BRAND_MAESTRO:
                return mBrands[6];
            case BRAND_MASTERCARD:
                return mBrands[7];
            case BRAND_SOLO:
                return mBrands[8];
            case BRAND_SWITCH:
                return mBrands[9];
            case BRAND_UNION_PAY:
                return mBrands[10];
            case BRAND_VISA:
                return mBrands[11];
            default:
                return "NAN";
        }
    }

    public int getLastFourDigits() {
        return Integer.parseInt(getSpacedNumber().split(" ")[3]);
    }

    public String toEncryptedString() {
        JSONObject mJsonObject = new JSONObject();

        try {
            mJsonObject.put("number", number);
            mJsonObject.put("cvv", cvv);
            mJsonObject.put("brandId", brandId);
            mJsonObject.put("expireDate", expireDate);
            mJsonObject.put("ownerName", ownerName);
            mJsonObject.put("id", id);

            SecretKey mSecretKey = AESHelper.generateKey();
            if (mSecretKey == null) {
                throw new NullPointerException("A chave não pode ser nula (Key can´t be null)");
            }

            String encripetdCard = AESHelper.encryptMsg(mJsonObject.toString(), mSecretKey);
            String strSecretKey = AESHelper.keyToString(mSecretKey);

            /*Não há lugar seguro em um dispositivo android rooteado para guardar uma chave de criptografia
             * a abordagem ideal e guardar a chave em um servidor através de uma conexão segura mas por se tratar
             * de um exemplo vou guarda-la em sharedPreferences mesmo*/
            SharedPreferences.Editor mEditor = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext()).edit();
            mEditor.putString(String.valueOf(id), strSecretKey).apply();


            return encripetdCard;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    public static CreditCard fromEncrypetdString(String id, String encriptedJson) {

        CreditCard mCreditCard = new CreditCard((int) Long.parseLong(id));

        String strKey = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext()).getString(id, null);

        if (strKey == null) {
            throw new NullPointerException("A chave não foi encontrada em SharedPreferences (Key wasn't found in SharedPreferences) ");
        }

        SecretKey mSecretKey = AESHelper.stringToKey(strKey);
        String strJson = AESHelper.decryptMsg(encriptedJson, mSecretKey);

        try {
            JSONObject mJsonObject = new JSONObject(strJson);

            mCreditCard.setNumber(mJsonObject.optLong("number"));
            mCreditCard.setCvv(mJsonObject.optInt("cvv"));
            mCreditCard.setBrandId(mJsonObject.optInt("brandId"));
            mCreditCard.setExpireDate(mJsonObject.optString("expireDate"));
            mCreditCard.setOwnerName(mJsonObject.optString("ownerName"));

            return mCreditCard;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
