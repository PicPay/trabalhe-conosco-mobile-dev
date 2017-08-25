# Solução

Para solução deste problema adotei as ferramentas: Ionic 3, Angular 4, TypeScript, SASS e armazenamento em LocalStorage; utilizei tal conjunto pois é o stack de ferramentas em que possuo experiencia, além de agilizar o desenvolvimento nas duas plataformas (iOS e Android).
O tempo de desenvolvimento desta solução completa foi de 6 horas.

# Possíveis Melhorias

Considerando o uso do Ionic 3/Angular 4:
 * [Plugin de reconhecimento de cartões](https://ionicframework.com/docs/native/card-io/) (facilitando o input do usuário);

 * [Máscara de Valores](https://github.com/cesarrew/ng2-currency-mask) (facilita a utilização do usuário);

 * Tratamento de icones e imagem de carregamento.

Considerando perfomance:
- Para obter a maior perfomance, neste caso, seria ideal o desenvolvimento nativo em Android/iOS, sendo esta uma opção mais elegante.

# Preview Online
* [https://iowaz.github.io/trabalhe-conosco-mobile-dev/www-online/](https://iowaz.github.io/trabalhe-conosco-mobile-dev/www-online/)

# Download APK (Android)
* [https://github.com/iowaz/trabalhe-conosco-mobile-dev/raw/master/apk-android.apk](https://github.com/iowaz/trabalhe-conosco-mobile-dev/raw/master/apk-android.apk)

Observação: é necessário habilitar instalação de fontes desconhecidas em Configurações > Seguranças.

# Imagens
![Imagem de Preview](https://raw.githubusercontent.com/iowaz/trabalhe-conosco-mobile-dev/master/preview.png)



![PicPay](https://user-images.githubusercontent.com/1765696/26998603-711fcf30-4d5c-11e7-9281-0d9eb20337ad.png)

# Teste Mobile

O teste pode ser feito para iOS ou Android e é uma simulação de envio de dinheiro para uma outra pessoa via cartão de crédito.

Você deve fazer um ***Fork*** deste repositório e soliciar um ***Pull Request***, **com seu nome na descrição**, para nossa avaliação.

O seu usuário deverá escolher uma pessoa em uma lista, informar o valor a ser enviado e finalizar o pagamento com o cartão de crédito cadastrado. Se ele não possuir cartão de crédito cadastrado, deverá informar o dados do cartão (número do cartão, data de validade e CVV, além do id do usuário de destino) antes de finalizar o pagamento.

Os cartões devem ser persistidos no aplicativo para serem usados em pagamentos futuros.

-----
###### Lista de usuários

Para listar as pessoas que podem receber pagamentos, faça uma requisição para o json nessa url: http://careers.picpay.com/tests/mobdev/users

-----

###### Pagamento

Realizar um `POST` para http://careers.picpay.com/tests/mobdev/transaction com os seguintes atributos:
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
