//
//  Dynamic.swift
//  Loteca
//
//  Created by Marcelo Reis on 11/06/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public final class Dynamic<T> {
    typealias Listener = (T) -> Void
    var listener: Listener?
    
    func bind(_ listener: Listener?) {
        self.listener = listener
    }
    
    func bindAndFire(_ listener: Listener?) {
        self.listener = listener
        listener?(content)
    }
    
    var content: T {
        didSet {
            listener?(content)
        }
    }
    
    init(_ v: T) {
        content = v
    }
}
