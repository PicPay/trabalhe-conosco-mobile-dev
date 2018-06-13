//
//  DataManager.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 12/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

public class DataManager {

    static fileprivate var documentUrls: [URL] {
        return FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
    }
    
    static func delete(_ path: String) throws {
        let url = documentUrls.first!.appendingPathComponent(path, isDirectory: false)
        try FileManager.default.removeItem(at: url)
    }
    
    static func save <T:Encodable>(_ object: T, with path: String) throws {
        let data = try JSONEncoder().encode(object)
        let url = documentUrls.first!.appendingPathComponent(path, isDirectory: false)
        FileManager.default.createFile(atPath: url.path, contents: data, attributes: nil)
    }
    
    static func load <T:Decodable>(_ path: String, with type: T.Type) throws -> T {
        let url = documentUrls.first!.appendingPathComponent(path, isDirectory: false)
        let data = FileManager.default.contents(atPath:url.path)
        return try JSONDecoder().decode(type, from: data!)
    }
    
    static func loadAll <T:Decodable>(_ type: T.Type) throws -> [T] {
        return try FileManager.default.contentsOfDirectory(atPath: documentUrls.first!.path)
            .map({ try load($0, with: type) })
    }
}
