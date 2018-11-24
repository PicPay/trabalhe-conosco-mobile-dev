// A camada Business deverá conter estruturas internas responsáveis por concentrar todas as regras de negócio. 
// Ela deve ser exclusivamente acessada pela camada Manager e invocar a camada Provider (quando necessário).
// Essa camada também será responsável por receber "dados primitivos" (por ex. NSDictionary) 
// e convertê­los para modelos antes de retorná­los para a camada Manager..
// Por fim, (caso necessário) essa camada poderá invocar outros objetos de negócio.
// Obs. Utilizamos apenas um business por manager.

// Business (internal struct)
//    ○ Concentra as regras de negócio
//    ○ Um Manager deve ter apenas um Business
//    ○ Camada exclusivamente invocada pela Manager
//    ○ Coordena chamadas para outros objetos de negócio (se necessário)
//    ○ Os testes unitários serão eventualmente realizados nos métodos dessa
//    camada
//    ○ Recebe "dados primitivos" (NSDictionary) e os converte para modelos antes de
//    repassá-los para camada invocadora

// Nomeclatura dos arquivos:
// [NomeDaClass]Business.swift
