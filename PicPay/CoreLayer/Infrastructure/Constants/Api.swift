//
//  Api.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//
import Foundation

extension Env {
    struct Api {
        static var basePath: String = "http://careers.picpay.com/tests/mobdev"
    }
}

struct Api {
    static var users: Session {
        let urlSession = URLSession(configuration: .ephemeral)
        guard let baseURL = URL(string: Env.Api.basePath) else { fatalError("The baseURL cant be parsed to URL") }
        
        return Session(session: urlSession, baseURL: baseURL)
    }

}
