//
//  UserDataSource.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

// MARK: - Overrides

public final class UserDataSource: NSObject, UITableViewDataSource {
    
    // MARK: - Properties
    
    public var model: Dynamic<[User]> = Dynamic([User]())
    public var allUsers: [User] = []

    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.model.content.count
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: UsersUIViewCell.storyboardId, for: indexPath) as? UsersUIViewCell else { preconditionFailure("The cell class cant be found") }
        guard indexPath.row < model.content.count else { preconditionFailure("Can't load item from model") }
        let item = self.model.content[indexPath.row]
        
        cell.update(with: item)
        
        return cell
    }
}
