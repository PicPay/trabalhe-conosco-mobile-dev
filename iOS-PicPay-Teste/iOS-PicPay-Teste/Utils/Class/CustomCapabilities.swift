//
//  CustomCapabilities.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class DefaultButton: UIButton {
    override init(frame: CGRect) {
        super.init(frame: frame)
        backgroundColor = .lightGreen
        layer.cornerRadius = 30
        layer.masksToBounds = true
        setTitleColor(UIColor.white, for: .normal)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

class DefaultTextField: UITextField, UITextFieldDelegate {
    let borderView: UIView = {
        let view = UIView()
        view.backgroundColor = .darkGray//UIColor(red: 17/255, green: 199/255, blue: 111/255, alpha: 1.0)
        return view
    }()
    let placeholderview: UILabel = {
        let label = UILabel()
        label.textColor = .darkGray
        label.font = UIFont.boldSystemFont(ofSize: 16)
        label.minimumScaleFactor = 0.4
        label.isHidden = true
        return label
    }()
    var placeholderCustom: String? {
        didSet {
            super.attributedPlaceholder = NSAttributedString(string: placeholderCustom ?? "", attributes: [NSAttributedString.Key.foregroundColor : UIColor.darkGray])
            self.placeholderview.text = placeholderCustom!
        }
    }
    var masks: String?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        textColor = .white
        UITextField.appearance().tintColor = .lightGreen
        layer.backgroundColor = UIColor.clear.cgColor
        layer.masksToBounds = false
        keyboardType = .numberPad
        configureBorder()
        configureCustomPlaceholder()
    }
    
    //MARK:- Animations
    func animationToTop() {
        UIView.animate(withDuration: 0.2) {
            self.placeholderview.isHidden = false
            self.placeholderview.textColor = .lightGreen
            self.borderView.backgroundColor = .lightGreen
            self.placeholder = ""
            self.placeholderview.transform = CGAffineTransform(translationX: 0, y: -25)
        }
    }
    func animationToDown() {
        if self.text == "" {
            UIView.animate(withDuration: 0.2, animations: {
                self.placeholderview.transform = CGAffineTransform(translationX: 0, y: 0)
            }) { (_) in
                self.placeholderCustom = self.placeholderview.text ?? ""
                self.placeholderview.isHidden = true
            }
        } else { self.placeholderview.textColor = .darkGray }
        self.borderView.backgroundColor = .darkGray
    }

    //MARK:- Configure Custom Class
    fileprivate func configureCustomPlaceholder() {
        self.addSubview(self.placeholderview)
        self.placeholderview.anchorXY(centerX: nil, centerY: self.centerYAnchor, top: nil, leading: self.leadingAnchor, bottom: nil, trailing: self.trailingAnchor)
    }
    fileprivate func configureBorder() {
        self.addSubview(self.borderView)
        self.borderView.anchor(top: self.bottomAnchor, leading: self.leadingAnchor, bottom: nil, trailing: self.trailingAnchor, padding: .init(), size: .init(width: 0, height: 1))
    }
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}

class ViewButton: UIView {
    
    let button = DefaultButton()
    let view: UIView = {
        let view = UIView()
        view.backgroundColor = .clear
        return view
    }()
    let stackView: UIStackView = {
        let stack = UIStackView()
        stack.axis = .horizontal
        stack.spacing = 15
        stack.distribution = .fillProportionally
        return stack
    }()
    override init(frame: CGRect) {
        super.init(frame: CGRect(x: 0, y: 0, width: 0, height: 60))
        UserDefaults.standard.setValue(false, forKey: "_UIConstraintBasedLayoutLogUnsatisfiable")
        
        self.addSubview(self.view)
        self.view.addSubview(self.stackView)
        self.stackView.addArrangedSubview(self.button)
        
        self.view.anchor(top: self.topAnchor, leading: self.layoutMarginsGuide.leadingAnchor, bottom: self.bottomAnchor, trailing: self.layoutMarginsGuide.trailingAnchor, padding: .init(top: 0, left: 0, bottom: 15, right: 0))
        
        self.button.anchor(top: nil, leading: nil, bottom: nil, trailing: nil, padding: .init(), size: CGSize(width: 0, height: 60))
        self.stackView.anchorXY(centerX: nil, centerY: self.view.centerYAnchor, top: nil, leading: self.view.leadingAnchor, bottom: nil, trailing: self.view.trailingAnchor, padding: .init(), size: CGSize(width: 0, height: 60))
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override var intrinsicContentSize: CGSize {
        return CGSize.zero
    }
    
    //    //MARK:- Fix the problem with iPhone X to inputAccessoryView
    override func didMoveToWindow() {
        super.didMoveToWindow()
        if #available(iOS 11.0, *) {
            if let window = window {
                bottomAnchor.constraint(lessThanOrEqualToSystemSpacingBelow: window.safeAreaLayoutGuide.bottomAnchor, multiplier: 1.0).isActive = true
            }
        }
    }
}
