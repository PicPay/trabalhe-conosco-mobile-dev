//
//  CardSelectionViewController.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/17/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//


import UIKit

enum CardSelectionFieldError {
    case noCardSelection
    case invalidValue
}

class CardSelectionViewController: UIViewController {
    @IBOutlet weak var valueTextField: UITextField! {
        didSet {
            self.valueTextField.addTarget(self, action: #selector(textChanged(_:)), for: .editingChanged
            )
            self.valueTextField.keyboardType = .numberPad
        }
    }
    @IBOutlet weak var cardTableView: UITableView! {
        didSet {
            self.cardTableView.delegate = self
            self.cardTableView.dataSource = self
            self.cardTableView.register(CardTableViewCell.self, forCellReuseIdentifier: String(describing: CardTableViewCell.self))
            self.cardTableView.register(UINib(nibName: String(describing: CardTableViewCell.self), bundle: nil), forCellReuseIdentifier: String(describing: CardTableViewCell.self))
        }
    }
    @IBOutlet weak var payButton: UIButton!
    
    @IBOutlet weak var loadingView: UIView! {
        didSet {
            self.loadingView.isHidden = true
        }
    }
    
    
    var cards: [CardCoreData] = [] {
        didSet {
            self.cardTableView.reloadData()
        }
    }
    var user: User?
    var selectedCard: CardCoreData?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let name = self.user?.name {
            self.navigationItem.title = name
        } else {
            self.navigationItem.title = ""
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if let coreDataCards = CoreDataService.sharedInstance.fetch(entityName: CardCoreDataKeys.entityName) as? [CardCoreData] {
            self.cards = coreDataCards
        }
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesBegan(touches, with: event)
        self.view.endEditing(true)
    }
    
    
    //MARK: - IBACtions
    
    @IBAction func didTapAddCard(_ sender: Any) {
        let storyboard = UIStoryboard(name: StoryboardName.payment, bundle: nil)
        if let vc = storyboard.instantiateViewController(withIdentifier: ViewControllerName.addCard) as? AddCardViewController {
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    @IBAction func didTapPay(_ sender: Any) {
        let (success, fieldError) = self.valideteField()
        if !success, let fieldError = fieldError {
            self.showError(fieldError: fieldError)
        } else {
            self.goToCvvNumber()
        }
    }
    
    
    //MARK: - Functions
    private func valideteField() -> (Bool, CardSelectionFieldError?) {
        guard let value = self.valueTextField.text, !value.isEmpty else {
            return (false, .invalidValue)
        }
        
        guard let _ = self.selectedCard else {
            return (false, .noCardSelection)
        }
        
        return (true, nil)
    }
    
    private func showError(fieldError: CardSelectionFieldError) {
        let alertController: UIAlertController
        switch fieldError {
        case .noCardSelection:
            alertController = UIAlertController(title: "Error", message: "No card selected", preferredStyle: .alert)
        case .invalidValue:
            alertController = UIAlertController(title: "Error", message: "Invalid value", preferredStyle: .alert)
        }
        
        alertController.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
        self.present(alertController, animated: true, completion: nil)
    }
    
    private func goToCvvNumber() {
        guard let _ = self.valueTextField.text, let _ = self.selectedCard, let _ = self.user else {
            return
        }
        
        let sb = UIStoryboard(name: StoryboardName.payment, bundle: nil)
        if let vc = sb.instantiateViewController(withIdentifier: ViewControllerName.cvv) as? CvvViewController {
            vc.modalPresentationStyle = .overCurrentContext
            vc.modalTransitionStyle = .crossDissolve
            vc.delegate = self
            self.present(vc, animated: true, completion: nil)
        }
    }
    
    func getValue() -> Double? {
        guard let value = self.valueTextField.text, !value.isEmpty else {
            return nil
        }
        
        
        guard let doubleValue = value.getDouble()  else {
            return nil
        }
        
        return doubleValue
    }
    
    func paymentSuccess() {
        let alert: UIAlertController
        if let name = self.user?.name {
            alert = UIAlertController(title: "Success", message: String(format: "Your transaction with %@ was successfully", name) , preferredStyle: .alert)
        } else {
            alert = UIAlertController(title: "Success", message: "Your transaction was successfully", preferredStyle: .alert)
        }
        alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action) in
            self.navigationController?.popViewController(animated: true)
        }))
        self.present(alert, animated: true, completion: nil)
    }
    
    func paymentFailure() {
        let alert: UIAlertController
        if let name = self.user?.name {
            alert = UIAlertController(title: "Error", message: String(format: "Your transaction with %@ was rejected", name) , preferredStyle: .alert)
        } else {
            alert = UIAlertController(title: "Error", message: "Your transaction was rejected", preferredStyle: .alert)
        }
        alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
    
    
    @objc func textChanged(_ textField: UITextField) {
        guard let text = textField.text else {
            return
        }
        
        textField.text = text.currencyInputFormatting()
    }
    
    
}

extension CardSelectionViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        var reloadRows: [IndexPath] = []
        reloadRows.append(indexPath)
        if let selectedCard = self.selectedCard, let index = self.cards.index(of: selectedCard) {
            reloadRows.append(IndexPath(row: index, section: 0))
        }
        
        self.selectedCard = self.cards[indexPath.row]
        tableView.reloadRows(at: reloadRows, with: .none)
        self.view.endEditing(true)
    }
}

extension CardSelectionViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.cards.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CardTableViewCell.self), for: indexPath) as? CardTableViewCell else {
            return UITableViewCell()
        }
        
        let card = self.cards[indexPath.row]
        
        if let selectedCard = self.selectedCard, selectedCard == card {
            cell.setUpCell(card: card, isSelected: true)
        } else {
            cell.setUpCell(card: card, isSelected: false)
        }
        
        return cell
        
    }
}

extension CardSelectionViewController: CvvViewControllerDelegate {
    func didTapConfirm(cvvNumber: Int) {
        guard let value = self.getValue(), let selectedCard = self.selectedCard, let user = self.user else {
            return
        }
        
        self.loadingView.isHidden = false
        AlamofireService.payment(userId: user.id, card: selectedCard, cvv: cvvNumber, value: value) { (success) in
            self.loadingView.isHidden = true
            if success {
                self.paymentSuccess()
            } else {
                self.paymentFailure()
            }
        }
    }
}
