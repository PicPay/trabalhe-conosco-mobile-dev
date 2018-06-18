package com.example.eduardo.demoapppagamento.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.eduardo.demoapppagamento.data.Contact;
import com.example.eduardo.demoapppagamento.data.source.ContactsDataSource;
import com.example.eduardo.demoapppagamento.util.AppSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactsRemoteDataSource implements ContactsDataSource {

    private static ContactsRemoteDataSource INSTANCE = null;
    RequestQueue queue;


    public static ContactsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ContactsRemoteDataSource();
            INSTANCE.queue = Volley.newRequestQueue(AppSingleton.getContext());
        }
        return INSTANCE;
    }

    @Override
    public void getContacts(@NonNull final LoadContactsCallback callback) {
        //checkNotNull(callback);

        /*contacts.add(new Contact(100,"Antonio da Silva","img.jpg","@antonio.silva"));
        contacts.add(new Contact(101,"Maria da Silva","img.jpg","@maria.silva"));
        contacts.add(new Contact(102,"Jose da Silva","img.jpg","@jose.silva"));
        contacts.add(new Contact(103,"Pedro da Silva","img.jpg","@pedro.silva"));
        */

        String url = "http://careers.picpay.com/tests/mobdev/users";

        RequestQueue queue = Volley.newRequestQueue(AppSingleton.getContext());  // this = context
        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        //Log.d("Response", response.toString());

                        /*try {
                            Thread.sleep(3000);
                        } catch (Exception e){

                        }*/
                        callback.onContactsLoaded(parseContacts(response));
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());

                        List<Contact> contacts = new ArrayList<Contact>();
                        callback.onContactsLoaded(contacts);
                    }
                }
        );

        queue.add(getRequest);
    }

    private List<Contact> parseContacts(JSONArray response){
        List<Contact> contacts = new ArrayList<Contact>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String img = jsonObject.getString("img");
                String username = jsonObject.getString("username");
                //Log.d("--->", name );
                contacts.add(new Contact(id, name, img, username));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contacts;
    }

    /*
    private static void requestImage(String url, Bitmap img) {
        ImageRequest request = new ImageRequest(url,
            new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    img = bitmap;
                }
            }, 0, 0, null,
            new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {

                }
            });
    }
*/


}
