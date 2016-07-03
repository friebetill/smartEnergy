package com.nile.nile;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.nile.nile.util.UrlBuilder;
import com.nile.nile.api.ApiCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserAddAddressActivity extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_address);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }


    public void addAddress(View view) {

        EditText etName = (EditText) findViewById(R.id.tvName);
        EditText etStreet = (EditText) findViewById(R.id.tvAddress);
        EditText etHouseNumber = (EditText) findViewById(R.id.tvHouseNumber);
        EditText etZipCode = (EditText) findViewById(R.id.tvZipCode);
        EditText etFloor = (EditText) findViewById(R.id.tvFloor);
        EditText etCity = (EditText) findViewById(R.id.tvCity);

        String name = etName.getText().toString();
        String street = etStreet.getText().toString();
        int houseNumber = Integer.parseInt(etHouseNumber.getText().toString());
        int zipCode = Integer.parseInt(etZipCode.getText().toString());
        int floor = Integer.parseInt(etFloor.getText().toString());
        String city = etCity.getText().toString();

        UrlBuilder url = new UrlBuilder();
        String apiUrl = url.constructApiUrl(street, houseNumber, zipCode, city);
        ApiCall request = new ApiCall();
        String response = request.getLocationData(apiUrl);
        try {
            JSONObject location = new JSONObject(response);
            Log.d("RESULT", location.toString());
            JSONObject jsonResponse = location.getJSONObject("Response");
            JSONArray viewArray = jsonResponse.getJSONArray("View");
            JSONObject result = viewArray.getJSONObject(0);
            JSONArray apiArray = result.getJSONArray("Result");
            JSONObject address = apiArray.getJSONObject(0);
            JSONObject locationAddress = address.getJSONObject("Location");
            JSONObject displayPosition = locationAddress.getJSONObject("DisplayPosition");
            JSONObject apiAddress = locationAddress.getJSONObject("Address");
            String latitude = displayPosition.getString("Latitude");
            String longitude = displayPosition.getString("Longitude");
            String postalCode = apiAddress.getString("PostalCode");
            String apiHouseNumber = apiAddress.getString("HouseNumber");
            String apiStreet = apiAddress.getString("Street");
            String apiCity = apiAddress.getString("City");

            Log.d("Latitude", latitude);
            Log.d("longitude", longitude);
            Log.d("zipcode", postalCode);
            Log.d("Street", apiStreet);
            Log.d("Number", apiHouseNumber);
            Log.d("City", apiCity);
            JSONObject jsonLocation = new JSONObject();
            jsonLocation.put("lat", latitude);
            jsonLocation.put("lng", longitude);
            JSONObject jsonObjectSend = new JSONObject();
            try {
                jsonObjectSend.put("name", name);
                jsonObjectSend.put("street", apiStreet + " " + apiHouseNumber);
                jsonObjectSend.put("postcode", postalCode);
                jsonObjectSend.put("city", apiCity);
                jsonObjectSend.put("country", "Germany");
                jsonObjectSend.put("floor", floor);
                jsonObjectSend.put("location", jsonLocation);
                SharedPreferences pref = this.getSharedPreferences("Share", Context.MODE_PRIVATE);
                int currentID = pref.getInt("currentID", -1);
                AsyncUserTask postAddress = new AsyncUserTask(currentID);
                postAddress.execute(String.valueOf(jsonObjectSend));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

    }

    private class AsyncUserTask extends AsyncTask<String, Void, String> {

        private String apiEndPoint = "http://54.93.34.46/users/";
        private int userID;

        public AsyncUserTask(int userID) {
            this.userID = userID;
        }


        @Override
        protected String doInBackground(String... params) {
            String jsonData = params[0];
            String jsonResponse = null;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(apiEndPoint + userID + "/addresses/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.connect();

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(jsonData);
                writer.close();

                int responseCode = urlConnection.getResponseCode();
                Log.d("URL CODE", "" + responseCode);

                InputStream inputStream;
                if(urlConnection.getResponseCode() / 100 == 2) {
                    inputStream = urlConnection.getInputStream();
                } else {
                    inputStream = urlConnection.getErrorStream();
                }
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                jsonResponse = buffer.toString();
                Log.i("JSON RESPONSE", jsonResponse);

                return jsonResponse;
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
                if(reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException ex ) {
                        Log.e("EXCEPTION", "Error closing stream", ex);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
