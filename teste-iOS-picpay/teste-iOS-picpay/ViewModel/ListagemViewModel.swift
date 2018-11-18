//
//  ListagemViewModel.swift
//  teste-iOS-picpay
//
//  Created by Bruno Lopes de Mello on 18/11/18.
//  Copyright © 2018 Bruno Lopes de Mello. All rights reserved.
//

import Foundation

protocol ListagemViewModelProtocol {
    var pessoas: [String] {get set}
    func numberOfSections() -> Int
    func numberOfRows(in section:Int) -> Int
    func getPessoa(in indexPath: IndexPath) -> String
}

class ListagemViewModel: ListagemViewModelProtocol {
    
    var pessoas: [String] = ["Cristiano", "Ronaldo", "Bruno", "Felipe", "Lucas", "Carol", "Sirlene", "Bruna"]
    
    
    func numberOfSections() -> Int {
        return 1
    }
    
    func numberOfRows(in section: Int) -> Int {
        return pessoas.count
    }
    
    func getPessoa(in indexPath: IndexPath) -> String {
        return pessoas[indexPath.row]
    }
    
    
}
