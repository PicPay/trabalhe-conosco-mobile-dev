// A camada Manager deverá conter classes públicas* responsáveis por controlar todo o fluxo de requisições 
// entre a camada View Controller e Business. Ela deve ser exclusivamente acessada pela camada View Controller 
// e seu retorno de dados sempre deverá ser realizado na thread principal.
// Essa camada pode ser, se necessário, uma instância da classe OperationQueue para controlar operações 
// assíncronas e o por poder facilitar o cancelamento ou pausa de uma operação. Ela também pode reestabelecer a 
// relação de dependência entre as operações.
// Obs. Utilizamos apenas um manager por view controller.

// *Esses objetos não deverão ser estruturas, pois suas propriedades poderão sofrer alterações

// Manager (public class)
//	○ Retorna os dados na thread principal
//	○ Um View Controller deve ter apenas um Manager
//	○ Camada exclusivamente invocada pela View Controller
//	○ Controla o fluxo das requisições entre a camada View Controller e Business
//	○ Usar OperationQueue para controlar operações assíncronas (se necessário)
//	○ Definida como class, e não struct, pois suas propriedades podem ser alteradas

// Nomeclatura dos arquivos:
// [NomeDaClass]Manager.swift
