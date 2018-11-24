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
    
    public var model: Dynamic<[<#model class#>]> = Dynamic([<#model class#>]())
    
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.model.content.count ?? 0
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: <#reuse cell class#>.storyboardId, for: indexPath) as? <#reuse cell class#> else { preconditionFailure("The cell class cant be found") }
        guard indexPath.row < model.content.count else { preconditionFailure("Can't load item from model") }
        let item = self.model.content[indexPath.row]
        
        cell.update(withItem: item)
        
        return cell
    }
}
