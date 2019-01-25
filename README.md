![PicPay](https://user-images.githubusercontent.com/1765696/26998603-711fcf30-4d5c-11e7-9281-0d9eb20337ad.png)

# Teste Mobile

O teste consiste em se desenvolver um app nativo iOS ou Android, dessa forma, deverá ser implementado em Swift, Kotlin, Objective-C ou Java. É uma simulação de envio de dinheiro para uma outra pessoa via cartão de crédito.

O usuário deverá escolher um contato de uma lista, informar o valor a ser enviado e finalizar o pagamento com o cartão de crédito cadastrado. Se não houver cartão de créditos cadastrado, deverá informá-lo também (número do cartão, data de validade e o CVV) antes de finalizar o pagamento.

Os cartões devem ser persistidos no aplicativo para serem usados em pagamentos futuros.

Devem ser usadas boas práticas de programação, assim como padrões de projeto e Arquitetura.

O layout está disponível em:
Android - https://goo.gl/M5RFzY
iOS - https://goo.gl/yi5AG7

-----
###### Lista de usuários

Para listar as pessoas que podem receber pagamentos, faça uma requisição para o json nessa url: http://careers.picpay.com/tests/mobdev/users

-----

###### Pagamento

Realizar um `POST` para http://careers.picpay.com/tests/mobdev/paymentRequest com os seguintes atributos:
+ ID do usuário que irá receber o pagamento
+ Número do cartão
+ Vencimento do cartão
+ CVV
+ Valor total

``` json
{  
   "card_number":"1111111111111111",
   "cvv":789,
   "value":79.9,
   "expiry_date":"01/18",
   "destination_user_id":1002
}
```

## Para fins de teste, o número de cartão 1111111111111111 aprova a transação, qualquer outro recusa. 
