package br.com.picpay.core.exceptions;

public class URLException extends CoreException {
    public URLException() {
        super("Endpoint não encontrado");
    }
}
