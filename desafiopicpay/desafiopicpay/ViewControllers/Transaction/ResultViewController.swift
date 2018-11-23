//
//  ResultViewController.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 23/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import UIKit

class ResultViewController: UIViewController {
    @IBOutlet weak var descriptionLabel: UILabel!
    @IBOutlet weak var returnButton: UIButton!
    @IBOutlet weak var destinationPicture: UIImageView!
    
    var transaction: TransactionInfo?
    
    // MARK: - View controller life cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        let number = NSNumber(value: transaction?.value ?? 0)
        let formattedValue = FormatterHelper.formatCurrency(value: number)
        
        let message = "O envio do valor de \(formattedValue) para \(transaction?.destinationUser?.name ?? "") foi \(transaction?.status ?? "")."
        descriptionLabel.text = message
        
        configurePicture()
        
        let radius = Float(returnButton.bounds.height) / 2
        corner(in: returnButton, radius: radius)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.isNavigationBarHidden = true
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.isNavigationBarHidden = false
    }

    // MARK: - Privates
    
    private func configurePicture() {
        let url = URL(string: transaction?.destinationUser?.img ?? "")
        destinationPicture.kf.setImage(with: url)

        let radius = Float(destinationPicture.bounds.height) / 2
        corner(in: destinationPicture, radius: radius)
        destinationPicture.layer.masksToBounds = true
    }
    
    // MARK: - Actions
    
    @IBAction func dismiss(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
}
