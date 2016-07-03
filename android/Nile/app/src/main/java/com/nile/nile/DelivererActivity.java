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
import android.widget.Toast;

import com.nile.nile.model.NileAddress;
import com.nile.nile.service.GPSTracker;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import com.google.gson.Gson;

public class DelivererActivity extends AppCompatActivity implements LocationListener {

    private double mLatitude;
    private double mLongitude;

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
        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout1);

        TextView nextAddr = new TextView(this);
        nextAddr.setTextSize(25);
        nextAddr.setText("Next Address");

        layout.addView(nextAddr);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000,   // 3 sec
                0, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout1);
        layout.removeAllViews();

        TextView nextAddr = new TextView(this);
        nextAddr.setTextSize(25);
        nextAddr.setText("Next Address");

        layout.addView(nextAddr);

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

        TextView textView1 = new TextView(this);
        textView1.setTextSize(25);
        textView1.setText(addresses[0].getStreet() + " " + addresses[0].getNumber() + ",");

        layout.addView(textView1);

        TextView textView2 = new TextView(this);
        textView2.setTextSize(25);
        textView2.setText(addresses[0].getZipcode() + " " + addresses[0].getCity());

        layout.addView(textView2);

        Log.d("TOKEN", FirebaseInstanceId.getInstance().getToken());

    }

    @Override
    public void onLocationChanged(Location location) {
        String str = "Latitude: "+location.getLatitude()+"Longitude: "+location.getLongitude();

        Log.d("Location", str);
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }
}
