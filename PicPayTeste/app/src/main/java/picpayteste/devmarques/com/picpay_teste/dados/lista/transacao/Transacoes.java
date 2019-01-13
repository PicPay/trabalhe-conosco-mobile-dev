package picpayteste.devmarques.com.picpay_teste.dados.lista.transacao;

import java.io.Serializable;

import picpayteste.devmarques.com.picpay_teste.dados.lista.usuario.Usuario;

public class Transacoes implements Serializable {

    /*"id":12314,
      "timestamp":1547227700,
      "value":"10,00",
      "destination_user":{
         "id":1003,
         "name":"Márcia da Silva",
         "img":"https://randomuser.me/api/portraits/women/57.jpg",
         "username":"@marcia.silva"
      },
      "success":false,
      "status":"Recus*/

    private Long id;
    private Long timestamp;
    private String value;
    private Usuario destination_user;
    private boolean success;
    private String status;

    public Transacoes(Long id, Long timestamp, String value, Usuario destination_user, boolean success, String status) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
        this.destination_user = destination_user;
        this.success = success;
        this.status = status;
    }

    public Transacoes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Usuario getDestination_user() {
        return destination_user;
    }

    public void setDestination_user(Usuario destination_user) {
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
