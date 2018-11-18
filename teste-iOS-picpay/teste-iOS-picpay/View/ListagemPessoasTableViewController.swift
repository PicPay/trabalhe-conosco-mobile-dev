//
//  ListagemPessoasTableViewController.swift
//  teste-iOS-picpay
//
//  Created by Bruno Lopes de Mello on 18/11/18.
//  Copyright © 2018 Bruno Lopes de Mello. All rights reserved.
//

import UIKit

class ListagemPessoasTableViewController: UITableViewController {
    
    var viewModel: ListagemViewModelProtocol!
    private var shadowImageView: UIImageView?

    override func viewDidLoad() {
        super.viewDidLoad()

        viewModel = ListagemViewModel()
        tableView.tableFooterView = UIView()
        tableView.tableHeaderView?.isHidden = true
        title = "Contatos"
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        if shadowImageView == nil {
            shadowImageView = findShadowImage(under: navigationController!.navigationBar)
        }
        shadowImageView?.isHidden = true
        
        loadData()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        shadowImageView?.isHidden = false
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return viewModel.numberOfSections()
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return viewModel.numberOfRows(in: section)
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if let cell = tableView.dequeueReusableCell(withIdentifier: "reuseCell") as? PessoaCell {
            if let pessoa = viewModel.getPessoa(in: indexPath) {
                cell.preencheCelula(pessoa)
                return cell
            }
            
            return UITableViewCell()
        }
        
        return UITableViewCell()
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        viewModel.verificaCartaoCadastrado(onComplete: {
            //HA UM CARTAO CADASTRADO
            
        }) {
            //NAO HA UM CARTAO CADASTRADO
        }
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.automaticDimension
    }
    
    override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return UITableView.automaticDimension
    }

}

extension ListagemPessoasTableViewController {
    
    private func findShadowImage(under view: UIView) -> UIImageView? {
        if view is UIImageView && view.bounds.size.height <= 1 {
            return (view as! UIImageView)
        }
        
        for subview in view.subviews {
            if let imageView = findShadowImage(under: subview) {
                return imageView
            }
        }
        return nil
    }
    
    func loadData() {
        viewModel.getListagemDePessoas(onComplete: {
            DispatchQueue.main.async {
                self.tableView.reloadData()
                self.tableView.tableHeaderView?.isHidden = false
            }
        }) { (msg) in
            DispatchQueue.main.async {
                let alert = GlobalAlert(with: self, msg: msg)
                alert.showAlert()
            }
        }
    }
}
