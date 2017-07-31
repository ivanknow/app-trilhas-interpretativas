package br.com.ufrpe.isantos.trilhainterpretativa;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Local;
import br.com.ufrpe.isantos.trilhainterpretativa.entity.Point;
import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;
import br.com.ufrpe.isantos.trilhainterpretativa.services.GeoService;
import br.com.ufrpe.isantos.trilhainterpretativa.services.LocationService;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailConstants;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailJSONParser;

public class MapActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    double scala;

    ListView listPoints;
    //ArrayList<Point> points;
    ArrayAdapter<Point> adapter;

    TextView tvLatitudeValue;
    TextView tvLongetudeValue;

    TextView tvAltValue;
    TextView tvRaio;
    TextView tvHoraInicio;
    TextView tvTrailDesc;


    double longitude;
    double latitude;
    double acc;
    double alt;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private final String LOG_TAG = "TrilhaInterpretativaApp";
    private Trail trail;
    private Point p;
    private long interval = 5000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        listPoints = (ListView) findViewById(R.id.view_list_points);


        tvLatitudeValue = (TextView) findViewById(R.id.lbLatitudeValue);
        tvLongetudeValue = (TextView) findViewById(R.id.lbLongitudeValue);
        tvAltValue = (TextView) findViewById(R.id.lbAltValue);
        tvHoraInicio = (TextView) findViewById(R.id.tvhorarioinicio);
        tvTrailDesc = (TextView) findViewById(R.id.tvTrailDesc);
        tvRaio = (TextView) findViewById(R.id.textViewRaio);

        FileInputStream fis = null;
        try {
            fis = getApplicationContext().openFileInput(getString(R.string.db_file));

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            trail = TrailJSONParser.stringToObject(sb.toString());

            adapter = new ArrayAdapter<>(MapActivity.this, android.R.layout.simple_list_item_1, GeoService.points);
            listPoints.setAdapter(adapter);
            listPoints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(MapActivity.this, PointActivity.class);

                    i.putExtra("pointid",""+GeoService.points.get(position).getId());
                    startActivity(i);

                }
            });

        } catch (FileNotFoundException e) {
            String filename = getString(R.string.db_file);
            trail = new Trail();
            trail.setTitle("Trilha Indefinida");
            String string = TrailJSONParser.ObjectToString(trail);
            FileOutputStream outputStream;

            try {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();
            } catch (Exception ef) {
                ef.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTitle(trail.getTitle());
       /* SharedPreferences sharedPref2 = getSharedPreferences("default",Context.MODE_PRIVATE);

        float raio = sharedPref2.getFloat(getString(R.string.raio),TrailConstants.SCALA);
        tvRaio.setText(raio+"");
        scala = raio;*/


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String raio = settings.getString(getString(R.string.raio), TrailConstants.SCALA+"");
        tvRaio.setText(raio);
        scala = Double.parseDouble(raio);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String result = sharedPref.getString(getString(R.string.horaInicio), "");
        if(result.equals("")){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.horaInicio), new Date().toString());
            editor.commit();
             result = sharedPref.getString(getString(R.string.horaInicio), "");
        }

        tvHoraInicio.setText(result);
        tvTrailDesc.setText(trail.getDescr());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(interval);


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
        tvLatitudeValue.setText(location.getLatitude() + "");
        tvLongetudeValue.setText(location.getLongitude() + "");
        tvAltValue.setText(location.getAltitude() + "");

                //points = GeoService.points;
                adapter.notifyDataSetChanged();

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

    public void finishTrail(View v){
        stopService(new Intent(MapActivity.this, GeoService.class));

        finish();

    }


}
