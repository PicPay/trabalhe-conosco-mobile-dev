// A camada Provider é responsável pela abstração das camadas da aplicação com acesso as fontes de dados e bibliotecas terceiras. 
// Ela deve sempre retornar tipos de "dados primitivos" (por ex. NSDictionary) e ser exclusivamente acessada pela camada Business.
// Por ser baseada no design pattern Facade, que visa garantir a flexibilidade do código, 
// devemos ter um provider por propósito com assinaturas de métodos genéricos. Dessa forma, 
// caso seja necessário substituirmos uma biblioteca por outra, o impacto será mínimo. 
// Afinal, as demais camadas permanecerão invocando o método com a mesma assinatura.
// Outra vantagem dessa camada é o design pattern Strategy, onde podemos utilizar o provider para acessar 
// recursos de mais de uma biblioteca e expormos um método genérico para camada acima (Business).

// Provider (internal struct)
//	○ Camada exclusivamente invocada pela Business
//	○ Sempre retorna "dados primitivos" para camada invocadora
//	○ Possui metódos genéricos para abstrair as fontes de dados (DB, API, Backend...)

// Nomeclatura dos arquivos:
// [NomeDaClass]Provider.swift
