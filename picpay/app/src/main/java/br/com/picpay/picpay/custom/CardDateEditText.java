package br.com.picpay.picpay.custom;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;

import io.card.payment.CreditCard;

public class CardDateEditText extends CustomEditText {

    private boolean isUpdating;
    private int positioning[] = {1, 2, 4, 5};
    private CreditCard creditCard;

    public CardDateEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();

    }

    public CardDateEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CardDateEditText(Context context) {
        super(context);
        initialize();
    }

    public boolean isValid() {
        String date = getCleanText();
        if (date.length() == 5) {
            CreditCard creditCard = new CreditCard();
            creditCard.expiryMonth = Integer.parseInt(date.substring(0, 2));
            creditCard.expiryYear = Integer.parseInt("20" + date.substring(3, 5));
            boolean isValid = creditCard.isExpiryValid();
            setCreditCard(isValid ? creditCard : null);
            return isValid;
        } else {
            return false;
        }
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getCleanText() {
        String text = CardDateEditText.this.getText().toString();
        text.replaceAll("[^0-9]*", "");
        return text;
    }

    private void initialize() {
        final int maxNumberLength = 4;
        this.setKeyListener(keylistenerNumber);

        this.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String current = s.toString();
                if (isUpdating) {
                    isUpdating = false;
                    return;

                }

                String number = current.replaceAll("[^0-9]*", "");
                if (number.length() > 4) {
                    number = number.substring(0, 4);
                }

                int length = number.length();

                String paddedNumber = padNumber(number, maxNumberLength);

                String part1 = paddedNumber.substring(0, 2);
                String part2 = paddedNumber.substring(2, 4);

                String phone = part1;
                if (part1.trim().length() >= 2) {
                    phone += "/" + part2.trim();
                }

                isUpdating = true;

                if (part1.trim().isEmpty() && part2.trim().isEmpty()) {
                    phone = "";
                }

                CardDateEditText.this.setText(phone);

                if (!phone.isEmpty()) {
                    if (length < 1) {
                        CardDateEditText.this.setSelection(positioning[length]);
                    } else {
                        CardDateEditText.this.setSelection(positioning[length - 1]);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }
        });
    }

    protected String padNumber(String number, int maxLength) {
        StringBuilder padded = new StringBuilder(number);
        for (int i = 0; i < maxLength - number.length(); i++)
            padded.append(" ");
        return padded.toString();

    }

    private final KeylistenerNumber keylistenerNumber = new KeylistenerNumber();

    private class KeylistenerNumber extends NumberKeyListener {

        public int getInputType() {
            return InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;

        }

        @Override
        protected char[] getAcceptedChars() {
            return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        }
    }
}
