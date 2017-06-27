package br.com.ufrpe.isantos.trilhainterpretativa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Point;
import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailFileUtils;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailJSONParser;

import static java.lang.System.in;

public class PointActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tvPointDesc;
    Button btnBackMap;
    Point point = new Point();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        imageView = (ImageView) findViewById(R.id.imgPoint);
        int id =  getIntent().getIntExtra("pointid",1);
        Toast.makeText(getApplicationContext(), ":"+id, Toast.LENGTH_SHORT).show();
        try {
            String tjson = TrailFileUtils.getFileContent(getString(R.string.db_file),getApplicationContext());
            Trail trail = TrailJSONParser.stringToObject(tjson);
            for(Point p:trail.getPoints()){
                if(p.getId() == id){
                   point = p;
                }
            }

            tvPointDesc = (TextView) findViewById(R.id.tvDescPoint);
            tvPointDesc.setText(point.getDesc());
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
}
