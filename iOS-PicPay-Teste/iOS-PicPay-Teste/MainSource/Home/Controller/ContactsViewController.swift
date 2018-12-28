//
//  ViewController.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 03/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class ContactsViewController: BaseTableViewController<MainListContactsCell, ContatosViewModel>, CheckViewControllerProtocol {
    
    
    //MARK:- Call Child CheckingViewController
    func didPaymentSuccess(with ticketUser: TicketUser) {
        let viewc = CheckingViewController()
        viewc.ticket = ticketUser
        viewc.modalPresentationStyle = .overFullScreen
        self.present(viewc, animated: true, completion: nil)
    }
    
    
    //Replace the color of next viewcontroller and change he's color to green
    override func viewWillDisappear(_ animated: Bool) {
        navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .plain, target: nil, action: nil)
        navigationItem.backBarButtonItem?.tintColor = .lightGreen
        self.navigationController?.navigationBar.isHidden = false
    }
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.isHidden = true
    }
    
    
    //Constants
    let searchBar: SearchBarView = {
        let search = SearchBarView()
        search.textField.addTarget(self, action: #selector(handlerChangedValue(_:)), for: .editingChanged)
        search.sizeToFit()
        return search
    }()
    
    let searchBarOnTop: SearchBarView = {
        let search = SearchBarView()
        search.textField.addTarget(self, action: #selector(handlerChangedValue(_:)), for: .editingChanged)
        search.sizeToFit()
        return search
    }()
    var filtered: [ContatosViewModel] = []
    
    let titleViewc: CustomNavView = {
        let view = CustomNavView()
        return view
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configureViewController()
        configureSearchDelegate()
        FetchContacts()
        configureTopSearchBar()
        configureTableView()
        configureSelections()
    }
    
    
    //MARK:- Download Main Contacts
    fileprivate func FetchContacts() {
        let url = BASEURL + LIST_USERS
        ContactsFetch.shared.FetchData(url) { (feed: Array<Contato>) in
            let contacts = feed.map( {return ContatosViewModel(contato: $0)} )
            self.items = contacts.sorted(by: { $0.name < $1.name })
            self.filtered = self.items
            self.tableView.reloadData()
            self.tableView.refreshControl?.endRefreshing()
        }
    }
    
    
    
    //MARK:- Configuration of ViewController
    fileprivate func configureTableView() {
        self.navigationItem.titleView = UIView()
        self.view.backgroundColor = .strongBlack
        self.navigationController?.navigationBar.prefersLargeTitles = true
        self.navigationController?.navigationItem.hidesSearchBarWhenScrolling = true
        self.navigationController?.navigationBar.shadowImage = UIImage()
        self.navigationController?.navigationBar.isTranslucent = false
        self.navigationController?.navigationBar.largeTitleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.white]
        self.navigationItem.largeTitleDisplayMode = .automatic
        self.navigationController?.navigationBar.barTintColor = .strongBlack
        self.navigationController?.navigationBar.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.white]
    }
    
    
    fileprivate func configureViewController() {
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.tableView.keyboardDismissMode = .onDrag
        self.view.addSubview(titleViewc)
    }
    
    //MARK:- Selections
    fileprivate func configureSelections() {
        self.didSelect = { contato in
            let ctc = Contato(contato)
            self.pushViewController(ctc)
        }
    }
    //Concigure the static searchbar on top
    fileprivate func configureTopSearchBar() {
        self.view.addSubview(self.tableView)
        self.tableView.fillSuperview()
        self.tableView.contentInset = UIEdgeInsets(top: 64, left: 0, bottom: 0, right: 0)
        
        self.view.addSubview(self.searchBarOnTop)
        self.searchBarOnTop.anchor(top: self.view.layoutMarginsGuide.topAnchor, leading: self.view.leadingAnchor, bottom: nil, trailing: self.view.trailingAnchor, padding: .init(), size: .init(width: self.view.frame.width, height: 60))
        
        
        guard let statusBarView = UIApplication.shared.value(forKeyPath: "statusBarWindow.statusBar") as? UIView else {
            return
        }
        statusBarView.backgroundColor = UIColor.strongBlack
    }
    
    
    //MARK:- Handlers
    //Custom SearchBar valueChanged
    @objc fileprivate func handlerChangedValue(_ textField: UITextField) {
        let str = textField.text!
        filterContentForSearchText(str)
    }
    
    
    //RefreshControll TableView
    @objc override func handleRefresh() {
        FetchContacts()
    }
    
    
    //MARK:- Change Direction Controller
    fileprivate func pushViewController(_ contato: Contato) {
        let cred = CredCoreData()
        if cred.FetchRequest()?.numero != nil {
            let viewc = PaymentViewController()
            viewc.contato = contato
            viewc.delegate = self
            self.navigationController?.pushViewController(viewc, animated: true)
        } else {
            let viewc = PrimingController()
            viewc.contato = contato
            viewc.delegate = self
            self.navigationController?.pushViewController(viewc, animated: true)
        }
    }
    
    
    
    //MARK:- TableView Functions
    override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 60
    }
    override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return searchBar
    }
    
    
    // MARK: - UISearchResultsUpdating Delegate
    internal func configureSearchDelegate() {
        self.searchBar.textField.delegate = self
        searchBar.frame = CGRect(x: 0, y: 0, width: self.view.frame.width, height: 55)
        definesPresentationContext = true
    }
    
    
    // MARK: - Private instance methods
    fileprivate func filterContentForSearchText(_ searchText: String) {
        self.items = self.filtered.filter({( contato : ContatosViewModel) -> Bool in
            if searchText == "" {
                self.items = self.filtered
                return true
            } else {
                return true && (contato.name.lowercased().contains(searchText.lowercased()))
            }
        })
        self.tableView.reloadData()
    }
    
    
    override func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return true
    }
    override func textFieldDidBeginEditing(_ textField: UITextField) {
        self.searchBar.isEditing = true
        self.searchBarOnTop.isEditing = true
    }
    override func textFieldDidEndEditing(_ textField: UITextField) {
        self.searchBar.isEditing = false
        self.searchBarOnTop.isEditing = false
    }

    
    override func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let offset = scrollView.contentOffset.y
        if(offset > 17.0) {
            hiddenTopNavbar(false)
        } else {
            hiddenTopNavbar(true)
        }
        
        if(offset > 200){
            self.titleViewc.frame = CGRect(x: 20, y: 0, width: self.view.bounds.size.width, height: 0)
        } else {
            self.titleViewc.frame = CGRect(x: 20, y: 0, width: self.view.bounds.size.width, height: 95 - offset)
        }
    }
    
    
    
    fileprivate func hiddenTopNavbar(_ bool: Bool) {
        if self.searchBarOnTop.isHidden, bool {
            self.searchBarOnTop.isHidden = bool
            self.searchBarOnTop.textField.text = self.searchBar.textField.text
        } else {
            self.searchBarOnTop.isHidden = bool
            self.searchBar.textField.text = self.searchBarOnTop.textField.text
        }
    }
}

