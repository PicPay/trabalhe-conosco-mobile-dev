package com.tmontovaneli.picpaychallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by tmontovaneli on 23/08/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Serializable {

    private Long id;

    private Long timestamp;

    private double value;

    @JsonProperty("destination_user")
    private User user;

    private boolean success;

    private String status;


    public Long getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public double getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", value=" + value +
                ", user=" + user +
                ", success=" + success +
                ", status='" + status + '\'' +
                '}';
    }
}
