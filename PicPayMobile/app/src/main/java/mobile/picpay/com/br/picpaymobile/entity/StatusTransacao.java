package mobile.picpay.com.br.picpaymobile.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by johonatan on 09/10/2017.
 */

public class StatusTransacao {
    @SerializedName("StatusTransacao")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
