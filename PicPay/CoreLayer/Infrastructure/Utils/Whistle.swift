//
//  Whistle.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation
import Whisper

enum WhistleType: Int {
    case save
    case waitingForResponse
    case warning
    case error
}

class Whistle {
    @discardableResult init(withMessage msg: String, action: WhistleType = .save) {
        let time: TimeInterval = TimeInterval(5)
        switch action {
        case .save:
            self.show(withMessage: msg, titleColor: .white, bgColor: UIColor(hex: Env.Palette.green), time: time)
        case .waitingForResponse:
            self.show(withMessage: msg, titleColor: .white, bgColor: UIColor(hex: Env.Palette.yellow), time: nil)
        case .warning:
            self.show(withMessage: msg, titleColor: .white, bgColor: UIColor(hex: Env.Palette.yellow), time: time)
        case .error:
            self.show(withMessage: msg, titleColor: .white, bgColor: .darkGray, time: time)
        }
    }
    
    private func show(withMessage msg: String, titleColor: UIColor, bgColor: UIColor, time: TimeInterval?) {
        let murmur = Murmur(title: msg, backgroundColor: bgColor, titleColor: titleColor)
        
        // Present a permanent status bar message
        Whisper.show(whistle: murmur, action: .present)
        if let time = time {
            Whisper.hide(whistleAfter: time)
        }
    }
    
    static func observer(withMessage msg: String) {
        NotificationCenter.default.post(name: .notifyWhistle, object: msg)
    }
}
