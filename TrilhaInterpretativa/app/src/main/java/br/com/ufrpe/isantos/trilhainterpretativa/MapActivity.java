package br.com.ufrpe.isantos.trilhainterpretativa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.LocationListener;

import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.m;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailJSONParser;

public class MapActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    EditText etLatitude;
    EditText etLongetude;
    EditText etJson;
    EditText etAcc;
    EditText etAlt;

    double longitude;
    double latitude;
    double acc;
    double alt;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private final String LOG_TAG = "TrilhaInterpretativaApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLongetude = (EditText) findViewById(R.id.etLongetude);
        etAcc = (EditText) findViewById(R.id.etAcc);
        etAlt = (EditText) findViewById(R.id.etAlt);
        etJson = (EditText) findViewById(R.id.jsonText);

        FileInputStream fis = null;
        try {
            fis = getApplicationContext().openFileInput("db.json");

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            etJson.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*File f = new File("db.json");
        try {
            etJson.setText(TrailJSONParser.fileToObject(f).getTitle());
        } catch (IOException e) {
            Toast.makeText(MapActivity.this,"file error: ", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*/

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(LOG_TAG, "FAIL");

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG, "Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOG_TAG, "Connection Fail");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOG_TAG, "CHANGED");
        etLatitude.setText(location.getLatitude() + "");
        etLongetude.setText(location.getLongitude() + "");
        etAlt.setText(location.getAltitude() + "");
        etAcc.setText(location.getSpeed() + "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
