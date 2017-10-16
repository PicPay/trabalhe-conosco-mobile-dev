package mobile.picpay.com.br.picpaymobile.entity;

import java.io.Serializable;

/**
 * Created by johonatan on 10/10/2017.
 */

public class RetTransacao implements Serializable{

    private int id;
    private int timestamp;
    private Pessoa destination_user;
    private boolean success;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Pessoa getDestination_user() {
        return destination_user;
    }

    public void setDestination_user(Pessoa destination_user) {
        this.destination_user = destination_user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
