//
//  AddCardViewController.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 20/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit

protocol AddCardViewControllerDelegate: class {
    func removeBlurredBackgroundView()
}

class AddCardViewController: UIViewController, UITextFieldDelegate, UIScrollViewDelegate {
    
    @IBOutlet weak var viewPopup: UIView!
    @IBOutlet weak var scrollView: UIScrollView!
    
    @IBOutlet weak var numeroCartaoTF: UITextField!
    @IBOutlet weak var nomeCartaoTF: UITextField!
    @IBOutlet weak var validadeTF: UITextField!
    @IBOutlet weak var cvvTF: UITextField!
    @IBOutlet weak var adicionarCartaoBt: UIButton!
    
    private var previousTextFieldContent: String?
    private var previousSelection: UITextRange?
    
    weak var delegate: AddCardViewControllerDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configDesignables()
        formatStringTextField()
        dismissKeyboardOnTap()
        scrollView.delegate = self
    }
    
    @IBAction func dismissSelf(_ sender: Any) {
        self.dismiss(animated: true) {
            self.delegate?.removeBlurredBackgroundView()
        }
    }
    
    override func viewDidLayoutSubviews() {
        self.view.backgroundColor = .clear
    }
    
    private func formatStringTextField() {
        validadeTF.delegate = self
        numeroCartaoTF.delegate = self
        cvvTF.delegate = self
        numeroCartaoTF.addTarget(self, action: #selector(AddCardViewController.reformatAsCardNumber), for: .editingChanged)
    }
    
    private func configDesignables() {
        // View principal
        self.viewPopup.layer.cornerRadius = 5.0
        self.viewPopup.layer.masksToBounds = true
        self.viewPopup.dropShadow()
        
        // botao adicionar cartao
        self.adicionarCartaoBt.layer.cornerRadius = 5.0
        self.adicionarCartaoBt.layer.masksToBounds = true
        self.adicionarCartaoBt.addTarget(self, action: #selector(AddCardViewController.saveCard), for: .touchUpInside)
    }
}



extension AddCardViewController {
    // referencia para criacao do cartao
    
    public func createCard(completion: ((Card) -> Void)){
        if self.checkBlankSpaces() == false {
            let card_number = self.numeroCartaoTF.text?.replacingOccurrences(of: " ", with: "")
            let cvv = Int(self.cvvTF!.text!)!
            let expiry_date = self.validadeTF.text
            let nome_cartao = self.nomeCartaoTF.text
            let card = Card(card_number: card_number!, cvv: cvv, expiration_date: expiry_date!)
            if !(self.nomeCartaoTF.text!.isEmpty) {
                card.card_name = self.nomeCartaoTF.text!
            }
            completion(card)
        } else {
            let alert = UIAlertController(title: "Complete todos os campos", message: "Complete todos os campos para salvar seu cartão", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: nil))
            self.present(alert, animated: true, completion: nil)
        }
    }
    
    
    @objc public func saveCard() {
        self.createCard { (card) in
            print("criou cartao")
            if let decodedCards = UserDefaults.standard.object(forKey: "cards") as? Data {
                var savedCards = NSKeyedUnarchiver.unarchiveObject(with: decodedCards) as! [Card]
                print("há cartões salvos")
                var isCardAlreadySaved = false
                for savedCard in savedCards {
                    if savedCard.card_number == card.card_number {
                        isCardAlreadySaved = true
                    }
                }
                if isCardAlreadySaved {
                    print("cartão já incluso -- não salvar")
                    let alert = UIAlertController(title: "Você já salvou esse cartão", message: "Você já salvou esse cartão. Por favor, cadastre outro cartão ou use-o como método de pagamento", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                } else {
                    print("salvando cartão...")
                    savedCards.append(card)
                    let encodedData: Data = NSKeyedArchiver.archivedData(withRootObject: savedCards)
                    UserDefaults.standard.set(encodedData, forKey: "cards")
                    if UserDefaults.standard.synchronize() {
                        let alert = UIAlertController(title: "Cartão Salvo", message: "Cartão salvo com sucesso", preferredStyle: .alert)
                        alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: { (action) in
                            self.dismiss(animated: true, completion: {
                                self.delegate?.removeBlurredBackgroundView()
                            })
                            //self.dismiss(animated: true, completion: nil)
                            
                        }))
                        self.present(alert, animated: true, completion: nil)
                    }
                    
                }
            } else {
                print("salvando cartão...")
                var auxCards = [Card]()
                card.isMainCard = true
                auxCards.append(card)
                let encodedData : Data = NSKeyedArchiver.archivedData(withRootObject: auxCards)
                UserDefaults.standard.set(encodedData, forKey: "cards")
                if UserDefaults.standard.synchronize() {
                    let alert = UIAlertController(title: "Cartão Salvo", message: "Cartão salvo com sucesso", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: { (action) in
                        self.dismiss(animated: true, completion: {
                            self.delegate?.removeBlurredBackgroundView()
                        })
                        //self.dismiss(animated: true, completion: nil)
                    }))
                    self.present(alert, animated: true, completion: nil)
                }
            }
        }
    }
    
    private func checkBlankSpaces() -> Bool {
        if self.numeroCartaoTF.text!.isEmpty || self.cvvTF.text!.isEmpty || self.validadeTF.text!.isEmpty || self.nomeCartaoTF.text!.isEmpty {
            return true
        } else {
            return false
        }
    }
}

extension AddCardViewController: UITextViewDelegate {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        if textField == validadeTF {
            
            // check the chars length dd -->2 at the same time calculate the dd-MM --> 5
            if (validadeTF?.text?.count == 2) {
                if !(string == "") {
                    // append the text
                    validadeTF?.text = (validadeTF?.text)! + "/"
                }
            }
            return !(textField.text!.count > 4 && (string.count ) > range.length)
        } else {
            if textField == numeroCartaoTF {
                previousTextFieldContent = textField.text;
                previousSelection = textField.selectedTextRange;
            } else if textField == cvvTF {
                let maxLength = 3
                let currentString: NSString = textField.text! as NSString
                let newString: NSString =
                    currentString.replacingCharacters(in: range, with: string) as NSString
                return newString.length <= maxLength
            }
        }
        return true
        
        
    }
    
    @objc func reformatAsCardNumber(textField: UITextField) {
        var targetCursorPosition = 0
        if let startPosition = textField.selectedTextRange?.start {
            targetCursorPosition = textField.offset(from: textField.beginningOfDocument, to: startPosition)
        }
        
        var cardNumberWithoutSpaces = ""
        if let text = textField.text {
            cardNumberWithoutSpaces = self.removeNonDigits(string: text, andPreserveCursorPosition: &targetCursorPosition)
        }
        
        if cardNumberWithoutSpaces.count > 19 {
            textField.text = previousTextFieldContent
            textField.selectedTextRange = previousSelection
            return
        }
        
        let cardNumberWithSpaces = self.insertCreditCardSpaces(cardNumberWithoutSpaces, preserveCursorPosition: &targetCursorPosition)
        textField.text = cardNumberWithSpaces
        
        if let targetPosition = textField.position(from: textField.beginningOfDocument, offset: targetCursorPosition) {
            textField.selectedTextRange = textField.textRange(from: targetPosition, to: targetPosition)
        }
    }
    
    func removeNonDigits(string: String, andPreserveCursorPosition cursorPosition: inout Int) -> String {
        var digitsOnlyString = ""
        let originalCursorPosition = cursorPosition
        
        for i in Swift.stride(from: 0, to: string.count, by: 1) {
            let characterToAdd = string[string.index(string.startIndex, offsetBy: i)]
            if characterToAdd >= "0" && characterToAdd <= "9" {
                digitsOnlyString.append(characterToAdd)
            }
            else if i < originalCursorPosition {
                cursorPosition -= 1
            }
        }
        
        return digitsOnlyString
    }
    
    func insertCreditCardSpaces(_ string: String, preserveCursorPosition cursorPosition: inout Int) -> String {
        // Mapping of card prefix to pattern is taken from
        // https://baymard.com/checkout-usability/credit-card-patterns
        
        let is456 = string.hasPrefix("1")
        let is465 = [
            // Amex
            "34", "37",
            
            // Diners Club
            "300", "301", "302", "303", "304", "305", "309", "36", "38", "39"
            ].contains { string.hasPrefix($0) }
        let is4444 = !(is456 || is465)
        
        var stringWithAddedSpaces = ""
        let cursorPositionInSpacelessString = cursorPosition
        
        for i in 0..<string.count {
            let needs465Spacing = (is465 && (i == 4 || i == 10 || i == 15))
            let needs456Spacing = (is456 && (i == 4 || i == 9 || i == 15))
            let needs4444Spacing = (is4444 && i > 0 && (i % 4) == 0)
            
            if needs465Spacing || needs456Spacing || needs4444Spacing {
                stringWithAddedSpaces.append(" ")
                
                if i < cursorPositionInSpacelessString {
                    cursorPosition += 1
                }
            }
            
            let characterToAdd = string[string.index(string.startIndex, offsetBy:i)]
            stringWithAddedSpaces.append(characterToAdd)
        }
        
        return stringWithAddedSpaces
    }
}

extension AddCardViewController {
    // referencia ao teclado
    func dismissKeyboardOnTap() {
        let tap = UITapGestureRecognizer(target: self.viewPopup, action: #selector(UIView.endEditing(_:)))
        tap.cancelsTouchesInView = false
        self.viewPopup.addGestureRecognizer(tap)
    }
    
//    @objc func endEditing() {
//        self.view.endEditing(true)
//    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        self.view.endEditing(true)
    }
}

extension UIView {
    
    func dropShadow(scale: Bool = true) {
        layer.masksToBounds = false
        layer.shadowColor = UIColor.black.cgColor
        layer.shadowOpacity = 0.5
        layer.shadowOffset = CGSize(width: -1.5, height: 1.5)
        layer.shadowRadius = 1
        
        layer.shadowPath = UIBezierPath(rect: bounds).cgPath
        layer.shouldRasterize = true
        layer.rasterizationScale = scale ? UIScreen.main.scale : 1
    }
}
