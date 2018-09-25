//
//  UserViewModel.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 17/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import Foundation
import UIKit

public class UserViewModel {
    private let user : User
    private var image: UIImage?
    
    public init(user : User) {
        self.user = user
        
    }
    
    public var name: String {
        return user.name
    }
    
    public var id : Int {
        return user.id
    }
    
    public var username : String {
        return user.username
    }
    
    public var userImage : UIImage {
        return self.userImage
    }
    
    
}
