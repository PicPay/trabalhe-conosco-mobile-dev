//
//  CredcardDataSource.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

// MARK: - Overrides

public final class CredcardDataSource: NSObject, UITableViewDataSource {
    
    // MARK: - Properties
    
    public var model: Dynamic<[Card]> = Dynamic([Card]())
    
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.model.content.count
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: CredcardListUIViewCell.storyboardId, for: indexPath) as? CredcardListUIViewCell else { preconditionFailure("The cell class cant be found") }
        guard indexPath.row < model.content.count else { preconditionFailure("Can't load item from model") }
        let item = self.model.content[indexPath.row]
        
        cell.update(with: item)
        
        return cell
    }
}
