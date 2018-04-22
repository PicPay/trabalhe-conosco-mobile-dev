//
//  UserTableViewCell.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import UIKit

class UserTableViewCell: UITableViewCell {

    @IBOutlet weak var avatarImageView: UIImageView!
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var containerView: UIView!
    
    public static let identifier = "userIdentifier"
    
    var user: User? {
        didSet {
            guard let user = self.user else { return }
            usernameLabel.text = user.username
            nameLabel.text = user.name
            guard let urlForImage = user.img else { return }
            avatarImageView.loadImage(from: urlForImage, placeholder: nil, showActivityIndicator: true, cache: nil)
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        avatarImageView.roundedImage()
        makeShadown()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    private func makeShadown() {
        self.layer.masksToBounds = true
        containerView.layer.cornerRadius = 10
        containerView.clipsToBounds = true
        containerView.layer.masksToBounds = false
        containerView.layer.shadowOffset = CGSize(width: 0, height: 0.5)
        containerView.layer.shadowColor = UIColor.black.cgColor
        containerView.layer.shadowOpacity = 0.15
        containerView.layer.shadowRadius = 0.9
        self.backgroundColor = UIColor.clear
    }
}
