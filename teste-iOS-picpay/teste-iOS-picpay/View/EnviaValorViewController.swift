//
//  EnviaValorViewController.swift
//  teste-iOS-picpay
//
//  Created by Bruno Lopes de Mello on 18/11/18.
//  Copyright © 2018 Bruno Lopes de Mello. All rights reserved.
//

import UIKit

protocol EnviarDinheiroDelegate {
    func finalizar()
}

class EnviaValorViewController: UIViewController {

    @IBOutlet weak var valorTextField: UITextField!
    @IBOutlet weak var enviarButton: UIButton!
    
    var delegate: EnviarDinheiroDelegate!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    @IBAction func enviar(_ sender: Any) {
        dismiss(animated: true) {
            self.delegate.finalizar()
        }
    }
    
    @IBAction func cancelar(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
}

extension EnviaValorViewController: UITextFieldDelegate {
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        return true
    }
}
