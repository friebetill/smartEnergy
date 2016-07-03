package com.nile.nile;

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
        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout1);

        TextView nextAddr = new TextView(this);
        nextAddr.setTextSize(25);
        nextAddr.setText("Next Address");

        layout.addView(nextAddr);
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
                +"    \"street\": \"Charlottenstraße\","
                +"    \"number\": 2,"
                +"    \"zipcode\": 10969,"
                +"    \"city\": \"Berlin\""
                +  "}"
                +"]";

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
}
