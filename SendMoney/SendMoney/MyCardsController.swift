//
//  MyCardsController.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 29/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import UIKit

class MyCardsController: UITableViewController {
    
    var myCards = [CreditCard]()

    @IBOutlet var noContentView: UIView!
    @IBOutlet weak var noContentText: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        navigationItem.title = Localize.MyCards.rawValue.localized
        
        configureBarButton()
        loadMyCards()
        setupScreen()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        loadMyCards()
        tableView.reloadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Navigation bar methods
    func configureBarButton() {
        let rightButton = UIBarButtonItem.init(barButtonSystemItem: .add, target: self, action: #selector(displayCardDetails))
        navigationItem.setRightBarButtonItems([rightButton], animated: true)
    }
    
    func displayCardDetails() {
        performSegue(withIdentifier: "newCardSegue", sender: nil)
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return myCards.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "myCardCell", for: indexPath)

        let cardNumber = myCards[indexPath.row].cardNumber
        cell.textLabel?.text = "\(Localize.CardNumberEncripted.rawValue.localized)  ...\(cardNumber.substring(from: cardNumber.index(cardNumber.endIndex, offsetBy: -4)))"

        return cell
    }
    
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if (editingStyle == UITableViewCellEditingStyle.delete) {
            let repo = CreditCardRepository()
            repo.Delete(item: myCards[indexPath.row])
            
            myCards.remove(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
        }
    }
    
    // MARK: - Load data
    func loadMyCards() {
        let repo = CreditCardRepository()
        let results = repo.All()
        
        myCards = results.filter({$0.cardNumber == $0.cardNumber})
    }
    
    // MARK: - Setup screen
    func setupScreen() {
        noContentText.text = Localize.NoContentCards.rawValue.localized
        noContentView.frame = CGRect(x: 0, y: 0, width: 320.0, height: 640.0)
        let withoutContent = myCards.count == 0
        
        if withoutContent {
            noContentView.isHidden = false
            tableView.backgroundView = noContentView
        } else {
            noContentView.isHidden = true
            tableView.backgroundView = nil
        }
    }

}
