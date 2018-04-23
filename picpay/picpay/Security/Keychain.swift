//
//  Keychain.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//
// Reference: https://gist.github.com/PaulWagener/67bda40a26aa4ef4a938b2cc895f7309
//

import Foundation
import Security

class Keychain {
    
    class func set(key: String, value: String) -> Bool {
        if let data = value.data(using: String.Encoding.utf8) {
            return set(key: key, value: data)
        }
        return false
    }
    
    class func set(key: String, value: Data) -> Bool {
        let query = [
            kSecClass: kSecClassGenericPassword,
            kSecAttrAccount: key,
            kSecValueData: value
            ] as CFDictionary
        
        SecItemDelete(query)
        return SecItemAdd(query, nil) == noErr
    }
    
    public class func get(key: String) -> String? {
        if let data = getData(key: key) {
            return String(data: data as Data, encoding: String.Encoding.utf8)
        }
        return nil
    }
    
    public class func getData(key: String) -> Data? {
        let query = [
            kSecClass: kSecClassGenericPassword,
            kSecAttrAccount: key,
            kSecReturnData: kCFBooleanTrue,
            kSecMatchLimit: kSecMatchLimitOne
            ] as CFDictionary
        
        var dataTypeRef: AnyObject?
        let status = SecItemCopyMatching(query, &dataTypeRef)
        
        if status == noErr && dataTypeRef != nil {
            return dataTypeRef as? Data
        }
        
        return nil
    }
    
    public class func delete(key: String) -> Bool {
        let query = [
            kSecClass: kSecClassGenericPassword,
            kSecAttrAccount: key
            ] as CFDictionary
        return SecItemDelete(query) == noErr
    }
    
    public class func clear() -> Bool {
        let query = [
            kSecClass: kSecClassGenericPassword
            ] as CFDictionary
        return SecItemDelete(query) == noErr
    }
}
