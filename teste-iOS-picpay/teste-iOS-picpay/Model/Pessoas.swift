// To parse the JSON, add this file to your project and do:
//
//   let pessoasRetorno = try? newJSONDecoder().decode(PessoasRetorno.self, from: jsonData)
//
// To read values from URLs:
//
//   let task = URLSession.shared.pessoasRetornoTask(with: url) { pessoasRetorno, response, error in
//     if let pessoasRetorno = pessoasRetorno {
//       ...
//     }
//   }
//   task.resume()

import Foundation

typealias PessoasRetorno = [PessoasRetornoElement]

struct PessoasRetornoElement: Codable {
    let id: Int?
    let name: String?
    let img: String?
    let username: String?
}

// MARK: - URLSession response handlers

extension URLSession {
    fileprivate func codableTask<T: Codable>(with url: URL, completionHandler: @escaping (T?, URLResponse?, Error?) -> Void) -> URLSessionDataTask {
        return self.dataTask(with: url) { data, response, error in
            guard let data = data, error == nil else {
                completionHandler(nil, response, error)
                return
            }
            completionHandler(try? newJSONDecoder().decode(T.self, from: data), response, nil)
        }
    }
    
    func pessoasRetornoTask(with url: URL, completionHandler: @escaping (PessoasRetorno?, URLResponse?, Error?) -> Void) -> URLSessionDataTask {
        return self.codableTask(with: url, completionHandler: completionHandler)
    }
}
