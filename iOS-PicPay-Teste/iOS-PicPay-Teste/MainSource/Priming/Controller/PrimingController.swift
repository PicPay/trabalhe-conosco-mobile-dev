//
//  PrimingController.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class PrimingController: UIViewController {
    
    //Replace the color of next viewcontroller and change he's color to green
    override func viewWillDisappear(_ animated: Bool) {
        navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .plain, target: nil, action: nil)
        navigationItem.backBarButtonItem?.tintColor = .lightGreen
    }
    
    
    let priming: PrimingView = {
        let view = PrimingView()
        view.assignButton.addTarget(self, action: #selector(newCredCard), for: .touchUpInside)
        return view
    }()
    
    
    var contato: Contato!
    var delegate: CheckViewControllerProtocol!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        ConfigurePrimingVC()
    }
    
    
    fileprivate func ConfigurePrimingVC() {
        self.view.backgroundColor = .strongBlack
        self.view.addSubview(self.priming)
        self.priming.fillSuperviewLayoutGuide()
        
        self.navigationController?.navigationBar.prefersLargeTitles = true
    }
    
    
    @objc fileprivate func newCredCard() {
        let viewc = NewCredCardFormVC()
        viewc.contato = self.contato
        viewc.delegate = self.delegate
        self.navigationController?.pushViewController(viewc, animated: true)
    }
}
