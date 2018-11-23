//
//  String+Extension.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import UIKit

extension String {
    var length: Int { return self.count }
    
    var isNumeric: Bool {
        let formatter = NumberFormatter()
        let number = formatter.number(from: self)
        guard number != nil else { return false }
        
        return true
    }
    
    var isEmpty: Bool {
        return self.trim().length < 1
    }
    
    func trim() -> String {
        return self.trimmingCharacters(in: .whitespacesAndNewlines)
    }
    
    func index(from: Int) -> Index {
        return self.index(startIndex, offsetBy: from)
    }
    
    func substring(from: Int) -> String {
        let fromIndex = index(from: from)
        return substring(from: fromIndex)
    }
    
    func substring(to: Int) -> String {
        let toIndex = index(from: to)
        return substring(to: toIndex)
    }
    
    func substring(with r: Range<Int>) -> String {
        let startIndex = index(from: r.lowerBound)
        let endIndex = index(from: r.upperBound)
        return substring(with: startIndex..<endIndex)
    }
    
    func substring(_ startIndex: Int, length: Int) -> String {
        let start = self.index(self.startIndex, offsetBy: startIndex)
        let end = self.index(self.startIndex, offsetBy: startIndex + length)
        return String(self[start..<end])
    }
    
    func indexRaw(of str: String, after: Int = 0, options: String.CompareOptions = .literal, locale: Locale? = nil) -> String.Index? {
        guard str.length > 0 else {
            // Can't look for nothing
            return nil
        }
        guard (str.length + after) <= self.length else {
            // Make sure the string you're searching for will actually fit
            return nil
        }
        
        let startRange = self.index(self.startIndex, offsetBy: after)..<self.endIndex
        return self.range(of: str, options: options.removing(.backwards), range: startRange, locale: locale)?.lowerBound
    }
    
    func index(of str: String, after: Int = 0, options: String.CompareOptions = .literal, locale: Locale? = nil) -> Int {
        guard let index = indexRaw(of: str, after: after, options: options, locale: locale) else {
            return -1
        }
        return self.distance(from: self.startIndex, to: index)
    }
}

private extension OptionSet where Element == Self {
    /// Duplicate the set and insert the given option
    func inserting(_ newMember: Self) -> Self {
        var opts = self
        opts.insert(newMember)
        return opts
    }
    
    /// Duplicate the set and remove the given option
    func removing(_ member: Self) -> Self {
        var opts = self
        opts.remove(member)
        return opts
    }
}
