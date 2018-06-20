package br.com.gsas.app.picpay.Connection;

import java.util.List;

public interface MyCallback<T> {

    public void sucess(List<T> list);
    public void empty();
    public void failure(String msg);

}
