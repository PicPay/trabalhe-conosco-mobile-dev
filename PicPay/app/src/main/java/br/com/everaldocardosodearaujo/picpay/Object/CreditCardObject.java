package br.com.everaldocardosodearaujo.picpay.Object;

/**
 * Created by E. Cardoso de Araújo on 15/03/2018.
 */

public class CreditCardObject {
    private long id;
    private String flag;
    private String name;
    private String numberCard;
    private String validity;
    private String ccv;

    public CreditCardObject(){
        this.id = 0;
        this.flag = "";
        this.name = "";
        this.numberCard = "";
        this.validity = "";
        this.ccv = "";
    }

    public CreditCardObject(long id, String flag, String name, String numberCard, String validity, String ccv){
        this.id = id;
        this.flag = flag;
        this.name = name;
        this.numberCard = numberCard;
        this.validity = validity;
        this.ccv = ccv;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }
}
