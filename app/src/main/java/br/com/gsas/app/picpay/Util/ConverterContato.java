package br.com.gsas.app.picpay.Util;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import br.com.gsas.app.picpay.Domain.Contato;

public class ConverterContato {

    @TypeConverter
    public static Contato fromString(String value) {

        return new Gson().fromJson(value, Contato.class);
    }

    @TypeConverter
    public static String fromContato(Contato contato) {

        Gson gson = new Gson();
        return gson.toJson(contato);
    }
}
