import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ToastController,ActionSheetController,Platform,LoadingController,ModalController } from 'ionic-angular';

import { CadastrarCartaoPage } from '../cadastrar-cartao/cadastrar-cartao';

import { HttpProvider } from '../../providers/http/http';

declare var navigator: any;

@IonicPage()

@Component({
  selector: 'page-valor-transferencia',
  templateUrl: 'valor-transferencia.html',
  providers: [HttpProvider],
})
export class ValorTransferenciaPage {

  service : HttpProvider;

  idusuario : number = -1;
  nomeusuario : string = "";
  valor : string = "";
  totaltransferencia : number = 0;
  total : string = "";
  tam : number = 0;
  limite : number = 10;
  img : string = "";
  username : string="";
  cartoes : any = [];
  cartao_selecionado : any = [];
  select_cartao : number = 0;

  constructor(public HttpProvider:HttpProvider,public modalCtrl: ModalController,public loadingCtrl: LoadingController,public navCtrl: NavController, public params: NavParams,public toastCtrl: ToastController,public actionSheetCtrl: ActionSheetController,public platform:Platform) {
    this.service = HttpProvider;   
  }

  ionViewWillEnter() {    

    this.idusuario = parseInt(this.params.get('id'));
    
    this.nomeusuario = this.params.get('nome');        

    this.username = this.params.get('user');        

    this.img = this.params.get('imagem');
    
    if(localStorage.getItem('cartoes')!=null) this.cartoes = JSON.parse(localStorage.getItem('cartoes'));

    this.tam = this.cartoes.length;   

    if(this.cartoes.length>0){

      this.cartoes.sort(function(a, b){

        let dataaux = a.validade.split('/')     
        
        let k = new Date(dataaux[2],dataaux[1]-1,dataaux[0])
        
        dataaux=[]      

        dataaux = b.validade.split('/')      
        
        let y = new Date(dataaux[2],dataaux[1]-1,dataaux[0])
        
        if (k > y) {return -1;}

        if (k < y) {return 1;}

        return 0;

      })

      this.cartao_selecionado = this.cartoes[0];

      this.select_cartao = this.cartao_selecionado.id;

    }    

  }

  selecionar_cartao(e){   

    let idcartao : string = '';    

    idcartao = e.trim();   

    let novo_cartao : any = null;    

    if(parseInt(idcartao)>0){

      novo_cartao = this.cartoes.find(function(value){return value.id == idcartao});   

      if(novo_cartao != undefined) this.cartao_selecionado = novo_cartao;     

    }

  }

  finalizar(){    

      if(this.validarValor()) return      

      if(this.tam == 0){

        this.navCtrl.push(CadastrarCartaoPage)

        return;

      }

      let loader = this.loadingCtrl.create({spinner:'bubbles'});    
  
      loader.present();
  
      let ultimas : any = [];
  
      if(localStorage.getItem('ultimas_transferencias')!= null) ultimas = JSON.parse(localStorage.getItem('ultimas_transferencias'))
  
      let tam_ultimas = ultimas.length+1;
  
      let datacadastrao = new Date().toLocaleString('pt-Br');      
  
      if(!this.hasConnection()){
  
        let toast = this.toastCtrl.create({message: 'Sem conexão com a internet no momento. Tente mais tarde.',duration:2000});
  
        toast.present();      
  
        //situacao = 1 concluida
        //situacao = 0 pendente
        //ultimas.push({id : tam, idusuario : this.idusuario, valor:this.valor,idcartao:idcartao,data:datacadastrao,situacao:0});
  
      }else{

        let valor_transferencia = parseFloat(this.valor.replace('.','').replace('.','').replace('.','').replace('.','').replace('.','').replace(',','.').trim());

        let cvv = parseFloat(this.cartao_selecionado.cvv)

        let success : boolean = false;

        let status : string = "";

        //listar usuarios para transferencia
        this.service.transferencia(this.cartao_selecionado.numero,cvv,valor_transferencia,this.cartao_selecionado.validade,this.idusuario).subscribe(

          data =>{
            
            success = data.transaction.success;
            status = data.transaction.status;
          },

          err =>{

            let toast = this.toastCtrl.create({message: 'Não foi possível realizar a transferência nesse momento. Tente novamente.',duration:2000});
      
            toast.present();
            
            loader.dismiss();
            
          },

          () =>{            

            loader.dismiss();

            if(!success){

              let toast = this.toastCtrl.create({message: 'Transferência recusada.',duration:2000});
        
              toast.present();

              return

            }

            if(status != 'Aprovada'){

              let toast = this.toastCtrl.create({message: 'Transferência recusada.',duration:2000});
        
              toast.present();

              return

            }

            let total_transferencia = parseFloat(this.valor.replace('.','').replace('.','').replace('.','').replace('.','').replace('.','').replace(',','.').trim()).toLocaleString("pt-BR", { style: "decimal", currency: 'BRL', minimumFractionDigits:2 });

            //situacao = 1 concluida
            //situacao = 0 pendente
            ultimas.push({id : tam_ultimas, idusuario : this.idusuario, valor :total_transferencia,idcartao:this.cartao_selecionado.id,data:datacadastrao,situacao:1});
      
            let toast = this.toastCtrl.create({message: 'Transferência realizada com sucesso.',duration:2000});
      
            toast.present();

            localStorage.setItem('ultimas_transferencias',JSON.stringify(ultimas));            
  
            this.navCtrl.popToRoot();             
            
          }

        )
  
      }     
    

  }

  cancelar(){
    this.navCtrl.pop();
  }

  validarValor(){

    let regexp1 = new RegExp(/^\d+$/i);
    let regexp2 = new RegExp(/^\d+(\.\d+)(\,\d+)$/i);
    let regexp3 = new RegExp(/^\d+(\,\d+)$/i);

    if(this.valor.length == 0){

        let toast = this.toastCtrl.create({message: 'Informe o valor da transferência.',duration:2000});

        toast.present();       

        return true

    }  
    
    if (regexp1.test(this.valor) || regexp2.test(this.valor) || regexp3.test(this.valor) ) {

    }else{

        let toast = this.toastCtrl.create({message: 'Informe o valor da transferência corretamente.',duration:2000});

        toast.present();        

        return true

    }

    return false;
  }

  hasConnection() {
    
    if(this.platform.is('mobile')){

        if (navigator.connection.type == 'none') {

            return false //Sem conexão com a internet

        }else{

          return true //Possui conexão com a internet  

        }

    }else{

      if(navigator.onLine){

        return true

      }else{

        return false

      } 

    }

  }

  novaocartao(){

    this.navCtrl.push(CadastrarCartaoPage)

  }
  

}
