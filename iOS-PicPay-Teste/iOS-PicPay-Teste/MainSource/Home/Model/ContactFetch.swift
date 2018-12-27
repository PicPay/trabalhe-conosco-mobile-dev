//
//  ContactFetch.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 03/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

struct ContactsFetch {
    static let shared = ContactsFetch()
    func FetchData<I: Decodable>(_ urlstring: String, completion: @escaping (Array<I>) -> ()) {
        
        BrunoFire().request(urlstring) { (response) in
            do {
                let json = try JSONSerialization.jsonObject(with: response, options: JSONSerialization.ReadingOptions.allowFragments)
                print(json)
            } catch let err{
                print(err)
            }
            do {
                let feed = try JSONDecoder().decode(Array<I>.self, from: response)
                DispatchQueue.main.async {
                    completion(feed)
                }
            } catch let error {
                print(error.localizedDescription)
            }
        }
    }
}
