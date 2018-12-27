//
//  BaseTableViewCStatic.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 10/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class BaseTableViewCStatic: UITableViewController {
    
    let cellid = "cellid"
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tableView.keyboardDismissMode = .interactive
        
        self.tableView.tableFooterView = UIView()
        self.tableView.separatorStyle = .none
        self.tableView.register(BaseStaticCell.self, forCellReuseIdentifier: cellid)
    }
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return self.tableView.frame.height
    }
}

class BaseStaticCell: UITableViewCell {
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        self.backgroundColor = .clear
        self.selectionStyle = .none
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
