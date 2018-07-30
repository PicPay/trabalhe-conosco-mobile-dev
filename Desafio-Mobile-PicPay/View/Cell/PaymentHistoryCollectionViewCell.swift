//
//  PaymentHistoryCollectionViewCell.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 28/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

//View desenvolvida utilizando View Code para demonstrar conhecimento

import UIKit

class PaymentHistoryCollectionViewCell: UICollectionViewCell {
    
    var transaction: Transaction? {
        didSet{
            guard let transaction = transaction else {return}
            guard let user = transaction.transaction_user else {return}
            let atributtedString = NSMutableAttributedString(string: "Você", attributes: [NSAttributedStringKey.font : UIFont.systemFont(ofSize: 17, weight: .bold)])
            atributtedString.append(NSAttributedString(string: " pagou a ", attributes: [NSAttributedStringKey.font : UIFont.systemFont(ofSize: 17, weight: .light)]))
            atributtedString.append(NSAttributedString(string: "\(String(describing: user.username!))", attributes: [NSAttributedStringKey.font : UIFont.systemFont(ofSize: 17, weight: .bold)]))
            descriptionLabel.attributedText = atributtedString
            valueLabel.text = "\(Strings.R$) \(transaction.value.doubleToMoneyMask())"
            profileImageView.downloadImageView(user.profile_image_url!)
            self.dateLabel.text = transaction.date?.timeAgoDisplay()
        }
    }
    
    let profileImageView: CustomImageView = {
        let imageView = CustomImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.image = #imageLiteral(resourceName: "user-default")
        imageView.contentMode = .scaleAspectFill
        imageView.layer.cornerRadius = 25
        imageView.clipsToBounds = true
        return imageView
    }()
    
    let descriptionLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    let valueLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "R$ 6,24"
        label.textColor = UIColor(red: 216/255, green: 47/255, blue: 92/255, alpha: 1)
        label.font = UIFont.systemFont(ofSize: 15, weight: .semibold)
        return label
    }()
    
    let padlockImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.image = #imageLiteral(resourceName: "padlock")
        imageView.contentMode = .scaleAspectFill
        return imageView
    }()
    
    let separatorView:UIView = {
        let view = UIView()
        view.backgroundColor = .gray
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()
    
    let dateLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "2 semanas atrás"
        label.textColor = UIColor.gray
        label.font = UIFont.systemFont(ofSize: 15, weight: .regular)
        return label
    }()
    
    var stackView: UIStackView?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setupViews()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func setupViews(){
        self.backgroundColor = .white
        self.layer.cornerRadius = 7
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOpacity = 0.5
        self.layer.shadowOffset = CGSize.zero
        self.layer.shadowRadius = 1.3
        
        self.addSubview(profileImageView)
        self.addSubview(descriptionLabel)
        
        profileImageView.leftAnchor.constraint(equalTo: self.leftAnchor, constant: 12).isActive = true
        profileImageView.topAnchor.constraint(equalTo: self.topAnchor, constant: 12).isActive = true
        profileImageView.heightAnchor.constraint(equalToConstant: 50).isActive = true
        profileImageView.widthAnchor.constraint(equalToConstant: 50).isActive = true
        
        descriptionLabel.centerYAnchor.constraint(equalTo: profileImageView.centerYAnchor).isActive = true
        descriptionLabel.leftAnchor.constraint(equalTo: profileImageView.rightAnchor, constant: 12).isActive = true
        descriptionLabel.rightAnchor.constraint(equalTo: self.rightAnchor, constant: -6).isActive = true
        descriptionLabel.heightAnchor.constraint(equalToConstant: 18).isActive = true
        
        setUpStackView()
    }
    
    func setUpStackView(){
        let view = UIView()
        
        stackView = UIStackView(arrangedSubviews: [valueLabel, separatorView, padlockImageView, dateLabel, view])
        stackView?.translatesAutoresizingMaskIntoConstraints = false
        stackView?.axis = .horizontal
        stackView?.spacing = 6
        stackView?.distribution = .fill
        
        self.addSubview(stackView!)
        
        stackView?.leftAnchor.constraint(equalTo: profileImageView.leftAnchor).isActive = true
        stackView?.rightAnchor.constraint(equalTo: self.rightAnchor, constant: -8).isActive = true
        stackView?.bottomAnchor.constraint(equalTo: self.bottomAnchor, constant: -12).isActive = true
        stackView?.heightAnchor.constraint(equalToConstant: 18).isActive = true
        
        separatorView.widthAnchor.constraint(equalToConstant: 0.8).isActive = true
        padlockImageView.widthAnchor.constraint(equalToConstant: 18).isActive = true
    }
}
