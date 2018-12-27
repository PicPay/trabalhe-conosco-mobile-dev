//
//  NavPicPay.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 03/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class NavPicPay: UINavigationController {

    override func viewDidLoad() {
        super.viewDidLoad()

        let recentMessagesNavController = UINavigationController(rootViewController: ContactsViewController())
        recentMessagesNavController.tabBarItem.title = "Contatos"
        self.navigationController?.present(recentMessagesNavController, animated: true, completion: nil)
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
