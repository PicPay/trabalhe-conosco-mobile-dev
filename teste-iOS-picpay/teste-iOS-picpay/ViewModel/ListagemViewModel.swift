//
//  ListagemViewModel.swift
//  teste-iOS-picpay
//
//  Created by Bruno Lopes de Mello on 18/11/18.
//  Copyright © 2018 Bruno Lopes de Mello. All rights reserved.
//

import Foundation

protocol ListagemViewModelProtocol {
    var pessoas: [PessoasRetornoElement] {get set}
    func numberOfSections() -> Int
    func numberOfRows(in section:Int) -> Int
    func getPessoa(in indexPath: IndexPath) -> PessoasRetornoElement?
    func getListagemDePessoas(onComplete: @escaping () -> Void, onError: @escaping (_ mensagem: String) -> Void)
    func verificaCartaoCadastrado(onComplete: @escaping () -> Void,
                                  onError: @escaping () -> Void) -> Void
}

class ListagemViewModel: ListagemViewModelProtocol {
    
    var pessoas: [PessoasRetornoElement] = [PessoasRetornoElement]()
    
    
    func numberOfSections() -> Int {
        return 1
    }
    
    func numberOfRows(in section: Int) -> Int {
        return pessoas.isEmpty ? 0 : pessoas.count
    }
    
    func getPessoa(in indexPath: IndexPath) -> PessoasRetornoElement? {
        return pessoas.isEmpty ? nil : pessoas[indexPath.row]
    }
    
    func getListagemDePessoas(onComplete: @escaping () -> Void, onError: @escaping (String) -> Void) {
        ApiConnection.getListagemPessoas(onComplete: { (listaPessoas) in
            self.pessoas = listaPessoas
            onComplete()
        }) { (msg) in
            onError(msg)
        }
    }
    
    func verificaCartaoCadastrado(onComplete: @escaping () -> Void,
                                  onError: @escaping () -> Void) -> Void {
        if CoreDataCartaoManager.fetchCartao() != nil {
            onComplete()
            return
        }
        
        onError()
    }
}
