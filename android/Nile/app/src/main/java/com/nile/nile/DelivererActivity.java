package com.nile.nile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.nile.nile.service.GPSTracker;

public class DelivererActivity extends AppCompatActivity {

    private double mLatitude;
    private double mLongitude;

    GPSTracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverer);

        mTracker = new GPSTracker(this);
        if(mTracker.canGetLocation()) {
            mLatitude = mTracker.getLatitude();
            mLongitude = mTracker.getLongitude();
        } else {
            mTracker.showSettingsAlert();
        }
        Toast.makeText(getApplicationContext(),"Current location: Latitude: " + mLatitude + "\n" + "Longitude: " + mLongitude, Toast.LENGTH_LONG).show();
    }
}
