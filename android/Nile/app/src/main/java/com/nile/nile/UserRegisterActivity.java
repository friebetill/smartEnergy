package com.nile.nile;


import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;
import com.nile.nile.util.UrlBuilder;
import com.nile.nile.api.ApiCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class UserRegisterActivity extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        EditText etFirstName = (EditText) findViewById(R.id.tvFirstName);
        EditText etLastName = (EditText) findViewById(R.id.tvLastName);
        EditText etStreet = (EditText) findViewById(R.id.tvAddress);
        EditText etHouseNumber = (EditText) findViewById(R.id.tvHouseNumber);
        EditText etZipCode = (EditText) findViewById(R.id.tvZipCode);
        EditText etCity = (EditText) findViewById(R.id.tvCity);

        etFirstName.setText("Felix");
        etLastName.setText("BOenke");
        etStreet.setText("Detmolder Strasse");
        etHouseNumber.setText("62");
        etZipCode.setText("10715");
        etCity.setText("Berlin");

    }


    public void createUser(View view) {
        EditText etFirstName = (EditText) findViewById(R.id.tvFirstName);
        EditText etLastName = (EditText) findViewById(R.id.tvLastName);
        EditText etStreet = (EditText) findViewById(R.id.tvAddress);
        EditText etHouseNumber = (EditText) findViewById(R.id.tvHouseNumber);
        EditText etZipCode = (EditText) findViewById(R.id.tvZipCode);
        EditText etCity = (EditText) findViewById(R.id.tvCity);

        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String street = etStreet.getText().toString();
        int houseNumber = Integer.parseInt(etHouseNumber.getText().toString());
        int zipCode = Integer.parseInt(etZipCode.getText().toString());
        String city = etCity.getText().toString();

        UrlBuilder url = new UrlBuilder();
        String apiUrl = url.constructApiUrl(street, houseNumber, zipCode, city);
        ApiCall request = new ApiCall();
        String response = request.getLocationData(apiUrl);
        try {
            JSONObject location = new JSONObject(response);
            Log.d("RESULT", location.toString());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }


    }

}
