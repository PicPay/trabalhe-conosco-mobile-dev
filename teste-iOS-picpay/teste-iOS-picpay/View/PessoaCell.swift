//
//  PessoaCell.swift
//  teste-iOS-picpay
//
//  Created by Bruno Lopes de Mello on 18/11/18.
//  Copyright © 2018 Bruno Lopes de Mello. All rights reserved.
//

import UIKit

class PessoaCell: UITableViewCell {

    @IBOutlet weak var nomeLabel: UILabel!
    @IBOutlet weak var idLabel: UILabel!
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var imagem: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func preencheCelula(_ pessoa: PessoasRetornoElement) {
        nomeLabel.text = pessoa.name
        usernameLabel.text = pessoa.username
        idLabel.text = "\(pessoa.id ?? 0000)"
        
        let url = URL(string: pessoa.img ?? "")
        if let data = try? Data(contentsOf: url!)
        {
            let image: UIImage = UIImage(data: data)!
            self.imagem.image = image
        }
        
//        DispatchQueue.global(qos: .background).async {
//            do
//            {
//                let data = try Data.init(contentsOf: URL.init(string:pessoa.img ?? "")!)
//                DispatchQueue.main.async {
//                    let image: UIImage = UIImage(data: data)!
//                    self.imagem.image = image
//                }
//            }
//            catch {
//                // error
//            }
//        }
    }
}
