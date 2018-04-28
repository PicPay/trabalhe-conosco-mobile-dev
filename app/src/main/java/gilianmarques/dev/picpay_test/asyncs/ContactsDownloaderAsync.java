package gilianmarques.dev.picpay_test.asyncs;

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


public class ContactsDownloaderAsync extends AsyncTask<Void, Void, Void> {

    private final ContactsCallback callback;

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

        String json = downloadJson();

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
    private String downloadJson() {
        /*ultilizo instancias de HttpURLConnection + InputStreamReader + BufferedReader para obter o Json da nuvem*/

        HttpURLConnection urlConnection = null;
        StringBuilder mStringBuilder = new StringBuilder();

        try {
            final String URL = "http://careers.picpay.com/tests/mobdev/users";
            urlConnection = (HttpURLConnection) new URL(URL).openConnection();

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

            final int finalI = i;
            ProfilePictureUtils.SaveCallback mCallback = new ProfilePictureUtils.SaveCallback() {
                @Override
                public void done() {
                    /*chama o callback apenas quando a ultima foto é cacheada*/
                    if (finalI == jsonArray.length() - 1) callback.resut(mContacts, -1);
                }
            };

            new ProfilePictureUtils(mContact).saveProfilePic(mCallback);
            mContacts.add(mContact);
        }
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
