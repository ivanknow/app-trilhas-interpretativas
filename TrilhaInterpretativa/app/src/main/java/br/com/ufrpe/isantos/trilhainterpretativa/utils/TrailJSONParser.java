package br.com.ufrpe.isantos.trilhainterpretativa.utils;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;

/**
 * Created by ivan on 22/06/2017.
 */

public class TrailJSONParser {

    public static Trail fileToObject(File file) throws IOException {

        return stringToObject(readFile(file));
    }

    public static Trail stringToObject(String json) {
        Trail obj = new Gson().fromJson(json, Trail.class);
        return obj;
    }

    private static String readFile(File f)
            throws IOException {
        return new Scanner(f).useDelimiter("\\Z").next();
    }
}
