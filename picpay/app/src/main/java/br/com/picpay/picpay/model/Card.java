package br.com.picpay.picpay.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.UUID;

import io.card.payment.CreditCard;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Card extends RealmObject implements Parcelable {

    @PrimaryKey
    private Integer id = UUID.randomUUID().hashCode();

    private String cardNumber;
    private int expiryMonth = 0;
    private int expiryYear = 0;
    private String cvv;
    private String postalCode;
    private String cardholderName;

    public Card() {
    }

    protected Card(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        cardNumber = in.readString();
        expiryMonth = in.readInt();
        expiryYear = in.readInt();
        cvv = in.readString();
        postalCode = in.readString();
        cardholderName = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    @NonNull
    public CreditCard getCreditCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.cardNumber = cardNumber;
        creditCard.expiryMonth = expiryMonth;
        creditCard.expiryYear = expiryYear;
        creditCard.cvv = cvv;
        creditCard.postalCode = postalCode;
        creditCard.cardholderName = cardholderName;
        return creditCard;
    }

    public void setCreditCard(@NonNull CreditCard creditCard) {
        this.cardNumber = creditCard.getFormattedCardNumber();
        this.expiryMonth = creditCard.expiryMonth;
        this.expiryYear = creditCard.expiryYear;
        this.cvv = creditCard.cvv;
        this.postalCode = creditCard.postalCode;
        this.cardholderName = creditCard.cardholderName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(cardNumber);
        dest.writeInt(expiryMonth);
        dest.writeInt(expiryYear);
        dest.writeString(cvv);
        dest.writeString(postalCode);
        dest.writeString(cardholderName);
    }

    @NonNull
    public Integer getId() {
        return id;
    }
}
