package com.nile.nile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.nile.nile.model.NilePackage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout1);
        layout.removeAllViews();

        TextView orders = new TextView(this);
        orders.setTextSize(25);
        orders.setText("Orders");
        layout.addView(orders);

        new executeGETThread().execute();

    }

    private void updateOrders(String json) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout1);
        NilePackage[] packages = new Gson().fromJson(json, NilePackage[].class);

        for(int i = 0; i < packages.length; i++){
            TextView textView = new TextView(this);
            textView.setTextSize(25);
            textView.setText(packages[i].getSender() + " " + (int) packages[i].getMins_until_delivery() + "m");

            layout.addView(textView);
        }
    }

    public String executeGET(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(connection.getInputStream());
            String html = readStream(in);

            return html;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }
        is.close();
        return sb.toString();
    }

    class executeGETThread extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            int userID = 4;
            String orderedItems = executeGET("http://54.93.34.46/users/" + userID + "/packages/", "");
            return orderedItems;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result == null){
                Log.d("ExecuteThread", "Empty Message");
            } else {
                Log.d("ExecuteThread", result);
            }
            updateOrders(result);
        }
    }

}
