package br.com.ufrpe.isantos.trilhainterpretativa.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.ufrpe.isantos.trilhainterpretativa.R;

import static br.com.ufrpe.isantos.trilhainterpretativa.R.id.etBaseConsole;

/**
 * Created by ivan on 26/06/2017.
 */

public class TrailFileUtils {

    public static String getFileContent(String filename, Context context) throws IOException {

            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();


    }
}
