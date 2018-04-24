package gilianmarques.dev.picpay_test.models;

public class CreditCard {
    private long number;
    private int cvv, brandId;
    private String expireDate, ownerName, brand;
// TODO: 23/04/2018 resolver oque fazer com isso
    /*public static final int BRAND_UNKNOWN = -1;
    public static final int BRAND_AMEX = 0;
    public static final int BRAND_BCG = 1;
    public static final int BRAND_DINERS =2 ;
    public static final int BRAND_DISCOVER =3 ;
    public static final int BRAND_JCB = 4;
    public static final int BRAND_LASER=5 ;
    public static final int BRAND_MAESTRO = 6;
    public static final int BRAND_MASTERCARD = 7;
    public static final int BRAND_SOLO = 8;
    public static final int BRAND_SWITCH = 9;
    public static final int BRAND_UNION_PAY = 10;
    public static final int BRAND_VISA = 11;*/



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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @return uma {@link String} com o numero do cartão espaçado a cada 4 caracteres
     */
    public String getSpacedNumber() {
        String numbers = String.valueOf(getNumber());
        return numbers.replaceAll("....(?!$)", "$0 ");
    }


}
