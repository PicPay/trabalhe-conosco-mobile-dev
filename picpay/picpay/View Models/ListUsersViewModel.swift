//
//  ListUsersViewModel.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import Foundation
import UIKit

class ListUsersViewModel {
    
    var listOfUsers: [User] = []
    
    func numberOfSection() -> Int {
        return listOfUsers.isEmpty ? 0 : 1
    }
    
    func numberOfRows(in section: Int) -> Int {
        return listOfUsers.count
    }

    func user(of indexPath: IndexPath) -> User {
        return listOfUsers[indexPath.row]
    }
    
    func getUsers(onSuccess: @escaping () -> Void, onError: @escaping (String) -> Void) {
        NetworkManager.shared.getUsers { [unowned self] (users, message) in
            guard let users = users else {
                guard let message = message else {
                    onError("Ocorreu um erro")
                    return
                }
                onError(message)
                return
            }
            self.listOfUsers = users
            onSuccess()
        }
    }
}
