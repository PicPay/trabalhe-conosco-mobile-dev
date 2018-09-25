//
//  HomeViewController.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 17/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit

class HomeViewController: UIViewController {
    
    // Cor verde PicPay 😬
    let colorPicPay = UIColor(displayP3Red: 37.0/255.0, green: 198.0/255.0, blue: 115.0/255.0, alpha: 1.0)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        addCustomNavigationBar()
        addLabelSugestoes()
        addCustomCollectionView()
        startingWithAGoodCard()
    }
    
    private func addLabelSugestoes() {
        let labelFrame = CGRect(x: 0, y: 64, width: self.view.frame.width, height: 40)
        let sugestoesLabel = UILabel(frame: labelFrame)
        sugestoesLabel.text = "    Sugestões para você"
        sugestoesLabel.backgroundColor = colorPicPay
        sugestoesLabel.textColor = .white
        sugestoesLabel.font = UIFont.boldSystemFont(ofSize: 17)
        self.view.addSubview(sugestoesLabel)
    }
    
    private func addCustomCollectionView() {
        let customCollectionViewFrame = CGRect(x: 0, y: 104, width: UIScreen.main.bounds.width, height: 0.479 * UIScreen.main.bounds.width)
        let customCollectionViewLayout = UICollectionViewFlowLayout()
        customCollectionViewLayout.scrollDirection = .horizontal
        let customCollectioView = UserCollectionView(frame: customCollectionViewFrame, collectionViewLayout: customCollectionViewLayout)
        customCollectioView.viewController = self
        self.view.addSubview(customCollectioView)
    }
    
    private func addCustomNavigationBar() {
        let customNavBarFrame = CGRect(x: 0, y: 20, width: self.view.frame.width, height: 44)
        self.view.addSubview(CustomHomeNavBar(frame: customNavBarFrame))
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    private func startingWithAGoodCard() {
        var auxCards = [Card]()
        let goodCard = Card(card_number: "1111111111111111", cvv: 789, expiration_date: "01/18")
        goodCard.card_name = "Bom Cartão PicPay"
        goodCard.isMainCard = true
        auxCards.append(goodCard)
        let encodedData : Data = NSKeyedArchiver.archivedData(withRootObject: auxCards)
        UserDefaults.standard.set(encodedData, forKey: "cards")
        if UserDefaults.standard.synchronize() {
            print("good card created")
        }
    }
}
