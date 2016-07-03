package com.nile.nile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.nile.nile.service.GPSTracker;

public class MainActivity extends AppCompatActivity {

    private double mLatitude;
    private double mLongitude;

    GPSTracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = this.getSharedPreferences("Share", Context.MODE_PRIVATE);
        int isUser = pref.getInt("isUser", -1);
        int currentID = pref.getInt("currentID", -1);
        Log.d("IsUser",String.valueOf(isUser));
        Log.d("CurrentID",String.valueOf(currentID));

        if(isUser == -1){
            mTracker = new GPSTracker(this);
            if(mTracker.canGetLocation()) {
                mLatitude = mTracker.getLatitude();
                mLongitude = mTracker.getLongitude();
            } else {
                mTracker.showSettingsAlert();
            }
            Toast.makeText(getApplicationContext(),"Current location: Latitude: " + mLatitude + "\n" + "Longitude: " + mLongitude, Toast.LENGTH_LONG).show();
        } else if (isUser == 1){
            Intent intent;
            if(currentID == -1){
                intent = new Intent(this, UserRegisterActivity.class);
            } else {
                intent = new Intent(this, UserActivity.class);
            }
            startActivity(intent);
        } else {
            Intent intent = null;
            if(currentID == -1){
                //intent = new Intent(this, DelivererRegisterActivity.class);
            } else {
                intent = new Intent(this, DelivererActivity.class);
            }
            startActivity(intent);
        }
    }

    /** Called when the user clicks the User button */
    public void setUserView(View view) {
        SharedPreferences pref = this.getSharedPreferences("Share", Context.MODE_PRIVATE);
        int currentID = pref.getInt("currentID", -1);

        Intent intent;
        if(currentID == -1){
            intent = new Intent(this, UserRegisterActivity.class);
        } else {
            intent = new Intent(this, UserActivity.class);
        }

        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("isUser", 1);
        edit.commit();

        startActivity(intent);
    }

    /** Called when the user clicks the Deliverer button */
    public void setDelivererView(View view) {
        Intent intent = new Intent(this, DelivererActivity.class);

        SharedPreferences pref = this.getSharedPreferences("Share", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("isUser", 0);
        edit.commit();

        startActivity(intent);
    }
}
