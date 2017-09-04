package br.com.dalcim.picpay.creditcard;

import br.com.dalcim.picpay.data.CreditCard;
import br.com.dalcim.picpay.data.local.RepositoryLocal;

/**
 * @author Wiliam
 * @since 03/09/2017
 */
public class CreditCardPresenter implements CreditCardContract.Presenter {

    CreditCardContract.View view;
    RepositoryLocal repositoryLocal;

    public CreditCardPresenter(CreditCardContract.View view, RepositoryLocal repositoryLocal) {
        this.view = view;
        this.repositoryLocal = repositoryLocal;
    }

    @Override
    public void save(String numberCreditCard, String expiryDate, String cvv) {
        if(numberCreditCard.length() != 16){
            view.showError("Numero de cartão inválido!");
        }else if(expiryDate.length() != 7){
            view.showError("Validade inválida!");
        }else if(cvv.length() < 3){
            view.showError("CVV inválido!");
        }else{
            CreditCard creditCard = new CreditCard(numberCreditCard, expiryDate, Integer.valueOf(cvv));

            repositoryLocal.saveCreditCard(creditCard, new RepositoryLocal.CreditCardSaveCallBack() {
                @Override
                public void onSucess(CreditCard creditCard) {
                    view.onSucessSave(creditCard);
                }

                @Override
                public void onFailure() {
                    view.showError("Erro ao salvar Cartão, tente novamente!");
                }
            });
        }
    }
}
