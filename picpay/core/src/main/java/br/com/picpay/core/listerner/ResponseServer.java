package br.com.picpay.core.listerner;

import br.com.picpay.core.modelResponse.ErrorResponse;

public interface ResponseServer<MODEL> {

    void success(MODEL response);

    void error(ErrorResponse errorResponse);
}
