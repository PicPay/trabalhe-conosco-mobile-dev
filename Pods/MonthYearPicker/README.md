# MonthYearPicker

[![CI Status](http://img.shields.io/travis/alexanderedge/MonthYearPicker.svg?style=flat)](https://travis-ci.org/alexanderedge/MonthYearPicker)
[![Version](https://img.shields.io/cocoapods/v/MonthYearPicker.svg?style=flat)](http://cocoapods.org/pods/MonthYearPicker)
[![License](https://img.shields.io/cocoapods/l/MonthYearPicker.svg?style=flat)](http://cocoapods.org/pods/MonthYearPicker)
[![Platform](https://img.shields.io/cocoapods/p/MonthYearPicker.svg?style=flat)](http://cocoapods.org/pods/MonthYearPicker)

This is a `UIControl` subclass that allows date selection using month / year, unlike `UIDatePicker` which displays year, month, and day. This makes `MonthYearPicker` useful for credit card expiry dates, for example. It is locale-aware and shows localised values.

![Screenshot](https://raw.githubusercontent.com/alexanderedge/MonthYearPicker/master/screenshot.png)

## Usage

Initialise `MonthYearPicker` in the same way you would a `UIPickerView` instance.

```
let picker = MonthYearPickerView(frame: CGRect(origin: CGPoint(x: 0, y: (view.bounds.height - 216) / 2), size: CGSize(width: view.bounds.width, height: 216)))
picker.addTarget(self, action: #selector(dateChanged(_:)), for: .valueChanged)
view.addSubview(picker)
```

## Requirements

iOS 8.0 or later

## Installation

MonthYearPicker is available through [CocoaPods](http://cocoapods.org). To install
it, simply add the following line to your Podfile:

```ruby
pod "MonthYearPicker"
```

## Author

Alexander Edge, alex@alexedge.co.uk

## License

MonthYearPicker is available under the MIT license. See the LICENSE file for more info.
