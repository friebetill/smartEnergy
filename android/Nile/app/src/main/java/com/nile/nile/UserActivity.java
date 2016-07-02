package com.nile.nile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.firebase.iid.FirebaseInstanceId;



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

        String json =
                "["
                + "{"
                +"    \"id\": 1,"
                +"    \"deliverer\": {"
                +"      \"id\": 1,"
                +"      \"name\": \"KarlsruheUser\","
                +"      \"created_at\": \"2016-07-02T14:18:54.855487Z\","
                +"      \"updated_at\": \"2016-07-02T14:41:35.280191Z\""
                +"    },"
                +"    \"purchaser\": {"
                +"      \"id\": 4,"
                +"      \"name\": \"DHL\","
                +"      \"created_at\": \"2016-07-02T16:17:35.245816Z\","
                +"      \"updated_at\": \"2016-07-02T16:17:35.246141Z\""
                +"    },"
                +"    \"recipient\": null,"
                +"    \"status\": \"open\","
                +"    \"created_at\": \"2016-07-02T16:29:13.824609Z\","
                +"    \"updated_at\": \"2016-07-02T16:29:13.824686Z\""
                +  "}"
                +"]";

        int userID = 4;

        //String orderedItems = executeGET("http://54.93.34.46/users/" + userID + "/packages/", "");
        new executeGETThread().execute();

        //NilePackage[] packages = new Gson().fromJson(orderedItems, NilePackage[].class);
        /*NilePackage[] packages = new Gson().fromJson(json, NilePackage[].class);

        for(int i = 0; i < packages.length; i++){
            TextView textView = new TextView(this);
            textView.setTextSize(25);
            textView.setText(packages[i].getSender() + " " + (int) packages[i].getEstimatedDeliveryTime() + "m");

            layout.addView(textView);
        }

        Log.d("TOKEN",FirebaseInstanceId.getInstance().getToken());*/

    }

    public String excutePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String executeGET(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        //CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ) );
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
        }
    }

}
