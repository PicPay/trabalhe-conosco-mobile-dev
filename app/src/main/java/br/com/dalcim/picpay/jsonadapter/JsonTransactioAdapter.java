package br.com.dalcim.picpay.jsonadapter;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import br.com.dalcim.picpay.data.Transaction;
import br.com.dalcim.picpay.data.User;

public class JsonTransactioAdapter implements JsonDeserializer<Transaction>, JsonSerializer<Transaction> {
    @Override
    public Transaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        obj = obj.getAsJsonObject("transaction");

        Transaction transaction = new Transaction();

        transaction.setId(obj.get("id").getAsLong());
        transaction.setTimestamp(obj.get("timestamp").getAsLong());
        transaction.setValue(obj.get("value").getAsDouble());
        transaction.setDestinationUser(new Gson().fromJson(obj.get("destination_user"), User.class));
        transaction.setSuccess(obj.get("success").getAsBoolean());
        transaction.setStatus(obj.get("status").getAsString());

        return transaction;
    }

    @Override
    public JsonElement serialize(Transaction transaction, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", transaction.getId());
        jsonObject.addProperty("timestamp", transaction.getTimestamp());
        jsonObject.addProperty("value", transaction.getValue());
        jsonObject.addProperty("destination_user", new Gson().toJson(transaction.getDestinationUser()));
        jsonObject.addProperty("success", transaction.isSuccess());
        jsonObject.addProperty("status", transaction.getStatus());

        return jsonObject;
    }
}
