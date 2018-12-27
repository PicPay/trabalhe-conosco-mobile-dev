//
//  TopIndicatorGesture.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 08/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class TopIndicatorGestureView: UIView {
    
    let containerView: UIView = {
        let view = UIView()
        view.backgroundColor = .lightBlack
        view.layer.masksToBounds = true
        return view
    }()
    let indicatorView: UIView = {
        let view = UIView()
        view.backgroundColor = .lightGray
        view.layer.cornerRadius = 2.5
        view.layer.masksToBounds = true
        return view
    }()
    let title: UILabel = {
        let label = UILabel()
        label.textColor = .white
        label.text = "Recibo"
        label.textAlignment = .center
        label.font = UIFont.boldSystemFont(ofSize: 19)
        return label
    }()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
    }
    fileprivate func configure() {
        self.backgroundColor = .clear
        self.addSubview(self.containerView)
        self.addSubview(self.indicatorView)
        self.addSubview(self.title)
        
        self.containerView.anchor(top: self.topAnchor, leading: self.leadingAnchor, bottom: self.title.bottomAnchor, trailing: self.trailingAnchor, padding: .init(top: 0, left: 0, bottom: -5, right: 0))
        
        self.indicatorView.anchorXY(centerX: self.containerView.centerXAnchor, centerY: nil, top: self.layoutMarginsGuide.topAnchor, leading: nil, bottom: nil, trailing: nil, padding: .init(top: 5, left: 0, bottom: 0, right: 0), size: .init(width: 50, height: 5))
        
        self.title.anchorXY(centerX: self.containerView.centerXAnchor, centerY: nil, top: self.indicatorView.bottomAnchor, leading: nil, bottom: nil, trailing: nil, padding: .init(top: 10, left: 0, bottom: 0, right: 0), size: .init(width: 100, height: 30))
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
