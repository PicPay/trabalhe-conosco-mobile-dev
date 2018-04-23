package gilianmarques.dev.picpay_test.models;

public class CreditCard {
    private long number;
    private int cvv;
    private String expireDate, ownerName, brand;

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
