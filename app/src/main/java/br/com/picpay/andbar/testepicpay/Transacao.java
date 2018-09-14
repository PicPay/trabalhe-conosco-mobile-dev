package br.com.picpay.andbar.testepicpay;

/**
 * Created by andba on 22/04/2018.
 */

public class Transacao
{
    public static Transacao TransacaoSingleton;

    public double Valor;
    public CartaoUsuario Cartao;
    public int Destinatario;
    public String RespostaProcessamento;

}
