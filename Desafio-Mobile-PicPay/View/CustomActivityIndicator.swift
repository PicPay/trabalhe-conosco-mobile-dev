//
//  CustomActivityIndicator.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

class CustomActivityIndicator: UIView {        
    
    let activityIndicator: UIActivityIndicatorView = {
        let activityIndicator = UIActivityIndicatorView(activityIndicatorStyle: UIActivityIndicatorViewStyle.gray)
        activityIndicator.translatesAutoresizingMaskIntoConstraints = false
        return activityIndicator
    }()
    
    lazy var rootViewController = UIApplication.shared.keyWindow?.rootViewController as? UINavigationController
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func showActivityIndicator(){
        setUpView()
        activityIndicator.startAnimating()
    }
    
    func hideActivityIndicator(){
        activityIndicator.stopAnimating()
    }
    
    func setUpView(){
        guard let visibleViewController = rootViewController?.visibleViewController else {return}
        visibleViewController.view.addSubview(activityIndicator)
        
        activityIndicator.centerXAnchor.constraint(equalTo: visibleViewController.view.centerXAnchor).isActive = true
        activityIndicator.centerYAnchor.constraint(equalTo: visibleViewController.view.centerYAnchor).isActive = true
    }
}
