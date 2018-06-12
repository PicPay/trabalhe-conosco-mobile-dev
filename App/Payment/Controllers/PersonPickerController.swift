//
//  PersonPickerController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 09/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

protocol PersonPickerControllerDelegate: class {
    func personPickerController(_ pickerController: PersonPickerController, didPickPerson person: Person)
    func personPickerControllerDidCancel(_ pickerController: PersonPickerController)
}

private let reuseIdentifier = "cell"

class PersonPickerController: UICollectionViewController {

    fileprivate var persons = [Person]()
    
    weak var delegate: PersonPickerControllerDelegate?
    
    @IBAction func cancel(_ sender: UIBarButtonItem) {
        delegate?.personPickerControllerDidCancel(self)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let _ = PicPayService().loadPersons { persons, error in
            if error == nil {
                self.persons = persons!
                
                DispatchQueue.main.async {
                    self.collectionView?.reloadData()
                }
            }
        }
    }
    
    // MARK: Collection view data source
    
    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return persons.count
    }
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        return collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath) as! PersonView
    }
    
    // MARK: Collection view delegate
    
    override func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        
        let cell = cell as! PersonView
        let person = persons[indexPath.row]
        cell.titleLabel.text = person.name
        cell.subtitleLabel.text = person.username
        let client = WebClient(baseUrl: person.photoURL.deletingLastPathComponent())
        let _ = client.request(path: person.photoURL.lastPathComponent, body: nil) { data, error in
            if error == nil {
                let image = UIImage(data: data!)
                
                DispatchQueue.main.async {
                    cell.imageView.image = image
                }
            }
        }
    }
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        delegate?.personPickerController(self, didPickPerson: persons[indexPath.row])
    }
}
