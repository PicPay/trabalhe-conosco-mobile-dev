package gilianmarques.dev.picpay_test.asyncs;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.utils.ProfilePicHolder;


public class ContactsDownloaderAsync extends AsyncTask<Void, Void, Void> {

    private ContactsCallback callback;
    private final String CONTACT_LIST_URL = "http://careers.picpay.com/tests/mobdev/users";


    public ContactsDownloaderAsync(@NonNull ContactsCallback callback) {
        this.callback = callback;
    }

    /**
     * @param voids null
     *              <p>
     *              chama métodos chave para baixar e converter o json em um {@link ArrayList}
     */
    @Override
    protected Void doInBackground(Void... voids) {

        String json = getJson();

        if (json == null)
            callback.resut(null, 0);
        else try {
            createArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.resut(null, 1);
        }


        return null;
    }



    @Nullable
    private String getJson() {
        /*ultilizo instancias de HttpURLConnection + InputStreamReader + BufferedReader para obter o Json da nuvem*/

        HttpURLConnection urlConnection = null;
        StringBuilder mStringBuilder = new StringBuilder();

        try {
            URL url = new URL(CONTACT_LIST_URL);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream mInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader mReader = new BufferedReader(new InputStreamReader(mInputStream));

            String line;
            while ((line = mReader.readLine()) != null) {
                mStringBuilder.append(line);
            }

            return mStringBuilder.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
    }

    /**
     * @param json os contatos baixados da nuvem
     *             <p>
     *             Retorna atraves de um callback um {@link ArrayList} com os contatos obtidos do Json
     */
    private void createArray(@NonNull String json) throws JSONException {
        final List<Contact> mContacts = new ArrayList<>();
        final JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject mObject = jsonArray.getJSONObject(i);

            final Contact mContact = new Contact();
            mContact.setName(mObject.getString("name"));
            mContact.setPhoto(mObject.getString("img"));
            mContact.setUserName(mObject.getString("username"));
            mContact.setId(mObject.getInt("id"));

            /*evita que as imagens sejam baixadas ao carregar os dados do usuario no recyclerview VEJA a classe Contact*/
            final int finalI = i;
            new RoundImageAsync(new RoundImageAsync.Callback() {
                @Override
                public void result(Drawable mDrawable) {
                    ProfilePicHolder.getInstance().addPic(mContact.getPhoto(),mDrawable);
                    /*chama o callback apenas quando a ultima foto é carregada*/
                 //   if (finalI == jsonArray.length() - 1) callback.resut(mContacts, -1);
                }
            }).execute(mContact.getPhoto());

            mContacts.add(mContact);
        }

        callback.resut(mContacts, -1);
    }

    /**
     * Interface usada para reotrnar os valores obtidos
     * <p>
     * --Criada internamente para não inflar o projeto com arquivos não genéricos
     */
    public interface ContactsCallback {
        void resut(List<Contact> mContacts, int errorCode);
    }


}
