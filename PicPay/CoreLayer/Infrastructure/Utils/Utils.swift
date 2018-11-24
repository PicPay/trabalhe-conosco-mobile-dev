//
//  Utils.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Reachability
import Foundation
import AudioToolbox
import UIKit

public final class Utils {
    
    static let shared = Utils()
    private init() {}
    
    enum Vibrations: Int {
        case success = 1519
        case warning = 1520
        case error = 1521
        case light
        case medium
        case heavy
    }
    
    static var hasConnection: Bool {
        if let reachability = Reachability() {
            let networkStatus = reachability.connection
            
            switch networkStatus {
            case .none:
                NotificationCenter.default.post(name: .notifyWhistle, object: "No internet connection")
                return false
            default:
                return true
            }
        }
        
        dd("Unable to create Reachability")
        return false
    }
    
    func openUrl(_ urlString: String) {
        guard let url = URL(string: urlString) else { dd("Cant parse url"); return }
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
    }
    
    func commonElements<T: Sequence, U: Sequence>(_ lhs: T, _ rhs: U) -> [T.Iterator.Element]
        where T.Iterator.Element: Equatable, T.Iterator.Element == U.Iterator.Element {
            var common: [T.Iterator.Element] = []
            
            for lhsItem in lhs {
                for rhsItem in rhs where lhsItem == rhsItem {
                    common.append(lhsItem)
                }
            }
            return common
    }
    
    func resizeImage(image: UIImage, newWidth: CGFloat) -> UIImage {
        let scale = newWidth / image.size.width
        let newHeight = image.size.height * scale
        
        UIGraphicsBeginImageContext(CGSize(width: newWidth, height: newHeight))
        
        image.draw(in: CGRect(x: 0, y: 0, width: newWidth, height: newHeight))
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        return newImage!
    }
    
    func impactFeedback(with level: Vibrations) {
        if #available(iOS 10.0, *) {
            switch level {
            case .success:
                UINotificationFeedbackGenerator().notificationOccurred(.success)
            case .warning:
                UINotificationFeedbackGenerator().notificationOccurred(.warning)
            case .error:
                UINotificationFeedbackGenerator().notificationOccurred(.error)
            case .light:
                UIImpactFeedbackGenerator(style: .light).impactOccurred()
            case .medium:
                UIImpactFeedbackGenerator(style: .medium).impactOccurred()
            case .heavy:
                UIImpactFeedbackGenerator(style: .heavy).impactOccurred()
            }
        } else {
            AudioServicesPlaySystemSound(SystemSoundID(level.rawValue))
        }
    }
}
