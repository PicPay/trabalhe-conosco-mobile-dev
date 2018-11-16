package project.picpay.test.home.model.transaction_sended;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rodrigo Oliveira on 13/11/2018 for app.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public class Transaction {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("destination_user")
    @Expose
    private DestinationUser destinationUser;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public DestinationUser getDestinationUser() {
        return destinationUser;
    }

    public void setDestinationUser(DestinationUser destinationUser) {
        this.destinationUser = destinationUser;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
