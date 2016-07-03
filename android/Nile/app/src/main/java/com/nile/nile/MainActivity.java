package com.nile.nile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        SharedPreferences pref = this.getSharedPreferences("Share", Context.MODE_PRIVATE);
        int userID = pref.getInt("isUser", -1);
<<<<<<< HEAD
        if(userID == 1) {
            Intent intent = new Intent(this, UserAddAddressActivity.class);
=======
        if (userID == 1) {
            Intent intent = new Intent(this, UserRegisterActivity.class);
>>>>>>> 6cc5717f5942e0a6e05f4e8d264f549b5c2c8177
            startActivity(intent);
        } else if (userID == 0) {
            Intent intent = new Intent(this, DelivererActivity.class);
            startActivity(intent);
        } */

    }
/*
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = this.getSharedPreferences("Share", Context.MODE_PRIVATE);
        int isUser = pref.getInt("isUser", -1);
        int currentID = pref.getInt("currentID", -1);
        Log.d("IsUser", String.valueOf(isUser));
        Log.d("CurrentID", String.valueOf(currentID));

        if (isUser == -1) {
            GPSTracker mTracker = new GPSTracker(this);
            double mLatitude = 0;
            double mLongitude = 0;
            if (mTracker.canGetLocation()) {
                mLatitude = mTracker.getLatitude();
                mLongitude = mTracker.getLongitude();
            } else {
                mTracker.showSettingsAlert();
            }
            Toast.makeText(getApplicationContext(), "Current location: Latitude: " + mLatitude + "\n" + "Longitude: " + mLongitude, Toast.LENGTH_LONG).show();
        } else if (isUser == 1) {
            Intent intent;
<<<<<<< HEAD
            if(currentID == -1){
                intent = new Intent(this, UserAddAddressActivity.class);
=======
            if (currentID == -1) {
                intent = new Intent(this, UserRegisterActivity.class);
>>>>>>> 6cc5717f5942e0a6e05f4e8d264f549b5c2c8177
            } else {
                intent = new Intent(this, UserActivity.class);
            }
            startActivity(intent);
        } else {
            Intent intent = null;
            if (currentID == -1) {
                intent = new Intent(this, DelivererRegisterActivity.class);
            } else {
                intent = new Intent(this, DelivererActivity.class);
            }
            startActivity(intent);
        }
    } */

    /**
     * Called when the user clicks the User button
     */
    public void setUserView(View view) {


        Intent intent;
        intent = new Intent(this, UserActivity.class);
        /*
        if(currentID == -1){
            intent = new Intent(this, UserAddAddressActivity.class);
        } else {
            intent = new Intent(this, UserActivity.class);
        }


        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("isUser", 1);
        edit.commit(); */

        startActivity(intent);
    }

    /**
     * Called when the user clicks the Deliverer button
     */
    public void setDelivererView(View view) {
        Intent intent = new Intent(this, DelivererRegisterActivity.class);;
        startActivity(intent);
    }

    public void startDeliver(View view) {
        Intent intent = new Intent(this, DelivererActivity.class);
        startActivity(intent);
    }

    public void addAddress(View view) {
        Intent intent = new Intent(this, UserAddAddressActivity.class);
        startActivity(intent);
    }

    public void createUser(View view) {
        Intent intent = new Intent(this, UserRegisterActivity.class);
        startActivity(intent);
    }

    public void showMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
