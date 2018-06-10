//
//  PersonPickerController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 09/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

protocol PersonPickerControllerDelegate: class {
    func personPickerController(_ picker: PersonPickerController, didPickPerson person: Person)
    func personPickerDidCancel(_ picker: PersonPickerController)
}

private let reuseIdentifier = "cell"

class PersonPickerController: UICollectionViewController {

    var displayedPersons: [Person]?
    
    weak var delegate: PersonPickerControllerDelegate?
    
    @IBAction func cancel(_ sender: UIBarButtonItem) {
        delegate?.personPickerDidCancel(self)
    }
    
    // MARK: UICollectionViewDataSource
    
    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return displayedPersons?.count ?? 0
    }
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        return collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath) as! PersonView
    }
    
    // MARK: UICollectionViewDelegate
    
    override func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        
        let cell = cell as! PersonView
        let person = displayedPersons![indexPath.row]
        cell.titleLabel.text = person.name
        cell.subtitleLabel.text = person.username
        let client = WebClient(baseUrl: person.photoURL.deletingLastPathComponent())
        let _ = client.request(path: person.photoURL.lastPathComponent, body: nil) { data, error in
            DispatchQueue.main.async {
                if let imageData = data {
                    cell.imageView.image = UIImage(data: imageData)
                } else {
                    cell.imageView.image = nil
                }
            }
        }
    }
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        delegate?.personPickerController(self, didPickPerson: displayedPersons![indexPath.row])
    }
}
