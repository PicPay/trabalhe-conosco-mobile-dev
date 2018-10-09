package br.com.picpay.picpay.presenter;

public class Presenter<V> {

    private V view;

    public void setView(V view) {
        this.view = view;
    }

    public V getView() {
        return view;
    }

}

