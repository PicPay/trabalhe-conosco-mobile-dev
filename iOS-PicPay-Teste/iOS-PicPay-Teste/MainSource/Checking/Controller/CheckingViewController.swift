//
//  CheckingViewController.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class CheckingViewController: UIViewController, UIGestureRecognizerDelegate {

    let checing = CheckingView()
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.navigationBar.isHidden = false
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.isHidden = true
    }
    
    deinit {
        print("Removeu referência")
    }
    
    //Constants
    var ticket: TicketUser!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configurePaymentView()
    }

    //MARK:- ConfigureViewC
    fileprivate func configurePaymentView() {
        self.view.backgroundColor = .strongBlack
        self.view.addSubview(self.checing)
        self.checing.fillSuperview()
        self.checing.ticket = ticket.map( {return TicketViewModel(ticket: $0)} )

        let gesture = UIPanGestureRecognizer(target: self, action: #selector(onTapPan(_:)))
        gesture.delegate = self
        self.view.addGestureRecognizer(gesture)
        
        let indicator = TopIndicatorGestureView()
        self.view.addSubview(indicator)
        indicator.anchor(top: self.view.topAnchor, leading: self.view.leadingAnchor, bottom: nil, trailing: self.view.trailingAnchor, padding: .init(), size: .init(width: 0, height: 44))
        
        self.modalPresentationStyle = .overCurrentContext
        self.providesPresentationContextTransitionStyle = true
        self.definesPresentationContext = true
    }

    
    //MARK:- Make Drag Down
    var initialTouchPoint: CGPoint = CGPoint(x: 0, y: 0)
    @objc func onTapPan(_ sender: UIPanGestureRecognizer) {
        
        let touchPoint = sender.location(in: self.view.window)
        if sender.state == UIGestureRecognizer.State.began {
            initialTouchPoint = touchPoint
        } else if sender.state == UIGestureRecognizer.State.changed {
            if touchPoint.y - initialTouchPoint.y > 0 {
                self.view.frame = CGRect(x: 0, y: touchPoint.y - initialTouchPoint.y, width: self.view.frame.size.width, height: self.view.frame.size.height)
            }
        } else if sender.state == UIGestureRecognizer.State.ended  || sender.state == UIGestureRecognizer.State.cancelled {
            if touchPoint.y - initialTouchPoint.y > 100 {
                let transition = CATransition()
                transition.duration = 0.4
                transition.type = CATransitionType.reveal
                transition.subtype = CATransitionSubtype.fromBottom
                transition.prepareForInterfaceBuilder()
                self.navigationController?.view.layer.add(transition, forKey: kCATransition)
                self.dismiss(animated: true, completion: nil)
                
            } else {
                UIView.animate(withDuration: 0.3) {
                    self.view.frame = CGRect(x: 0, y: 0, width: self.view.frame.size.width, height: self.view.frame.size.height)
                }
            }
            
        }
    }
}
