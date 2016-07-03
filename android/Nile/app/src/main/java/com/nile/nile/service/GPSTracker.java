package com.nile.nile.service;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Felix on 02.07.16.
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    private int deliverID;

    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 1 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 200; // 5 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context, int ID) {
        this.mContext = context;
        this.deliverID = ID;
         getLocation(context);
    }

    public Location getLocation(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("Message: ","Location changed, " + location.getAccuracy() + " , " + location.getLatitude()+ "," + location.getLongitude());
        JSONObject newLocation = new JSONObject();
        try {
            newLocation.put("lat", location.getLatitude());
            newLocation.put("lng", location.getLongitude());
            JSONTask postTask = new JSONTask(this, deliverID);
            postTask.execute(String.valueOf(newLocation));

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

   private class JSONTask extends AsyncTask<String, Void, String> {

       private Context mContext;
       private int userID;

       private String apiEndPoint = "http://54.93.34.46/users/";

       public JSONTask(Context context, int ID) {
           mContext = context;
           userID = ID;
       }

       @Override
       protected String doInBackground(String... params) {
           String jsonData = params[0];
           String jsonResponse = null;
           HttpURLConnection urlConnection = null;
           BufferedReader reader = null;

           try {
               URL url = new URL(apiEndPoint + userID + "/locations/");
               urlConnection = (HttpURLConnection) url.openConnection();
               urlConnection.setDoOutput(true);
               urlConnection.setRequestMethod("POST");
               urlConnection.setRequestProperty("Content-Type", "application/json");
               urlConnection.setRequestProperty("Accept", "application/json");
               urlConnection.connect();


               Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
               writer.write(jsonData);
               writer.close();
               /*
               OutputStream os = urlConnection.getOutputStream();
               os.write(jsonData.getBytes());
               os.flush();
               os.close(); */

               int responseCode = urlConnection.getResponseCode();
               Log.d("URL CODE", "" + responseCode);

               InputStream inputStream;
               if(urlConnection.getResponseCode() / 100 == 2) {
                   inputStream = urlConnection.getInputStream();
               } else {
                   inputStream = urlConnection.getErrorStream();
               }
               StringBuffer buffer = new StringBuffer();
               if (inputStream == null) {
                   // Nothing to do.
                   return null;
               }
               reader = new BufferedReader(new InputStreamReader(inputStream));
               String inputLine;
               while ((inputLine = reader.readLine()) != null)
                   buffer.append(inputLine + "\n");
               if (buffer.length() == 0) {
                   // Stream was empty. No point in parsing.
                   return null;
               }
               jsonResponse = buffer.toString();
               Log.i("JSON RESPONSE", jsonResponse);

               return jsonResponse;
           } catch (IOException ex) {
               ex.printStackTrace();
           } finally {
               if(urlConnection != null) {
                   urlConnection.disconnect();
               }
               if(reader != null) {
                   try {
                       reader.close();
                   } catch (final IOException ex ) {
                       Log.e("EXCEPTION", "Error closing stream", ex);
                   }
               }
           }
           return null;
       }
   }
}
