//
//  DataManager.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 12/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

public class DataManager {

    static fileprivate var userDocuments: [URL] {
        return FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
    }
    
    static func delete(at path: String) -> Bool {
        
        let file = userDocuments.first!.appendingPathComponent(path, isDirectory: false)
        
        do {
            try FileManager.default.removeItem(at: file)
        } catch {
            return false
        }
        
        return true
    }
    
    static func save <T:Encodable>(_ object: T, at path: String) -> Bool {
        
        let file = userDocuments.first!.appendingPathComponent(path, isDirectory: false)
        
        let dir = file.deletingLastPathComponent()
        
        do {
            if !FileManager.default.fileExists(atPath: dir.path) {
                try FileManager.default.createDirectory(at: dir, withIntermediateDirectories: true)
            }
            
            FileManager.default.createFile(atPath: file.path, contents: try JSONEncoder().encode(object))
        } catch {
            return false
        }
        
        return true
    }
    
    static func loadAll <T:Decodable>(_ value: T.Type, at path: String) -> [T] {
        
        let dir = userDocuments.first!.appendingPathComponent(path, isDirectory: true)
        
        do {
            return try FileManager.default.contentsOfDirectory(atPath: dir.path)
                .map({ dir.appendingPathComponent($0, isDirectory: false) })
                .map({ FileManager.default.contents(atPath: $0.path)! })
                .map({ try JSONDecoder().decode(value, from: $0) })
        } catch {
            return []
        }
    }
}
