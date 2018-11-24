//
//  DocumentManager.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

class DocumentManager: NSObject {
    private let business: DocumentBusiness = DocumentBusiness()
    
    func loadPDFUrl(_ pdfName: String, completion: DocumentCompletion) {
        self.business.loadPDFUrl(pdfName, completion: completion)
    }
}
