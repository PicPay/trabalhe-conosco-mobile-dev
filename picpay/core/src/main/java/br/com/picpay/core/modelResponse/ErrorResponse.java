package br.com.picpay.core.modelResponse;

/**
 * Created by victormoraes on 16/03/2018.
 */

public class ErrorResponse {

    private String men;
    private boolean serverError;
    private boolean internetError;

    public String getMen() {
        return men;
    }

    public void setMen(String men) {
        this.men = men;
    }

    public boolean isServerError() {
        return serverError;
    }

    public void setServerError(boolean serverError) {
        this.serverError = serverError;
    }

    public boolean isInternetError() {
        return internetError;
    }

    public void setInternetError(boolean internetError) {
        this.internetError = internetError;
    }
}
