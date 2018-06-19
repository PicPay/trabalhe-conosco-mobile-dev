//
//  DatePickerButton.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 11/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

class DatePickerButton: UIButton {

    let pickerView = UIPickerView()
    
    let monthComponent: [String] = {
        let dateFormatter = DateFormatter()
        
        return (0..<12).map({ dateFormatter.monthSymbols[$0] })
    }()
    
    let yearComponent: [String] = (2000..<3000).map({ String($0) })
    
    override var canBecomeFirstResponder: Bool {
        return true
    }
    
    override var inputView: UIView? {
        pickerView.delegate = self
        pickerView.dataSource = self
        return pickerView
    }
}

extension DatePickerButton: UIPickerViewDataSource {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 2
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        switch component {
        case 0:
            return monthComponent.count
        case 1:
            return yearComponent.count
        default:
            return 0
        }
    }
}

extension DatePickerButton: UIPickerViewDelegate {
    func pickerView(_ pickerView: UIPickerView, widthForComponent component: Int) -> CGFloat {
        return pickerView.bounds.width/2.0
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        switch component {
        case 0:
            return monthComponent[row]
        case 1:
            return yearComponent[row]
        default:
            return nil
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        let i = pickerView.selectedRow(inComponent: 0)+1
        let j = pickerView.selectedRow(inComponent: 1)
        
        setTitle("\(String(format: "%02d", i))/\(yearComponent[j])", for: .normal)
    }
}
