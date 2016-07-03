package com.nile.nile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.nile.nile.model.NilePackage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserActivity extends AppCompatActivity {

    private  ArrayAdapter<String> listAdapter;
    private ListView ordersList;

    Map map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final MapFragment mapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.mapUser);
        // initialize the Map Fragment and
        // retrieve the map that is associated to the fragment
        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(
                    OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    // now the map is ready to be used
                    map = mapFragment.getMap();
                    map.setCenter(new GeoCoordinate(52.5056115300,
                            13.3934924400), Map.Animation.NONE);

                } else {
                    Log.e("ERROR", "ERROR: Cannot initialize MapFragment");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ordersList = (ListView) findViewById(R.id.ordersList);

        SharedPreferences pref = this.getSharedPreferences("Share", Context.MODE_PRIVATE);
        int isUser = pref.getInt("isUser", -1);
        Log.d("ID", String.valueOf(isUser));

        listAdapter = new ArrayAdapter<String>(this, R.layout.simple_row);
        ordersList.setAdapter(listAdapter);
        listAdapter.add("Arriving orders");

        new executeGETThread().execute();
    }

    private void updateOrders(String json) {
        NilePackage[] packages = new Gson().fromJson(json, NilePackage[].class);

        /*for(int i = 0; i < packages.length; i++){
            listAdapter.add(packages[i].getSender() + ", " + (int) packages[i].getMins_until_delivery() + "min, " + packages[i].getDeliver().getName());

            MapMarker marker = new MapMarker();
            GeoCoordinate c = new GeoCoordinate(52.5056115300-i*0.001, 13.3934924400+i*0.001);
            marker.setCoordinate(c);
            map.addMapObject(marker);
        }*/

        listAdapter.add("From: Amazon");
        listAdapter.add("Deliverer: DHL");
        listAdapter.add("Estimated time: 5min");
        listAdapter.add("Paket-ID: JA382JA");

        MapMarker marker2 = new MapMarker();
        GeoCoordinate c2 = new GeoCoordinate(52.504942, 13.392758);
        marker2.setCoordinate(c2);
        map.addMapObject(marker2);

        listAdapter.notifyDataSetChanged();
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
            int userID = 5;
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
