//
//  UserCollectionView.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 17/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class UserCollectionView: UICollectionView, UICollectionViewDataSource, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout {
    
    var users = [User]()
    var usersDic = [[String : AnyObject]]()
    weak var viewController: UIViewController?
    
    let colorPicPay = UIColor(displayP3Red: 37.0/255.0, green: 198.0/255.0, blue: 115.0/255.0, alpha: 1.0)
    
    public override init(frame: CGRect, collectionViewLayout: UICollectionViewLayout) {
        
        super.init(frame: frame, collectionViewLayout: collectionViewLayout)
        delegate = self
        dataSource = self
        self.register(UserViewCollectionViewCell.self, forCellWithReuseIdentifier: "cellId")
        self.fetchUsers()
        autoresizingMask = [.flexibleWidth , .flexibleHeight]
        showsHorizontalScrollIndicator = false
        bounces = true
        backgroundColor = colorPicPay
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return users.count
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let screenWidth = self.frame.width / 4//CGFloat(users.count) * UIScreen.main.bounds.width
        let screenHeight = self.frame.height / 3
        return CGSize(width: screenWidth, height: screenHeight)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsetsMake(10, 20, 60, 20)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 20
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 20
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print("selecionou \(indexPath.row)")
        let user = users[indexPath.row]
        let payViewController = PayViewController()
        payViewController.user = user
        self.viewController?.present(payViewController, animated: true, completion: nil)
    }
    
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let user = users[indexPath.row]
        let userView = UserViewModel(user: user)
        let userCollectionCell = self.dequeueReusableCell(withReuseIdentifier: "cellId", for: indexPath) as! UserViewCollectionViewCell
        self.getImage(url: user.imageURL) { (image) in
            userCollectionCell.imageView.image = image
        }
        userCollectionCell.nameLabel.text = userView.username
        
        return userCollectionCell
    }
    
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

}
extension UserCollectionView {
    
    private func fetchUsers(){
        Alamofire.request("http://careers.picpay.com/tests/mobdev/users").responseJSON { (responseData) -> Void in
            if((responseData.result.value) != nil) {
                if let usersData = JSON(responseData.result.value!).arrayObject{
                    if let usersDictionary = usersData as? [[String: AnyObject]] {
                        self.usersDic = usersDictionary
                        self.dataFormUsers(usersDictionary: usersDictionary)
                    }
                }
            }
        }
    }
    
    private func dataFormUsers(usersDictionary: [[String : AnyObject]]){
        for userDictionary in usersDictionary {
            let name = userDictionary["name"] as! String
            let img = userDictionary["img"] as! String
            let id = userDictionary["id"] as! Int
            let username = userDictionary["username"] as! String
            
            let newUser = User(id: id, name: name, imageURL: URL(string: img)!, username: username)
            self.users.append(newUser)
        }
        self.reloadData()
    }
    
    public func getImage(url: URL, handler: @escaping ((UIImage) -> Void)) {
        URLSession.shared.dataTask(with: url) { data, response, error in
            guard
                let httpURLResponse = response as? HTTPURLResponse, httpURLResponse.statusCode == 200,
                let mimeType = response?.mimeType, mimeType.hasPrefix("image"),
                let data = data, error == nil,
                let image = UIImage(data: data)
                else { return }
            DispatchQueue.main.async() {
                handler(image)
            }
            }.resume()
    }
    
}
