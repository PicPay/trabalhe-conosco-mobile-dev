package stolato.com.br.paypalpayment.Model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by luiz on 19/06/17.
 */

public class ClientesDes implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement cliente = json.getAsJsonObject();
        return (new Gson().fromJson(cliente, Cliente.class));
    }
}
