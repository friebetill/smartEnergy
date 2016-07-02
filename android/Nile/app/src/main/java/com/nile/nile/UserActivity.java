package com.nile.nile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nile.nile.model.NilePackage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        String json =
            "[{"
                + "'id': '1',"
                + "'deliver_id' : 1,"
                + "'purchase_id' : '1',"
                + "'recipient_id' : '1',"
                + "'sender' : 'Amazon',"
                + "'status' : 'Open',"
                + "'estimatedDeliveryTime' : '15.0'"
                + "},{"
                + "'id': '1',"
                + "'deliver_id' : 1,"
                + "'purchase_id' : '1',"
                + "'recipient_id' : '1',"
                + "'sender' : 'Zalando',"
                + "'status' : 'Open',"
                + "'estimatedDeliveryTime' : '2.0'"
                + "}]";


        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout1);

        //String orderedItems = excutePost("", "");

        //NilePackage[] packages = new Gson().fromJson(orderedItems, NilePackage[].class);
        NilePackage[] packages = new Gson().fromJson(json, NilePackage[].class);

        for(int i = 0; i < packages.length; i++){
            TextView textView = new TextView(this);
            textView.setTextSize(25);
            textView.setText(packages[i].getSender() + " " + (int) packages[i].getEstimatedDeliveryTime() + "m");

            layout.addView(textView);
        }

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
}
