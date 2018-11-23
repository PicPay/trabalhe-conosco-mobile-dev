//
//  Codable+Extension.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation

extension Encodable {
    
    var dictionary: [String: Any]? {
        guard let data = try? JSONEncoder().encode(self) else { return nil }
        return (try? JSONSerialization.jsonObject(with: data, options: .allowFragments)).flatMap { $0 as? [String: Any] }
    }
    
    var data: Data? {
        guard let data = try? JSONEncoder().encode(self) else { return nil }
        return data
    }
    
    func dictionary(encodeStrategy: JSONEncoder.KeyEncodingStrategy?) -> [String: Any]? {
        let encoder = JSONEncoder()
        if encodeStrategy != nil {
            encoder.keyEncodingStrategy = encodeStrategy!
        }
        
        guard let data = try? encoder.encode(self) else { return nil }
        return (try? JSONSerialization.jsonObject(with: data, options: .allowFragments)).flatMap { $0 as? [String: Any] }
    }
    
    func data(encodeStrategy: JSONEncoder.KeyEncodingStrategy?) -> Data? {
        let encoder = JSONEncoder()
        if encodeStrategy != nil {
            encoder.keyEncodingStrategy = encodeStrategy!
        }
        
        guard let data = try? encoder.encode(self) else { return nil }
        return data
    }
}
