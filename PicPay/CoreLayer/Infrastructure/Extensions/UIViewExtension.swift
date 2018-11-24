//
//  UIViewExtension.swift
//  Loteca
//
//  Created by Marcelo Reis on 07/06/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

extension UIView {
    /**
     * Extension to load a UIView from nib
     * Call it like this:
     * let myCustomView: CustomView = UIView.fromNib()
     * ..or even:
     * let myCustomView: CustomView = .fromNib()
     */
    class func fromNib<T: UIView>() -> T {
        guard let nib = Bundle.main.loadNibNamed(String(describing: T.self), owner: nil, options: nil)?.first as? T else { preconditionFailure("Cant find NIB named \(T.self)") }
        
        return nib
    }
}
