package com.nile.nile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.search.Address;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.GeocodeRequest;
import com.here.android.mpa.search.Location;
import com.here.android.mpa.search.ResultListener;
import com.here.android.mpa.search.ReverseGeocodeRequest;

import com.nile.nile.service.GPSTracker;
import com.nile.nile.service.ReverseGeoCodeListener;



public class MainActivity extends AppCompatActivity {

    private double mLatitude;
    private double mLongitude;
    GPSTracker mTracker;

    Button btnShowLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTracker = new GPSTracker(this);
        if(mTracker.canGetLocation()) {
            mLatitude = mTracker.getLatitude();
            mLongitude = mTracker.getLongitude();
        } else {
            mTracker.showSettingsAlert();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Latitude: " + mLatitude + " Longitude: " + mLongitude, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        btnShowLocation = (Button) findViewById(R.id.btnLocation);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLatitude = mTracker.getLatitude();
                mLongitude = mTracker.getLongitude();
                GeoCoordinate currentLocation = new GeoCoordinate(mLatitude, mLongitude);
                ResultListener<Address> listener = new ReverseGeoCodeListener();
                ReverseGeocodeRequest request = new ReverseGeocodeRequest(currentLocation);
                
                Toast.makeText(getApplicationContext(), "Current location is: \n Lat: " + mLatitude + " \n Long: " + mLongitude, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the User button */
    public void setUserView(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the User button */
    public void setDelivererView(View view) {
        Intent intent = new Intent(this, DelivererActivity.class);
        startActivity(intent);
    }
}
