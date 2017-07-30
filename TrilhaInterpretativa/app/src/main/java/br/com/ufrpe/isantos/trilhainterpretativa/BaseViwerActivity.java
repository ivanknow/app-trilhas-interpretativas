package br.com.ufrpe.isantos.trilhainterpretativa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.IOException;

import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailFileUtils;

import static br.com.ufrpe.isantos.trilhainterpretativa.R.id.etBaseConsole;

public class BaseViwerActivity extends AppCompatActivity {
    EditText etBaseConsole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_viwer);
        etBaseConsole = (EditText) findViewById(R.id.etBaseConsole);
          try {

           String result =  TrailFileUtils.getFileContent(getString(R.string.db_file),getApplicationContext());
            etBaseConsole.setText(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            etBaseConsole.setText(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            etBaseConsole.setText(e.toString());
        }
    }
}
