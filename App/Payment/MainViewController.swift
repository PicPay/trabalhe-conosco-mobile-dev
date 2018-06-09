//
//  MainViewController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 08/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

class MainViewController: UITableViewController {

    var persons: [Person]?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        let urlString = "http://careers.picpay.com/tests/mobdev/users"
        
        guard let url = URL(string: urlString) else { return }
        
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            
            if error != nil {
                print(error!.localizedDescription)
            }
            
            guard let data = data else { return }
            
            do {
                let persons = try JSONDecoder().decode([Person].self, from: data)
                
                DispatchQueue.main.async {
                    self.persons = persons
                }
            } catch let jsonError {
                print(jsonError)
            }
            
        }.resume()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        clearsSelectionOnViewWillAppear = false
    }
}
