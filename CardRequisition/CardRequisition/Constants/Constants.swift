//
//  Constants.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/17/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//
import UIKit.UIImage

struct AlamofireConstants {
    static let baseUrl = "http://careers.picpay.com/tests/mobdev/"
    static let getUsersUrl = "users/"
    static let postPayment = "transaction/"
    static let success = "success"
    static let transaction = "transaction"
}

struct UserKeys {
    static let id = "id"
    static let imageUrl = "img"
    static let name = "name"
    static let username = "username"
}

struct TableViewCells {
    static let usersCell = "usersCell"
}

struct StoryboardName {
    static let payment = "Payment"
}

struct ViewControllerName {
    static let cardSelection = "CardSelectionViewController"
    static let addCard = "AddCardViewController"
    static let cvv = "CvvViewController"
}


struct CardCoreDataKeys {
    static let cardNumber = "cardNumber"
    static let expiryDate = "expiryDate"
    static let entityName = "CardCoreData"
}

struct PaymentParams {
    static let cardNumber = "card_number"
    static let cvv = "cvv"
    static let value = "value"
    static let expiryDate = "expiry_date"
    static let destinationUserId = "destination_user_id"
}

struct ImageConstants {
    static let cvvImage = UIImage(named: "icon_cvv")
    static let exitImage = UIImage(named: "icon_exit")
    static let profilePlaceholderImage = UIImage(named: "icon_profile_placeholder")
}

struct NotificationKeys {
    static let userId = "userId"
}
