// A camada View Controller atua como uma intermediária entre as camadas View e Manager. 
// ViewControllers são, portanto, um canal responsável por notificar 
// as Views sobre eventuais mudanças de estados dos modelos e vice­versa.
// Nessa camada também encontraremos as tratativas dos eventos, tais como: IBActions e Delegates / Datasources*.

// * Protocolos deverão ser implementados em extensions.

// Obs. Acesso direto aos componentes UI devem ser evitados. Para isso devemos utilizar a camada View.

// View Controller
//	○ Realiza as tratativas dos eventos, tais como:
//		■ IBActions
//		■ Delegates (*)
//	○ Intermedia e notifica as camadas View e Manager sobre alterações nos modelos

// Nomeclatura dos arquivos:
// [NomeDaClass].swift
