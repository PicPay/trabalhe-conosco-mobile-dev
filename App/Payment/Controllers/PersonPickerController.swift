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
        
        let _ = PicPayService().loadPersons { persons in
            if persons != nil {
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
        return collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath)
    }
    
    // MARK: Collection view delegate
    
    override func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        let person = persons[indexPath.row]
        
        DispatchQueue.global(qos: .background).async {
            let image: UIImage?
            
            do {
                image = UIImage(data: try Data(contentsOf: person.photoURL))
            } catch {
                image = UIImage(named: "Placeholder")
            }
            
            DispatchQueue.main.async {
                let imageView = cell.contentView.viewWithTag(1) as? UIImageView
                imageView?.image = image
            }
        }
        
        let titleLabel = cell.contentView.viewWithTag(2) as? UILabel
        titleLabel?.text = person.name
        
        let subtitleLabel = cell.contentView.viewWithTag(3) as? UILabel
        subtitleLabel?.text = person.username
    }
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        delegate?.personPickerController(self, didPickPerson: persons[indexPath.row])
    }
    
    override func collectionView(_ collectionView: UICollectionView, didEndDisplaying cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        let imageView = cell.contentView.viewWithTag(1) as? UIImageView
        imageView?.image = UIImage(named: "Placeholder")
    }
}
