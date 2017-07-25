package br.com.ufrpe.isantos.trilhainterpretativa;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Point;
import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailFileUtils;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailJSONParser;

import static android.R.attr.id;
import static br.com.ufrpe.isantos.trilhainterpretativa.R.string.latitude;
import static br.com.ufrpe.isantos.trilhainterpretativa.R.string.longitude;
import static java.lang.System.in;

public class PointActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tvPointDesc;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            GeoService.LocalBinder binder = (GeoService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    Button btnBackMap;
    Point point = new Point();
    GeoService mService;
    boolean mBound = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        imageView = (ImageView) findViewById(R.id.imgPoint);
        Intent intent2 = getIntent();
        String idpoint = intent2.getStringExtra("pointid");

       // Toast.makeText(getApplicationContext(), "point:"+idpoint, Toast.LENGTH_SHORT).show();

        try {
            String tjson = TrailFileUtils.getFileContent(getString(R.string.db_file),getApplicationContext());
            Trail trail = TrailJSONParser.stringToObject(tjson);
            for(Point p:trail.getPoints()){
                if(p.getId() == Integer.parseInt(idpoint)){
                   point = p;
                }
            }

            tvPointDesc = (TextView) findViewById(R.id.tvDescPoint);
            tvPointDesc.setText(point.getDescr()+"\n("+point.getLocal().getLatitude()+","+point.getLocal().getLatitude()+")");
            setTitle(point.getTitle());
            if (point.getImages().size() > 0) {
                try {
                    FileInputStream retorno = getApplicationContext().openFileInput(point.getImages().get(0).getSrc());
                    Bitmap image = BitmapFactory.decodeStream(retorno);
                    imageView.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }



    }

    public void backToMap(View v){
        finish();
    }
    public void openMap(View v){
        //GeoService.startActionFoo(getApplicationContext(),null);
      /*  if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            int num = mService.getRandomNumber();
            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
        }*/
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", point.getLocal().getLatitude(), point.getLocal().getLongitude());
        Uri myUri = Uri.parse(uri);
        showMap(myUri);

    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, GeoService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }*/
  public void showMap(Uri geoLocation) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(geoLocation);
      if (intent.resolveActivity(getPackageManager()) != null) {
          startActivity(intent);
      }
  }

}
