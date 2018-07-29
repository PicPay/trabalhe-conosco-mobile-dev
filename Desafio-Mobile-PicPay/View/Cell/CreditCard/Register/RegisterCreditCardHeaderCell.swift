//
//  RegisterCreditCardHeaderCell.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

class RegisterCreditCardHeaderCell: UIView {

    weak var CardViewController: CardViewController?
    
    @IBOutlet weak var cardContainerView: UIView!
    @IBOutlet weak var cardNumberLabel: UILabel!
    @IBOutlet weak var cardNameLabel: UILabel!
    @IBOutlet weak var cardDateLabel: UILabel!
    
    @IBAction func didPressBackButton(_ sender: Any) {
        CardViewController?.navigationController?.popViewController(animated:  true)
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        Timer.scheduledTimer(withTimeInterval: 0.8, repeats: false) { (_) in
            self.showAnimation()
        }
        
    }
    
    func showAnimation(){
        let animation = CABasicAnimation(keyPath: "position")
        animation.duration = 0.07
        animation.repeatCount = 4
        animation.autoreverses = true
        animation.fromValue = NSValue(cgPoint: CGPoint(x: cardContainerView.center.x - 10, y: cardContainerView.center.y))
        animation.toValue = NSValue(cgPoint: CGPoint(x: cardContainerView.center.x + 10, y: cardContainerView.center.y))
        
        cardContainerView.layer.add(animation, forKey: "position")
    }
}
