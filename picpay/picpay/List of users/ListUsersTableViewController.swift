//
//  ListUsersTableViewController.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import UIKit

class ListUsersTableViewController: UITableViewController {

    let viewModel = ListUsersViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.estimatedRowHeight = 40
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.tableFooterView = UIView() // Remove as linhas de baixo da tabela
        getListaOfUsers()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return viewModel.numberOfSection()
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.numberOfRows(in: section)
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: UserTableViewCell.identifier, for: indexPath) as? UserTableViewCell else {
            fatalError("Não foi possível localizar a célula de usuário")
        }
        cell.user = viewModel.user(of: indexPath)
        return cell
    }

    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }

}

extension ListUsersTableViewController {
    
    func getListaOfUsers() {
        viewModel.getUsers(onSuccess: { [weak self] in
            DispatchQueue.main.async {
                guard let strongSelf = self else { return }
                strongSelf.tableView.reloadData()
            }
        }, onError: { [weak self] message in
            DispatchQueue.main.async {
                guard let strongSelf = self else { return }
                let alert = UIAlertController(title: "Ops", message: message, preferredStyle: .alert)
                let actionAgain = UIAlertAction(title: "Tentar novamente", style: .default, handler: { _ in
                    strongSelf.getListaOfUsers()
                })
                let actionOk = UIAlertAction(title: "Ok", style: .default, handler: nil)
                alert.addAction(actionAgain)
                alert.addAction(actionOk)
                strongSelf.present(alert, animated: true, completion: nil)
            }
        })
    }
}
