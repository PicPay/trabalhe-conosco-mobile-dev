package br.com.dalcim.picpay.jsonadapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import br.com.dalcim.picpay.data.CreditCard;

/**
 * @author Wiliam
 * @since 30/08/2017
 */

public class JsonCreditCardAdapter implements JsonDeserializer<CreditCard>, JsonSerializer<CreditCard> {
    @Override
    public CreditCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        CreditCard creditCard = new CreditCard();


        creditCard.setCardNumber(obj.get("card_number").getAsString());
        creditCard.setCvv(obj.get("cvv").getAsInt());
        creditCard.setExpiryData(obj.get("expity_date").getAsString());

        return creditCard;
    }

    @Override
    public JsonElement serialize(CreditCard creditCard, Type typeOfSrc, JsonSerializationContext context) {
        return populeJsonObject(new JsonObject(), creditCard);
    }

    public JsonObject populeJsonObject(JsonObject jsonObject, CreditCard creditCard){
        jsonObject.addProperty("card_number", creditCard.getCardNumber());
        jsonObject.addProperty("cvv", creditCard.getCvv());
        jsonObject.addProperty("expity_date", creditCard.getExpiryData());

        return jsonObject;
    }
}
