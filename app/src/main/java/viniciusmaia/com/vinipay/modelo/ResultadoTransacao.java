package viniciusmaia.com.vinipay.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 04/12/2017.
 */

public class ResultadoTransacao {
    private int id;
    private int timestamp;
    private double value;

    @SerializedName("destination_user")
    private Usuario usuario;

    private boolean success;
    private String status;

    public ResultadoTransacao(){}

    public ResultadoTransacao(int id, int timestamp, double value, Usuario usuario, boolean success, String status) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
        this.usuario = usuario;
        this.success = success;
        this.status = status;
    }

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
