package com.nile.nile.api;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Felix on 02.07.16.
 */
public class ApiCall {

    private final String geoCodeUrl = "https://geocoder.cit.api.here.com/6.2/geocode.json";
    // constructor
    public ApiCall() {

    }

    public String getLocationData(String apiParameters) {

        try {
            // Create connection
            URL url = new URL(geoCodeUrl+apiParameters);
            Log.d("URL", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int statusCode = connection.getResponseCode();
            Log.d("Statuscode ", String.valueOf(statusCode));

            InputStream in = new BufferedInputStream(connection.getInputStream());
            String html = readStream(in);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return "";
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
            Log.d("Test", "foo");
        }
        is.close();
        return sb.toString();
    }
}

