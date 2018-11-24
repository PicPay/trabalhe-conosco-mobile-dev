//
//  Debugger.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

struct Debugger {
    static var step: Int = 0
}

func dd(_ items: Any..., file: String = #file, function: String = #function, line: Int = #line) {
    if Env.System.isDeveloperModeEnabled {
        let _file = file.lastPathComponent.stringByDeletingPathExtension
        
        var printable: String = ""
        if JSONSerialization.isValidJSONObject(items) {
            do {
                let options = JSONSerialization.WritingOptions.prettyPrinted
                let data = try JSONSerialization.data(withJSONObject: items, options: options)
                if let string = NSString(data: data, encoding: String.Encoding.utf8.rawValue) as String? {
                    printable += string
                }
            } catch {
                print(error)
            }
        } else {
            printable = items.map {"\($0)"} .joined(separator: " ")
        }
        
        var _result: String = ""
        Debugger.step += 1
        _result += "-\n"
        _result += "## DEBUG:\(Debugger.step) ##\n"
        _result += "TRACK:\n"
        _result += "\t LINE: \(line)\n"
        _result += "\t FUNC: \(function)\n"
        _result += "\t FILE: \(_file)\n"
        _result += "PRINT:\n"
        _result += "\t\(printable)\n"
        _result += "\n"
        _result += "## END_DEBUG:\(Debugger.step) ##\n"
        
        print(_result, "\n")
    }
}

/// String syntax sugar extension
extension String {
    var nss: NSString {
        return self as NSString
    }
    var lastPathComponent: String {
        return nss.lastPathComponent
    }
    var stringByDeletingPathExtension: String {
        return nss.deletingPathExtension
    }
}
