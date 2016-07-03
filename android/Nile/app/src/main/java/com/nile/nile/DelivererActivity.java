package com.nile.nile;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.nile.nile.model.NileAddress;
import com.nile.nile.service.GPSTracker;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import com.google.gson.Gson;

public class DelivererActivity extends AppCompatActivity {

    private double mLatitude;
    private double mLongitude;

    private ArrayAdapter<String> listAdapter;

    Map map;

    GPSTracker mTracker;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverer);

        SharedPreferences pref = this.getSharedPreferences("Share", Context.MODE_PRIVATE);
        int currentID = pref.getInt("currentID", -1);

        mTracker = new GPSTracker(this, currentID);
        if (mTracker.canGetLocation()) {
            mLatitude = mTracker.getLatitude();
            mLongitude = mTracker.getLongitude();
        } else {
            mTracker.showSettingsAlert();
        }
        Toast.makeText(getApplicationContext(), "Current location: Latitude: " + mLatitude + "\n" + "Longitude: " + mLongitude, Toast.LENGTH_LONG).show();

        final MapFragment mapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.mapDeliver);
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
                    map.getPositionIndicator().setVisible(true);
                    MapMarker marker = new MapMarker();
                    GeoCoordinate c = new GeoCoordinate(52.5056115300, 13.3934924400);
                    marker.setCoordinate(c);
                    map.addMapObject(marker);
                } else {
                    Log.e("ERROR", "ERROR: Cannot initialize MapFragment");
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ListView addressList = (ListView) findViewById(R.id.addressList);
        listAdapter = new ArrayAdapter<String>(this, R.layout.simple_row);
        addressList.setAdapter(listAdapter);

        String json =
                "["
                        + "{"
                        + "    \"street\": \"Charlottenstraße\","
                        + "    \"number\": 2,"
                        + "    \"zipcode\": 10969,"
                        + "    \"city\": \"Berlin\""
                        + "}"
                        + "]";

        //String orderedAddr = excutePost("http://localhost:8000/users/" + userID + "/packages/", "");

        //NileAddress[] addresses = new Gson().fromJson(orderedAddr, NileAddress[].class);

        NileAddress[] addresses = new Gson().fromJson(json, NileAddress[].class);

        listAdapter.add("Name: Kolja Esders");
        listAdapter.add("Street: " + addresses[0].getStreet() + " " + addresses[0].getNumber());
        listAdapter.add("Zip-Code: " + addresses[0].getZipcode() + ", City: " + addresses[0].getCity());
        listAdapter.add("Floor: 0");
        listAdapter.add("Paket ID: 73JA382JA");

        listAdapter.notifyDataSetChanged();
    }
}