//
//  CvvView.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/18/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//

import UIKit

protocol CvvViewDelegate: class {
    func didTapConfirm(cvvNumber: Int)
}

class CvvView: UIView {
    @IBOutlet private weak var cvvImage: UIImageView! {
        didSet {
            self.cvvImage.image = ImageConstants.cvvImage
            self.cvvImage.contentMode = .scaleAspectFit
        }
    }
    @IBOutlet private weak var cvvTextField: UITextField! {
        didSet {
            self.cvvTextField.delegate = self
            self.cvvTextField.keyboardType = .numberPad
        }
    }
    @IBOutlet private weak var confirmButton: UIButton! {
        didSet {
            self.confirmButton.layer.cornerRadius = 10
            self.confirmButton.layer.borderWidth = 1
            self.confirmButton.clipsToBounds = true
            self.confirmButton.setTitleColor(.black, for: .normal)
            self.confirmButton.layer.borderColor = UIColor.black.cgColor
        }
    }
    
    weak var delegate: CvvViewDelegate?
    
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUpView()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setUpView()
    }
    
    private func setUpView() {
        let view = viewFromNibForClass()
        view.frame = bounds
        view.layer.cornerRadius = 10
        view.clipsToBounds = true
        addSubview(view)
    }
    
    private func viewFromNibForClass() -> UIView {
        let bundle = Bundle(for: type(of: self))
        let nib = UINib(nibName: String(describing: type(of: self)), bundle: bundle)
        let view = nib.instantiate(withOwner: self, options: nil).first as! UIView
        
        return view
    }

    @IBAction func didTapConfirmButton(_ sender: Any) {
        guard let cvv = self.cvvTextField.text, cvv.count == 3 else {
            return
        }
        if let cvvNumber = self.getCvvNumber() {
            self.delegate?.didTapConfirm(cvvNumber: cvvNumber)
        }
    }
    
    private func getCvvNumber() -> Int? {
        guard let cvv = self.cvvTextField.text, let cvvNumber = Int(cvv) else {
            return nil
        }
        
        return cvvNumber
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesBegan(touches, with: event)
        self.endEditing(true)
    }
}

extension CvvView: UITextFieldDelegate {
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        guard let text = self.cvvTextField.text else {
            return true
        }
        return text.count < 3 || string == ""
    }
}
