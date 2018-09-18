//
//  CardCoreData.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/17/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//

import CoreData

@objc(CardCoreData)
class CardCoreData : NSManagedObject {
    @NSManaged public var cardNumber: String
    @NSManaged public var expiryDate: String
}
