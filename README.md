![PicPay](https://user-images.githubusercontent.com/1765696/26998603-711fcf30-4d5c-11e7-9281-0d9eb20337ad.png)

# Teste Mobile

O teste pode ser feito para iOS ou Android e é uma simulação de envio de dinheiro para uma outra pessoa via cartão de crédito.

BY: VICTOR MORAES

EMAIL: VICCTORMORAES@GMAIL.COM

# Entendendo o Projeto

Esse projeto utiliza o conceito MVP (Model View Presenter) com **UseCases**, ou seja, temos a camada da **View** que se comunica com o **Presenter** no qual se comunica com os **UseCases** e os mesmo se comunicam com os Serviços(**core**).

![Scheme](images/print1.png)

**APP**

* Tela - **View** responsável por mostrar informações ao usuário, como **Activity, Fragment, Dialog e etc**. 

![Scheme](images/print2.png)

* Contract - **Interface** responsável por garantir o **contrato entre o Presenter e a View**.

![Scheme](images/print3.png)

* Presenter - responsável por fazer a comunicação entre a **View** e os **UseCase**, **(NO PRESENTER NÃO VAI REGRA DE NEGÓCIO, POIS SE NÃO O USECASE FICA SEM EMPREGO)**

![Scheme](images/print4.png)

* UseCase - responsável por fazer a comunicação entre **Presenter** e o Serviço(**core**), sendo aqui onde fica as regras de negócios e conversão dos objetos entre **App e Core** utilizando os **Mapper**, **(NADA DE FAZER CONVERSÃO FORA DOS MAPPERS, POIS ELES PRECISAM DESSE EMPREGO)**

![Scheme](images/print5.png)

* Model - responsável por representar os dados de um Objeto para as **View**

![Scheme](images/print6.png)

* Mapper - responsável por converter o **ModelResponse do Core** em **Model do App**

![Scheme](images/print7.png)

**CORE**

* ModelRequest - Model que representa o conjunto de dados para uma requisição

![Scheme](images/print8.png)

* ModelResponse - Model que representa a resposta de uma requisição

![Scheme](images/print9.png)

* API - Classe que controla as chamadas 

![Scheme](images/print10.png)
