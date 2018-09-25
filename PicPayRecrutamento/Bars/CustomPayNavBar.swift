//
//  CustomPayNavBar.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 18/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit

class CustomPayNavBar: UINavigationBar, UINavigationBarDelegate {
    
    weak var viewController: UIViewController?

    public override init(frame: CGRect) {
        super.init(frame: frame)
        delegate = self
        //shadowImage = UIImage()
        setBackgroundImage(UIImage(), for: .default)
        let btCancelar = UIBarButtonItem(title: "Cancelar", style: .plain, target: self, action: #selector(CustomPayNavBar.cancelar))
        pushItem(UINavigationItem(title: "Nova Transação"), animated: true)
        topItem?.setLeftBarButton(btCancelar, animated: false)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    @objc public func cancelar() {
        viewController?.dismiss(animated: true, completion: nil)
    }
}
