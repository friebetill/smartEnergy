package com.nile.nile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class DelivererRegisterActivity extends AppCompatActivity {

    private int delivererID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverer_register);
    }

    public void createDeliverer(View view) {
        EditText etName = (EditText) findViewById(R.id.etNameDeliverer);
        String name = etName.getText().toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("type", "deliverer");
            AsynDelivererTask task = new AsynDelivererTask(this);
            task.execute(String.valueOf(jsonObject));

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private class AsynDelivererTask extends AsyncTask<String, Void, String> {

        private final String apiEndPoint = "http://54.93.34.46/users/";
        private Context mContext;

        public AsynDelivererTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String jsonData = params[0];
            String jsonResponse = null;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(apiEndPoint);
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
            try {
                JSONObject deliverer = new JSONObject(s);
                try {
                    delivererID = deliverer.getInt("id");
                    SharedPreferences pref = mContext.getSharedPreferences("Share", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putInt("currentID", delivererID);
                    edit.putInt("isUser", 0);
                    edit.commit();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
}
