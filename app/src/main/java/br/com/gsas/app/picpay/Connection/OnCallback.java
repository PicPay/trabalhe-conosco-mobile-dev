package br.com.gsas.app.picpay.Connection;

public interface OnCallback <T> {

    public void sucess(T element);
    public void empty();
    public void failure(String msg);
}
