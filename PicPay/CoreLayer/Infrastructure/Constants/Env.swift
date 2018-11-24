//
//  Env.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

struct Env {
    //Storyboards
    struct Storyboards {
        static let main: String = "Main"
    }
    
    struct System {
        static let name: String = "PicPay"
        static let appVersion: String = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String ?? ""
        static let sysVersion: String = UIDevice.current.systemVersion
        static let slug: String = "PicPay"
        static let build: String = Bundle.main.infoDictionary?["CFBundleVersion"] as? String ?? ""
        static let appID: String = "6512bd43d9caa6e02c990b0a82652fr"
        static let url: String = "http://project-site.com"
        static let contact: String = "me@marcelo.cc"
        
        /*
         * Todo tratamento de erro do projeto esta sendo jogado para o console através da função dd().
         * Esta funcão pode ser habilitada/desabilitada editando o scheme, na aba
         * Arguments > Envirenment Variables alterando a variável DEVELOPER_MODE para true
         */
        static let isDeveloperModeEnabled: Bool = (ProcessInfo.processInfo.environment["DEVELOPER_MODE"] == "true")
    }
    
    struct Palette {
        static let white: String = "#FFFFFF"
        static let black: String = "#000000"
        static let yellow: String = "#FFE001"
        static let green: String = "#00F900"
    }
    
    struct Fonts {
        static let main: String = "HelveticaNeue"
    }
}
