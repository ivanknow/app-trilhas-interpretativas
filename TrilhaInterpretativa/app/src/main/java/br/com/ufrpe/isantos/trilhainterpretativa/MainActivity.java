package br.com.ufrpe.isantos.trilhainterpretativa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;
import br.com.ufrpe.isantos.trilhainterpretativa.services.GeoService;

public class MainActivity extends AppCompatActivity {


    private final String LOG_TAG = "TrilhaInterpretativaApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent2 = new Intent(this, GeoService.class);
        stopService(intent2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent2 = new Intent(this, GeoService.class);
        stopService(intent2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    this.finish();
                }
                return;
            }


        }
    }

    public void openMap(View v) {

        Intent intent2 = new Intent(this, GeoService.class);
        startService(intent2);
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(intent);

    }


    public void updateData(View v) {
        Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
        startActivity(intent);
    }


}
