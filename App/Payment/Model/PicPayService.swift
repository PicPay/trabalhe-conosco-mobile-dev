//
//  PicPayService.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 09/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

final class PicPayService {
    private let client = WebClient(baseUrl: URL(string: "http://careers.picpay.com/tests/mobdev/")!)
    
    func loadPersons(completion: @escaping ([Person]?, WebError?) -> ()) -> URLSessionDataTask? {
        return client.request(path: "users", body: nil) { data, error in
            if let json = data {
                completion(try? JSONDecoder().decode([Person].self, from: json), nil)
            } else {
                completion(nil, error)
            }
        }
    }
}
