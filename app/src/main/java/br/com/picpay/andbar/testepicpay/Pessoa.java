package br.com.picpay.andbar.testepicpay;

import android.graphics.Bitmap;

/**
 * Created by andba on 20/04/2018.
 */

public class Pessoa
{

    public  static Pessoa PessoaSingleton;

    Integer id;
    String name;
    String img;
    String username;
    Bitmap imagem;

    public Integer getId(){return this.id;}
    public void setId(Integer value){this.id = value;}

    public String getName(){return this.name;}
    public void setName(String value){this.name = value;}

    public String getImg(){return this.img;}
    public void setImg(String value){this.img = value;}

    public String getUsername(){return  this.username;}
    public void setUsername(String value){this.username = value;}

    public Bitmap getImagem(){return  this.imagem;}
    public void setImagem(Bitmap value){this.imagem = value;}

}
