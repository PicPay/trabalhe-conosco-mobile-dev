//
//  RKLoading.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import UIKit

class RKLoading {

    private static let loadingView = UIView(frame: UIScreen.main.bounds)
    private static let activityIndicator = UIActivityIndicatorView(style: .white)
    
    private static func createLoadingView() {
        loadingView.backgroundColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.3)
        
        activityIndicator.alpha = 1.0
        activityIndicator.center = CGPoint(x: loadingView.frame.size.width / 2, y: loadingView.frame.size.height / 2)
        activityIndicator.startAnimating()
        loadingView.addSubview(activityIndicator)
        
        loadingView.bringSubviewToFront(activityIndicator)
    }
    
    static func showLoading() {
        createLoadingView()
        showStatusLoading()
        UIApplication.shared.keyWindow?.addSubview(loadingView)
    }
    
    static func hideLoading() {
        hideStatusLoading()
        DispatchQueue.main.async {
            activityIndicator.removeFromSuperview()
            loadingView.removeFromSuperview()
        }
    }
    
    static func showStatusLoading() {
        DispatchQueue.main.async {
            UIApplication.shared.isNetworkActivityIndicatorVisible = true
        }
    }
    
    static func hideStatusLoading() {
        DispatchQueue.main.async {
            UIApplication.shared.isNetworkActivityIndicatorVisible = false
        }
    }
    
}
