package br.com.ufrpe.isantos.trilhainterpretativa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static java.lang.System.in;

public class PointActivity extends AppCompatActivity {
 ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        imageView = (ImageView) findViewById(R.id.img);
        try {
            FileInputStream retorno = getApplicationContext().openFileInput("image1.jpg");
           // File fileImage = retorno.g
            Bitmap image = BitmapFactory.decodeStream(retorno);
            imageView.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
