//
//  UserTransactionView.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 19/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit
import PlaceholderTextView

class UserTransactionView: UIView {
    
    public let imageView: UIImageView
    public let usernameLabel: UILabel
    public let nameLabel: UILabel
    public let payTextView: PlaceholderTextView
    public let buttonPay: UIButton
    weak var viewController: UIViewController?
    var currentString = ""
    
    public override init(frame: CGRect) {
        let thisHeight = frame.height - 40
        let childFrame = CGRect(x: 10, y: 0, width: thisHeight, height: thisHeight)
        imageView = UIImageView(frame: childFrame)
        imageView.contentMode = .scaleAspectFit
        imageView.layer.cornerRadius = imageView.frame.height / 2
        imageView.clipsToBounds = true
        
        let labelFrame = CGRect(x: 15 + thisHeight, y: 0, width: frame.width/3, height: thisHeight/5)
        usernameLabel = UILabel(frame: labelFrame)
        usernameLabel.font = UIFont.systemFont(ofSize: 11)
        usernameLabel.textColor = .darkGray
        
        let nameLabelFrame = CGRect(x: 17 + thisHeight, y: thisHeight/5, width: frame.width/3, height: thisHeight/5)
        nameLabel = UILabel(frame: nameLabelFrame)
        nameLabel.font = .systemFont(ofSize: 14)
        nameLabel.textColor = .lightGray
        
        let pTFFrame = CGRect(x: 15 + thisHeight, y: 5 + thisHeight * 0.4 , width: (frame.width - thisHeight), height: thisHeight * 0.6)
        payTextView = PlaceholderTextView()
        payTextView.keyboardType = .numberPad
        payTextView.font = UIFont.systemFont(ofSize: 24)
        payTextView.textColor = .lightGray
        payTextView.frame = pTFFrame
        payTextView.placeholder = "R$0,00"
        payTextView.placeholderColor = .lightGray
        
        let buttonPayFrame = CGRect(x: UIScreen.main.bounds.width - 90, y: thisHeight, width: 80, height: 40)
        buttonPay = UIButton(frame: buttonPayFrame)
        buttonPay.setImage(UIImage(named: "bt_pay"), for: .normal)
        
        super.init(frame: frame)
        payTextView.delegate = self
        addSubview(imageView)
        addSubview(usernameLabel)
        addSubview(nameLabel)
        addSubview(payTextView)
        addSubview(buttonPay)
        payTextView.becomeFirstResponder()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

}

extension UserTransactionView: UITextViewDelegate {
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        switch text {
        case "0","1","2","3","4","5","6","7","8","9":
            currentString += text
            print(currentString)
            formatCurrency(string: currentString)
        default:
            var array = Array(text)
            var currentStringArray = Array(currentString)
            if array.count == 0 && currentStringArray.count != 0 {
                currentStringArray.removeLast()
                currentString = ""
                for character in currentStringArray {
                    currentString += String(character)
                }
                formatCurrency(string: currentString)
            }
        }
        return false
    }
    
    func formatCurrency(string: String) {
        print("format \(string)")
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.locale = Locale(identifier: "pt_BR")
        var numberFromField = (NSString(string: currentString).doubleValue)/100
        payTextView.text = formatter.string(from: NSNumber(value: numberFromField))
        print(payTextView.text )
    }
}
