package br.com.ufrpe.isantos.trilhainterpretativa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Image;
import br.com.ufrpe.isantos.trilhainterpretativa.entity.Point;
import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailConstants;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailFileUtils;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailJSONParser;

import static br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailFileUtils.getFileContent;
import static br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailJSONParser.stringToObject;

public class UpdateActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    TextView tvLastUpdate;
    EditText urlUpdate;
    EditText etRaio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mProgressDialog = new ProgressDialog(UpdateActivity.this);
        mProgressDialog.setMessage(getString(R.string.statusProgress));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        tvLastUpdate = (TextView) findViewById(R.id.lastUpdate);
        etRaio = (EditText) findViewById(R.id.editRaio);



        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String raio = settings.getString(getString(R.string.raio), TrailConstants.SCALA+"");
        Toast.makeText(getApplicationContext(),  raio, Toast.LENGTH_LONG).show();
        etRaio.setText(raio);

        urlUpdate = (EditText) findViewById(R.id.urlupdate);
        urlUpdate.setText(getString(R.string.service_url));
        Context context = getApplicationContext();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        String result = sharedPref.getString(getString(R.string.lastUpdateKey), "Nunca");

        tvLastUpdate.setText(getString(R.string.lastUpdate) + result);

    }

    public void startUpdate(View v) {

        final DownloadTask downloadTask = new DownloadTask(UpdateActivity.this);
        downloadTask.execute(urlUpdate.getText().toString());

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });

    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;


        public DownloadTask(Context context) {
            this.context = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, getString(R.string.updateFail) + result, Toast.LENGTH_LONG).show();
            else {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString(getString(R.string.lastUpdateKey), new Date().toString());

                editor.commit();
                Toast.makeText(context, getString(R.string.updateSuccess), Toast.LENGTH_SHORT).show();
                tvLastUpdate.setText(getString(R.string.lastUpdate) + new Date().toString());
            }


        }

        private String donwloadFile(String filename,String surl){
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            wl.acquire();

            try {
                InputStream input = null;
                FileOutputStream output = null;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(surl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return "Server returned HTTP " + connection.getResponseCode()
                                + " " + connection.getResponseMessage();


                    int fileLength = connection.getContentLength();


                    input = connection.getInputStream();
                    output = openFileOutput(filename, Context.MODE_PRIVATE);

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        // allow canceling with back button
                        if (isCancelled())
                            return null;
                        total += count;
                        // publishing the progress....
                        if (fileLength > 0) // only if total length is known
                            publishProgress((int) (total * 100 / fileLength));
                        output.write(data, 0, count);
                    }
                } catch (Exception e) {
                    return e.toString();
                } finally {
                    try {
                        if (output != null)
                            output.close();
                        if (input != null)
                            input.close();
                    } catch (IOException ignored) {
                    }

                    if (connection != null)
                        connection.disconnect();
                }
            } finally {
                wl.release();
            }
            return filename;
        }

        @Override
        protected String doInBackground(String... sUrl) {

            donwloadFile(getString(R.string.db_file),sUrl[0]);
            try {
                String result =  TrailFileUtils.getFileContent(getString(R.string.db_file),getApplicationContext());
                Trail t =TrailJSONParser.stringToObject(result);
                for (Point p:t.getPoints()){
                    for (Image i:p.getImages()){
                        donwloadFile(i.getSrc(),getString(R.string.service_url_base)+"res/"+i.getSrc());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //TrailJSONParser.fileToObject()
            return null;
        }
    }

    public void progressDialogConfigure(ProgressDialog mProgressDialog) {

    }
    public void resetBase(View v) {
        String filename = getString(R.string.db_file);
        Trail trail = new Trail();
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
    }
    public void showBase(View v) {

        Intent intent = new Intent(getApplicationContext(), BaseViwerActivity.class);
        startActivity(intent);




    }
    public void editRaio(View v) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(getString(R.string.raio), etRaio.getText()+"");
        editor.commit();


        Toast.makeText(getApplicationContext(), getString(R.string.raio)+etRaio.getText() , Toast.LENGTH_LONG).show();

    }




}



