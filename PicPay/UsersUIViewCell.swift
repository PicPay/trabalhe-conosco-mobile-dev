//
//  UsersUIViewCell.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class UsersUIViewCell: UITableViewCell {
    
    // MARK: - Properties
    static let storyboardId = "UsersUIViewCell"
    
    // Instantiate the Nib file
    class func instanceFromNib() -> UINib {
        return UINib(nibName: storyboardId, bundle: nil)
    }
    
    // MARK: - Outlets
    
    @IBOutlet weak var imgAvatar: RoundedUIImage!
    @IBOutlet weak var lblUsername: UILabel!
    @IBOutlet weak var lblName: UILabel!
    
    public func update(with user: User) {
        lblUsername.text = user.username
        lblName.text = user.name
        
        if let url = URL(string: user.img) {
            ImageStore.shared.fetchImage(for: user.username, from: url) { [weak self] result in
                guard let wSelf = self else { return }
                
                switch result {
                case let .success(img):
                    wSelf.imgAvatar.image = img
                case let .failure(error):
                    dd(error)
                }
            }
        }
    }
    
    public override func prepareForReuse() {
        imgAvatar.image = UIImage(named: "avatarPlaceholder")
    }
}
