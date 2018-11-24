//
//  Api.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//
import Foundation

extension Env {
    struct Api {
        static var baseGithubProfile: String = "https://github.com/marceloreis13"
        static var baseLinkToApp: String = "https://itunes.apple.com/br/app/\(Env.System.slug)/id\(Env.System.appID)?mt=8"
        static var baseUrlSchemeToApp: String = "itms-apps://itunes.apple.com/app"
    }
}

struct Api {}
