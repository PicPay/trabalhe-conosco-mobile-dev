package stolato.com.br.paypalpayment.Model;

public class Card {
    private int id;
    private String nome;
    private String number;
    private String expiry;

    public Card(int id,String nome, String number, String expiry){
        this.id = id;
        this.nome = nome;
        this.number = number;
        this.expiry = expiry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}
