package br.com.gsas.app.picpay.Domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "feed")
public class Feed {

    @SerializedName("id_contato")
    @PrimaryKey(autoGenerate = true)
    @Expose
    private int id;

    @SerializedName("value")
    private double valor;

    @SerializedName("success")
    private boolean success;

    @SerializedName("status")
    private String status;

    @Expose
    private String msg;

    @SerializedName("destination_user")
    private Contato contato;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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
