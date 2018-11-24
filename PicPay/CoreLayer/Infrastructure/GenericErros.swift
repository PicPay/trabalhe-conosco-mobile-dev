//
//  GenericErros.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

enum GenericError: Error {
    case untreatedError
    case parse(String)
    case invalidURL
    case notFound
    case duplicate
    case invalidDictionaryKey(String)
    case noConnection
}
