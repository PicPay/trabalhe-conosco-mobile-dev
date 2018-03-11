//
//  ViewController.swift
//  PicPay
//
//  Created by Vinicius Alves on 08/03/2018.
//  Copyright © 2018 TIPiniquim. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var txtVoce: UITextField!
    @IBOutlet weak var txtDestinatario: UITextField!
    @IBOutlet weak var txtDinheiro: UITextField!
    
    @IBOutlet weak var suaFoto: UIImageView!
    
    var cliente:Cliente = Cliente()
    var cartao:Cartao = Cartao()
    var destinatario: String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        txtVoce.text = cliente.name
        suaFoto.image = UIImage(named: cliente.img)
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func btnUITableView(_ sender: Any) {
        performSegue(withIdentifier: "segueTableView", sender: self)
    }
    
    @IBAction func btnPOST(_ sender: Any) {
        if cartao == nil {
            cartao = Cartao()
        }
        
        let dinheiro:String = txtDinheiro.text!
        
        if dinheiro == "" || dinheiro.isEmpty {
            let alertController = UIAlertController(title: "Informativo", message:
                "O dinheiro não pode ser vazio.", preferredStyle: UIAlertControllerStyle.alert)
            alertController.addAction(UIAlertAction(title: "Fechar", style: UIAlertActionStyle.default,handler: nil))
            
            self.present(alertController, animated: true, completion: nil)
        } else {
        
            cartao.card_number = "1111111111111111"
            cartao.cvv = 789
            cartao.expiry_date = "01/18"
            cartao.value = (dinheiro as NSString).doubleValue
            cartao.destination_user_id = cliente.id
            
            print("DINHEIRO: ", cartao.value)
            print("DESTINATÁRIO: ", cartao.destination_user_id)
            
            REST.concluirTransacao(cartao: cartao, onComplete: { (success) in
                print("CONCLUINDO TRANSAÇÃO")
            })
            
            let alertController = UIAlertController(title: "Informativo", message:
                "Transação completa!", preferredStyle: UIAlertControllerStyle.alert)
            alertController.addAction(UIAlertAction(title: "Fechar", style: UIAlertActionStyle.default,handler: nil))
            
            self.present(alertController, animated: true, completion: nil)
            
            txtDinheiro.text = ""
            txtVoce.text = ""
        }
    }
}

