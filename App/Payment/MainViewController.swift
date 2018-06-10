//
//  MainViewController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 08/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

class MainViewController: UITableViewController {

    var persons = [Person]()
    
    var pickedPerson: Person?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        clearsSelectionOnViewWillAppear = false
        
        let _ = PicPayService().loadPersons { persons, error in
            if error == nil {
                self.persons = persons!
            }
        }
    }
    
    // MARK: - Navigation
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let navigationController = segue.destination as? UINavigationController,
            let picker = navigationController.viewControllers.first as? PersonPickerController {
            picker.displayedPersons = persons
            picker.delegate = self
        }
    }
    
    // MARK: UITableViewDelegate
    
    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        guard indexPath.section == 0 else {
            return
        }
        
        if let person = pickedPerson {
            cell.textLabel?.text = person.name
            cell.textLabel?.textColor = .darkText
            cell.detailTextLabel?.text = person.username
            cell.detailTextLabel?.textColor = .darkText
        } else {
            cell.textLabel?.text = "Pessoa"
            cell.textLabel?.textColor = .lightGray
            cell.detailTextLabel?.text = nil
        }
    }
}

extension MainViewController: PersonPickerControllerDelegate {
    func personPickerDidCancel(_ picker: PersonPickerController) {
        tableView.deselectRow(at: IndexPath(row: 0, section: 0), animated: true)
        picker.dismiss(animated: true)
    }
    
    func personPickerController(_ picker: PersonPickerController, didPickPerson person: Person) {
        pickedPerson = person
        tableView.reloadData()
        picker.dismiss(animated: true)
    }
}
