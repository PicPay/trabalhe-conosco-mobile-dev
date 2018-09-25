//
//  UsersDataSource.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 18/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import Foundation
import Alamofire
import SwiftyJSON

final class UsersDataSource {
    var users: [User] = []
    var usersDic = [[String : AnyObject]]()
    
    public func getUsers() -> [User] {
        return users
    }
    
    private func fetchUsers(){
        Alamofire.request("http://careers.picpay.com/tests/mobdev/users").responseJSON { (responseData) -> Void in
            if((responseData.result.value) != nil) {
                if let usersData = JSON(responseData.result.value!).arrayObject{
                    if let usersDictionary = usersData as? [[String: AnyObject]] {
                        self.usersDic = usersDictionary
                        self.users = self.dataFormUsers(usersDictionary: usersDictionary)
                    }
                }
            }
        }
    }
    
    private func dataFormUsers(usersDictionary: [[String : AnyObject]]) -> [User] {
        var users = [User]()
        for userDictionary in usersDictionary {
            let name = userDictionary["name"] as! String
            let img = userDictionary["img"] as! String
            let id = userDictionary["id"] as! Int
            let username = userDictionary["username"] as! String
            
            let newUser = User(id: id, name: name, imageURL: URL(string: img)!, username: username)
            users.append(newUser)
        }
        return users
    }
    
}
