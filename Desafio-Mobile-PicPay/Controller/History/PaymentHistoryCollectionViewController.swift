//
//  PaymentHistoryCollectionViewController.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 27/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

//Tela desenvolvida utilizando View Code para demonstrar conhecimento

import UIKit

class PaymentHistoryCollectionViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout{
    
    let cellID = "cellID"
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var transactions = [Transaction]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.collectionView?.register(PaymentHistoryCollectionViewCell.self, forCellWithReuseIdentifier: cellID)
        self.collectionView?.backgroundColor = UIColor(red: 235/255, green: 235/255, blue: 241/255, alpha: 1)
        self.navigationItem.title = "Histórico"
        getTransactions()
    }
    
    func getTransactions(){
        transactions = appDelegate.coreDataManager.fetchTransactions()
        self.collectionView?.reloadData()
    }
    
    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return transactions.count
    }
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = self.collectionView?.dequeueReusableCell(withReuseIdentifier: cellID, for: indexPath) as! PaymentHistoryCollectionViewCell
        cell.transaction = transactions[indexPath.item]
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {        
        return CGSize(width: self.view.frame.width-24, height: 110)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 12, left: 12, bottom: 12, right: 12)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 12
    }
}
