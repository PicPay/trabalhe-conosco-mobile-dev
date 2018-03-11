//
//  ClientesTableViewController.swift
//  PicPay
//
//  Created by Vinicius Alves on 09/03/2018.
//  Copyright © 2018 TIPiniquim. All rights reserved.
//

import UIKit

class ClientesTableViewController: UITableViewController {

    var clientes: [Cliente] = []
    var cliente: Cliente?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        REST.loadClientes(onComplete: { (clientes) in
            
            self.clientes = clientes
            
            //Rodar na main thread
            DispatchQueue.main.async {
                self.tableView.reloadData()
            }
        }) { (error) in
                switch error {
            case .invalidJson:
                return print("invalidJson")
            case .url:
                return print("erro URL")
            case .taskError(_):
                return print("task error")
            case .noResponse:
                return print("noResponse")
            case .noData:
                return print("noData")
            case .responseStatusCode(let code):
                return print("responseStatusCode: ", code)
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return clientes.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)

        let cliente = clientes[indexPath.row]
        cell.textLabel?.text = cliente.name

        return cell
    }
 
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        cliente = clientes[indexPath.row]
        
        performSegue(withIdentifier: "segueVoltar", sender: self)
    }
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if segue.identifier == "segueVoltar" {
            let destino = segue.destination as! ViewController
            //destino.cliente.identificador = 2
            destino.cliente.id = cliente!.id
            destino.cliente.img = cliente!.img
            destino.cliente.name = cliente!.name
            destino.cliente.username = cliente!.username
        }
    }
}
