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
    func transfereValor(pessoa: PessoasRetornoElement, valor: Double, onComplete: @escaping () -> Void,
                        onError: @escaping (_ mensagem: String) -> Void) -> Void
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
    
    func transfereValor(pessoa: PessoasRetornoElement, valor: Double,
                        onComplete: @escaping () -> Void, onError: @escaping (String) -> Void) {
       
        let cartao = CoreDataCartaoManager.fetchCartao()
        
        if let numero = cartao?.numero, let cvv = cartao?.cvv, let expiryDate = cartao?.expiryDate,
            let pessoaId = pessoa.id {
            ApiConnection.getDetalheTransferencia(number: numero, cvv: Int(cvv), expiryDate: expiryDate,
                                                  userId: pessoaId, value: valor, onComplete: { (transferenciaRetorno) in
                
                                                    if let status = transferenciaRetorno.success {
                                                        if status {
                                                            onComplete()
                                                            return
                                                        }
                                                        
                                                        onError("Não foi possível concluir a transação!")
                                                        return
                                                    }
                                                    onError("Não foi possível concluir a transação!")
                                                    return
            }) { (msg) in
                onError(msg)
            }
        }
    }
}
