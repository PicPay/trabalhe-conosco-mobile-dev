package br.com.picpay.picpay.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import br.com.picpay.picpay.R;
import io.card.payment.CardType;
import io.card.payment.CreditCard;

public class CardUtil {

    public static String getNumberFormated(CreditCard creditCard) {
        char[] mask = getMaskCard(creditCard).toCharArray();
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (char c : mask) {
            if (c == '#') {
                if (index < creditCard.cardNumber.length()) {
                    sb.append(creditCard.cardNumber.charAt(index));
                    index++;
                } else {
                    break;
                }
            } else {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    private static String getMaskCard(@NonNull CreditCard creditCard) {
        switch (creditCard.getCardType()) {
            case AMEX:
                return "#### ###### #####";
            case DINERSCLUB:
                return "#### ###### ####";
            case MASTERCARD:
            case VISA:
            case MAESTRO:
            case DISCOVER:
            case JCB:
            case UNKNOWN:
            case INSUFFICIENT_DIGITS:
            default:
                return "#### #### #### ####";
        }
    }

    public static Drawable getIconCard(@NonNull CardType cardType, @NonNull Context context) {
        Drawable iconCard = null;
        switch (cardType) {
            case AMEX:
                iconCard = ContextCompat.getDrawable(context, R.drawable.icon_amex);
                break;
            case MASTERCARD:
                iconCard = ContextCompat.getDrawable(context, R.drawable.icon_master);
                break;
            case VISA:
                iconCard = ContextCompat.getDrawable(context, R.drawable.icon_visa);
                break;
            case DINERSCLUB:
                iconCard = ContextCompat.getDrawable(context, R.drawable.icon_dinners);
                break;
            case MAESTRO:
                iconCard = ContextCompat.getDrawable(context, R.drawable.icon_elo);
                break;
            case JCB:
            case DISCOVER:
                iconCard = ContextCompat.getDrawable(context, R.drawable.icon_undefined);
                break;
            case UNKNOWN:
                break;
            case INSUFFICIENT_DIGITS:
                break;
        }
        return iconCard;
    }

    public static String getDateFormated(@NonNull CreditCard creditCard) {
        if (creditCard.expiryMonth > 0 && creditCard.expiryYear > 0) {
            return String.format("%s/%s", String.valueOf(creditCard.expiryMonth < 10 ? "0" + creditCard.expiryMonth : creditCard.expiryMonth), String.valueOf(creditCard.expiryYear).substring(2));
        } else {
            return "";
        }
    }
}
