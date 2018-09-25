//
//  CardViewController.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 20/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit

class CardViewController: UIViewController, AddCardViewControllerDelegate {
    
    var cards = [Card]()
    
    // designables
    @IBOutlet weak var cardTableView: UITableView!
    @IBOutlet weak var navBar: UINavigationBar!

    override func viewDidLoad() {
        super.viewDidLoad()
        cardTableView.delegate = self
        cardTableView.dataSource = self
        configNavBar()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        // checar cartões
        self.reloadTableViewData()
    }
    
    @IBAction func openModally(_ sender: Any) {
        print("me chamou")
        self.definesPresentationContext = true
        self.providesPresentationContextTransitionStyle = true
        self.overlayBlurredBackgroundView()
        let addCardViewController = storyboard?.instantiateViewController(withIdentifier: "addCard") as! AddCardViewController
        addCardViewController.delegate = self
        addCardViewController.modalPresentationStyle = .overCurrentContext
        self.present(addCardViewController, animated: true, completion: nil)
        
    }
    
    @IBAction func deleteAll(_ sender: Any) {
        print("gente??")
        if let decodedCards = UserDefaults.standard.value(forKey: "cards") as? Data {
            let alert = UIAlertController(title: "Deletar todos os cartões", message: "Você tem certeza que deseja cancelar todos os cartões da lista?", preferredStyle: .actionSheet)
            alert.addAction(UIAlertAction(title: "Sim", style: .destructive, handler: { (action) in
                print("deletando")
                UserDefaults.standard.set(nil, forKey: "cards")
                if UserDefaults.standard.synchronize() {
                    self.reloadTableViewData()
                }
                
            }))
            alert.addAction(UIAlertAction(title: "Cancelar", style: .cancel, handler: nil))
            self.present(alert, animated: true, completion: nil)
        } else {
            let alert = UIAlertController(title: "Não há cartões", message: "Não há cartões para serem deletados", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: nil))
            self.present(alert, animated: true, completion: nil)
        }
        
    }
    
    private func configNavBar() {
        self.navBar.setBackgroundImage(UIImage(), for: .default)
    }
}

extension CardViewController {
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let identifier = segue.identifier {
            if identifier == "ShowModalView" {
                if let viewController = segue.destination as? AddCardViewController {
                    viewController.delegate = self
                    viewController.modalPresentationStyle = .overFullScreen
                }
            }
        }
    }
    
    // design blurred background
    func overlayBlurredBackgroundView() {
        print("me chamou")
        let blurEffect = UIBlurEffect(style: UIBlurEffectStyle.dark)
        let blurEffectView = UIVisualEffectView(effect: blurEffect)
        blurEffectView.frame = view.bounds
        blurEffectView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        self.view.addSubview(blurEffectView)
        
    }
    
    func removeBlurredBackgroundView() {
        print("e eu?")
        for subview in view.subviews {
            if subview.isKind(of: UIVisualEffectView.self) {
                subview.removeFromSuperview()
            }
        }
        self.reloadTableViewData()
    }
    
    func reloadTableViewData() {
        if let decodedCards = UserDefaults.standard.object(forKey: "cards") as? Data {
            print("there are cards")
            let savedCards = NSKeyedUnarchiver.unarchiveObject(with: decodedCards) as! [Card]
            self.cards = savedCards
            //print(cards.last?.card_number)
        } else {
            self.cards = [Card]()
            print("there are no cards")
        }
        cardTableView.reloadData()
        self.reloadInputViews()
    }
}

extension CardViewController: UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return cards.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 100
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        // mudar cartão principal
        print("tocou em mim")
        let cardToChange = cards[indexPath.row]
        if let decodedCards = UserDefaults.standard.value(forKey: "cards") as? Data {
            let savedCards = NSKeyedUnarchiver.unarchiveObject(with: decodedCards) as! [Card]
            for card in savedCards {
                if cardToChange.card_number == card.card_number {
                    card.isMainCard = true
                } else {
                    card.isMainCard = false
                }
            }
            self.cards = savedCards
            let encodedData: Data = NSKeyedArchiver.archivedData(withRootObject: savedCards)
            UserDefaults.standard.set(encodedData, forKey: "cards")
            if UserDefaults.standard.synchronize() {
                tableView.reloadData()
            }
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cardView = CardViewModel(card: self.cards[indexPath.row])
        print(cardView.card_number + " " + cardView.name!)
        let cell = tableView.dequeueReusableCell(withIdentifier: "cardcellid", for: indexPath) as! CardTableViewCell
        cell.selectionStyle = .none
        cell.finalCardLabel.text = "Cartão com final \(cardView.card_number.suffix(4))"
        if (cardView.name?.isEmpty)! {
            cell.nameLabel.text = "Cartão"
        } else {
            cell.nameLabel.text = cardView.name
        }
        if cardView.isMainCard {
            cell.mainCardLabel.text = "Principal"
        } else {
            cell.mainCardLabel.text = ""
        }
        
        return cell
    }
}
