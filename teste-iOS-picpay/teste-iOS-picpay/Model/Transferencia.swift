//
//  Transferencia.swift
//  teste-iOS-picpay
//
//  Created by Bruno Lopes de Mello on 18/11/18.
//  Copyright © 2018 Bruno Lopes de Mello. All rights reserved.
//

import Foundation

struct TransferenciaRetorno: Codable {
    let transaction: Transferencia?
}

struct Transferencia: Codable {
    let id, timestamp: Int?
    let value: Double?
    let destinationUser: DestinationUser?
    let success: Bool?
    let status: String?
    
    enum CodingKeys: String, CodingKey {
        case id, timestamp, value
        case destinationUser = "destination_user"
        case success, status
    }
}

struct DestinationUser: Codable {
    let id: Int?
    let name: String?
    let img: String?
    let username: String?
}

func newJSONDecoder() -> JSONDecoder {
    let decoder = JSONDecoder()
    if #available(iOS 10.0, OSX 10.12, tvOS 10.0, watchOS 3.0, *) {
        decoder.dateDecodingStrategy = .iso8601
    }
    return decoder
}

func newJSONEncoder() -> JSONEncoder {
    let encoder = JSONEncoder()
    if #available(iOS 10.0, OSX 10.12, tvOS 10.0, watchOS 3.0, *) {
        encoder.dateEncodingStrategy = .iso8601
    }
    return encoder
}

// MARK: - URLSession response handlers

extension URLSession {
    fileprivate func codableTask<T: Codable>(with url: URLRequest, completionHandler: @escaping (T?, URLResponse?, Error?) -> Void) -> URLSessionDataTask {
        return self.dataTask(with: url) { data, response, error in
            guard let data = data, error == nil else {
                completionHandler(nil, response, error)
                return
            }
            completionHandler(try? newJSONDecoder().decode(T.self, from: data), response, nil)
        }
    }
    
    func transferenciaRetornoTask(with url: URLRequest, completionHandler: @escaping (TransferenciaRetorno?, URLResponse?, Error?) -> Void) -> URLSessionDataTask {
        return self.codableTask(with: url, completionHandler: completionHandler)
    }
}
