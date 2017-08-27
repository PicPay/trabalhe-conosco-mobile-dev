package com.ghrc.picpay.deserialize;

import com.ghrc.picpay.model.User;
import com.google.gson.JsonDeserializationContext;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Guilherme on 25/08/2017.
 */

public class UserDes implements JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jso = json.getAsJsonObject();
        User user = new User();
        user.setName(jso.get("name").getAsString());
        user.setId(jso.get("id").getAsString());
        user.setImg(jso.get("img").getAsString());
        user.setUsername(jso.get("username").getAsString());
        return user;
    }
}
