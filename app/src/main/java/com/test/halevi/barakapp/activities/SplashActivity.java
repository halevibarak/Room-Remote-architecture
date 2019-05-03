package com.test.halevi.barakapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.test.halevi.barakapp.R;
import com.test.halevi.barakapp.application.AppController;
import com.test.halevi.barakapp.model.Contact;
import com.test.halevi.barakapp.sqlite.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Barak on 24/08/2017.
 */
public class SplashActivity extends AppCompatActivity {


    private static final String URL_JSON = "https://api.androidhive.info/contacts/";
    private static final String TAG = "TAG";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String ID = "id";
    private static final String CONTACTS = "contacts";
    private static final String MOBILE = "mobile";
    private DatabaseHandler mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mDataBase = new DatabaseHandler(this);

        makeJsonArrayRequest();
        final Intent mainActivityIntent = new Intent(this, MainActivity.class);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startActivity(mainActivityIntent);
                finish();
            }
        }, 2000);

    }

    private void makeJsonArrayRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL_JSON, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has(CONTACTS)){
                        JSONArray array = response.getJSONArray(CONTACTS);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jObject = (JSONObject) array.get(i);
                            if (jObject.has(NAME)  && jObject.has(ID) && jObject.has(PHONE) && jObject.getJSONObject(PHONE).has(MOBILE)){
                                Contact contact = new Contact(jObject.getString(ID),jObject.getString(NAME),jObject.getJSONObject(PHONE).getString(MOBILE));
                            mDataBase.addContact(contact);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

}
