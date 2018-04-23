//
//  ConfirmPaymentViewController.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import UIKit

class ConfirmPaymentViewController: UIViewController {

    @IBOutlet weak var containerView: UIView!
    @IBOutlet weak var avatarImageView: UIImageView!
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var valueTextField: UITextField!
    @IBOutlet weak var payButton: UIButton!
    
    var viewModel:ConfirmPaymentViewModel!
    var initialTouchPoint: CGPoint = CGPoint(x: 0,y: 0)
    var initialContainerViewFrame: CGRect!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let urlImage = viewModel.user.img {
            avatarImageView.loadImage(from: urlImage)
        }
        usernameLabel.text = viewModel.user.username
        nameLabel.text = viewModel.user.name
        setupView()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        initialContainerViewFrame = containerView.frame
        UIView.animate(withDuration: 0.3) {
            self.view.backgroundColor = UIColor.black.withAlphaComponent(0.7)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func payButtonTapped(_ sender: Any) {
        viewModel.confirmPaymment(onSuccess: { [weak self] in
            guard let strongSelf = self else { return }
            DispatchQueue.main.async {
                let alert = UIAlertController(title: "Sucesso", message: "Pagamento efetuado com sucesso", preferredStyle: .alert)
                let action = UIAlertAction(title: "OK", style: .default, handler: { _ in
                    strongSelf.dismiss(animated: true, completion: nil)
                })
                alert.addAction(action)
                strongSelf.present(alert, animated: true, completion: nil)
            }
        }, onError: { [weak self] message in
            guard let strongSelf = self else { return }
            DispatchQueue.main.async {
                let alert = UIAlertController(title: "Atenção", message: message, preferredStyle: .alert)
                let action = UIAlertAction(title: "OK", style: .default, handler: nil)
                alert.addAction(action)
                strongSelf.present(alert, animated: true, completion: nil)
            }
        })
    }
    
    @IBAction func panGestureAction(_ sender: UIPanGestureRecognizer) {
        let touchPoint = sender.location(in: self.view?.window)
        if sender.state == UIGestureRecognizerState.began {
            initialTouchPoint = touchPoint
        } else if sender.state == UIGestureRecognizerState.changed {
            let translation = sender.translation(in: self.view)
            self.containerView.center = CGPoint(x: self.containerView.center.x + translation.x, y: self.containerView.center.y + translation.y)
            sender.setTranslation(CGPoint.zero, in: self.view)
        } else if sender.state == UIGestureRecognizerState.ended || sender.state == UIGestureRecognizerState.cancelled {
            if (touchPoint.y - initialTouchPoint.y > 150) ||  (touchPoint.y - initialTouchPoint.y < -150) {
                self.dismiss(animated: true, completion: nil)
            } else {
                UIView.animate(withDuration: 0.4, animations: {
                    self.containerView.frame = self.initialContainerViewFrame
                })
            }
        }
    }
    
    @IBAction func tapGestureAction(_ sender: UITapGestureRecognizer) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func valueTextFieldDidChange(_ sender: UITextField) {
        if let amountString = sender.text?.currencyInputFormatting() {
            sender.text = amountString
            viewModel.value = Double(
                amountString.replacingOccurrences(of: "$", with: "")
                .replacingOccurrences(of: ".", with: "")
                .replacingOccurrences(of: ",", with: ".")
            ) ?? 0.0
        }
    }
    
}

extension ConfirmPaymentViewController {
    
    func setupView() {
        self.view.backgroundColor = UIColor.clear
        containerView.layer.cornerRadius = 10
        avatarImageView.roundedImage()
        containerView.backgroundColor = UIColor.white
        containerView.clipsToBounds = true
        payButton.backgroundColor = ColorConstant.primary
        payButton.tintColor = UIColor.white
        payButton.layer.cornerRadius = payButton.frame.height / 2
        valueTextField.layer.cornerRadius = valueTextField.frame.height / 2
        self.modalPresentationCapturesStatusBarAppearance = true
    }
}
