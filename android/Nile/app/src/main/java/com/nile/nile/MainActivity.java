package com.nile.nile;

import android.location.Location;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.here.android.mpa.search.ResultListener;
import com.here.android.mpa.search.ReverseGeocodeRequest;

import com.nile.nile.service.GPSTracker;
import com.nile.nile.service.ReverseGeoCodeListener;



public class MainActivity extends AppCompatActivity {

    private double mLatitude;
    private double mLongitude;

    GPSTracker mTracker;

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
        Toast.makeText(getApplicationContext(),"Current location: Latitude: " + mLatitude + "\n" + "Longitude: " + mLongitude, Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(this, UserRegisterActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Deliverer button */
    public void setDelivererView(View view) {
        Intent intent = new Intent(this, DelivererActivity.class);
        startActivity(intent);
    }
}
