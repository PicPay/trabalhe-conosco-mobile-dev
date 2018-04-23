//
//  UserTableViewCell.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import UIKit

class LoadingView: UIView {

    @IBOutlet weak var activity: UIActivityIndicatorView!
    @IBOutlet weak var textLabel: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
        activity.activityIndicatorViewStyle = .gray
        textLabel.textColor = UIColor.gray
        self.backgroundColor = ColorConstant.background
    }
    
    class func instantiateAndSet(in view: UIView) -> LoadingView {
        let nib = Bundle.main.loadNibNamed("LoadingView", owner: self, options: nil)
        guard let loadingView = nib?.first as? LoadingView else {
            fatalError("Erro ao instanciar a loading view")
        }
        loadingView.setLoading(in: view)
        if let tableView = view as? UITableView {
            tableView.isScrollEnabled = false
        }
        return loadingView
    }

    class func remove(from view: UIView) {
        for viewAux in view.subviews where viewAux is LoadingView {
            UIView.animate(withDuration: 0.5, animations: {
                viewAux.removeFromSuperview()
            })
        }
        if let tableView = view as? UITableView {
            tableView.isScrollEnabled = true
        }
    }
    
    func setLoading(in view: UIView) {
        self.frame = view.bounds
        UIView.animate(withDuration: 0.5, animations: {
            view.addSubview(self)
        })
    }
}
