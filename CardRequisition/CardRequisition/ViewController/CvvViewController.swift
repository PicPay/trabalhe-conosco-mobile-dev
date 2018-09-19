//
//  CvvViewController.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/18/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//

import UIKit

protocol CvvViewControllerDelegate: class {
    func didTapConfirm(cvvNumber: Int)
}

class CvvViewController: UIViewController {
    @IBOutlet private weak var cvvView: CvvView! {
        didSet {
            self.cvvView.delegate = self
        }
    }
    @IBOutlet private weak var exitButton: UIButton! {
        didSet {
            self.exitButton.imageView?.contentMode = .scaleAspectFit
        }
    }
    
    weak var delegate: CvvViewControllerDelegate? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesBegan(touches, with: event)
        self.view.endEditing(true)
    }
    
    @IBAction private func didTapExit(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
}

extension CvvViewController: CvvViewDelegate {
    func didTapConfirm(cvvNumber: Int) {
        self.dismiss(animated: true) {
            self.delegate?.didTapConfirm(cvvNumber: cvvNumber)
        }
    }
}
