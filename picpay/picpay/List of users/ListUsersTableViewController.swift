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
        tableView.backgroundColor = ColorConstant.background
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
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        if identifier == "payment" {
            if let data = Keychain.getData(key: KeysConstant.creditCardKey) {
                do {
                    let crediCard = try JSONDecoder().decode(CreditCard.self, from: data)
                } catch {
                    return false
                }
            } else {
                let alert = UIAlertController(title: "Atenção", message: "Você não possui um cartão de crédito cadastrado, deseja cadastrar agora?", preferredStyle: .alert)
                let actionRegister = UIAlertAction(title: "Cadastrar", style: .default) { action in
                    self.performSegue(withIdentifier: "registerCreditCard", sender: nil)
                }
                let actionCancel = UIAlertAction(title: "Cancelar", style: .cancel, handler: nil)
                alert.addAction(actionRegister)
                alert.addAction(actionCancel)
                self.present(alert, animated: true, completion: nil)
                return false
            }
        }
        return true
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
