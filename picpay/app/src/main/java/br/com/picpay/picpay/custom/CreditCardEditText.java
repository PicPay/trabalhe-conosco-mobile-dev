package br.com.picpay.picpay.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;

import br.com.picpay.picpay.util.CardUtil;
import io.card.payment.CardType;
import io.card.payment.CreditCard;

public class CreditCardEditText extends CustomEditText implements TextWatcher {

    private CreditCard creditCard = new CreditCard();
    private CreditCardTextListerner listerner;
    private String originalValue;

    public CreditCardEditText(Context context) {
        super(context);
        init();
    }

    public CreditCardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreditCardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setListerner(CreditCardTextListerner listerner) {
        this.listerner = listerner;
    }

    private void init() {
        addTextChangedListener(this);
        setFilters(new InputFilter[]{filterNumber});
    }

    private InputFilter filterNumber = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!Character.toString(source.charAt(i)).equals(" ") && !Character.isDigit(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String currentText = s.toString();
        if (!currentText.equals(originalValue)) {
            if (creditCard != null) {
                String cardNumber = s.toString().replace(" ", "");
                creditCard.cardNumber = cardNumber.substring(0, cardNumber.length() > 16 ? 16 : cardNumber.length());
                if (listerner != null) {
                    if (creditCard.cardNumber.equalsIgnoreCase("1111111111111111")) {
                        listerner.onCardType(CardType.VISA);
                    } else {
                        listerner.onCardType(creditCard.getCardType());
                    }
                }
                originalValue = CardUtil.getNumberFormated(creditCard);
                setText(originalValue.trim());
                setSelection(length());
            } else {
                originalValue = s.toString();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface CreditCardTextListerner {
        void onCardType(CardType cardType);
    }

    @NonNull
    public CreditCard getCreditCard() {
        return creditCard;
    }
}
