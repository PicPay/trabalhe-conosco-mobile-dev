//
//  PicPayService.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 09/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

final class PicPayService {
    static func deleteCard(_ card: Card) -> Bool {
        return DataManager.delete(at: "Cards/\(card.id)")
    }
    
    static func saveCard(_ card: Card) -> Bool {
        return DataManager.save(card, at: "Cards/\(card.id)")
    }
    
    static func loadCards() -> [Card] {
        return DataManager.loadAll(Card.self, at: "Cards")
    }
    
    static func loadTransactions() -> [Transaction] {
        return DataManager.loadAll(Transaction.self, at: "Transactions")
    }
    
    private let client = WebClient(baseUrl: URL(string: "http://careers.picpay.com/tests/mobdev/")!)
    
    func loadPersons(completion: @escaping ([Person]?) -> ()) -> URLSessionDataTask? {
        return client.request(path: "users", body: nil) { response in
            switch response {
            case .success(let data):
                completion(try? JSONDecoder().decode([Person].self, from: data))
            case .failure:
                completion(nil)
            }
        }
    }
    
    func sendPayment(_ payment: Payment, completion: @escaping (Bool) -> ()) -> URLSessionDataTask? {
        return client.request(path: "transaction", body: try! JSONEncoder().encode(payment)) { response in
            var status: Transaction.Status!
            
            switch response {
            case .success(let data):
                if let obj = try? JSONSerialization.jsonObject(with: data, options: []) as! [String : Any],
                    let tx = obj["transaction"] as? [String : Any],
                    let success = tx["success"] as? Int, success == 1 {
                    status = .approved
                } else {
                    status = .declined
                }
            case .failure:
                status = .declined
            }
            
            let transaction = Transaction(id: NSUUID().uuidString, date: Date(), status: status, payment: payment)
            let _ = DataManager.save(transaction, at: "Transactions/\(transaction.id)")
            completion(status == .approved)
        }
    }
}
