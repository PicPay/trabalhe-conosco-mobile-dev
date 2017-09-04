package br.com.dalcim.picpay.jsonadapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import br.com.dalcim.picpay.data.Payment;

public class JsonPaymentAdapter implements JsonDeserializer<Payment>, JsonSerializer<Payment> {

    private JsonCreditCardAdapter jsonCreditCardAdapter = new JsonCreditCardAdapter();

    @Override
    public Payment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        Payment payment = new Payment();
        payment.setValue(obj.get("value").getAsDouble());
        payment.setDestinationUserId(obj.get("destination_user_id").getAsLong());
        payment.setCreditCard(jsonCreditCardAdapter.deserialize(json, typeOfT, context));

        return payment;
    }

    @Override
    public JsonElement serialize(Payment payment, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", payment.getValue());
        jsonObject.addProperty("destination_user_id", payment.getDestinationUserId());
        jsonCreditCardAdapter.populeJsonObject(jsonObject, payment.getCreditCard());

        return jsonObject;
    }
}
