//
//  TESTEView.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 08/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class TESTEView: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        let swipe = UISwipeGestureRecognizer(target: self, action: #selector(teste))
        swipe.direction = .down
        self.view.addGestureRecognizer(swipe)
    }
    @objc func teste() {
        let viewc = ContactsViewController()
        self.navigationController?.pushViewController(viewc, animated: true)
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
