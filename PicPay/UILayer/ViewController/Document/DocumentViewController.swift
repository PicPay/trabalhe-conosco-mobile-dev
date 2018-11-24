//
//  DocumentViewController.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

class DocumentViewController: UIViewController {
    
    // MARK: - Properties
    
    static let storyboardId = "DocumentVC"
    let documentManager: DocumentManager = DocumentManager()
    
    // MARK: - Computed
    
    private var mainView: DocumentView {
        guard let _view = view as? DocumentView else { preconditionFailure("Please, create the mainview to manager outlets.") }
        
        return _view
    }
    
    // MARK: - Overrides
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.documentManager.loadPDFUrl("iOS-Architecture") {
            (docResult) in
            
            switch docResult {
            case let .Success(pdfData, pdfURL):
                self.mainView.webView.load(pdfData, mimeType: "application/pdf", textEncodingName: "", baseURL: pdfURL.deletingLastPathComponent())
                self.mainView.addSubview(self.mainView.webView)
            case let .Failure(error):
                print(error)
            }
        }
    }
}
