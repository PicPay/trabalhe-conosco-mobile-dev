package mobiledev.erickgomes.picpayapp.models;

/**
 * Created by erickgomes on 23/03/2018.
 */

public class Transaction {

    private int id;
    private int timestamp;
    private double value;
    private Friend destination_user;
    private boolean success;
    private String status;


    public int getId() {
        return id;
    }


    public int getTimestamp() {
        return timestamp;
    }


    public double getValue() {
        return value;
    }


    public Friend getDestination_user() {
        return destination_user;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

}
