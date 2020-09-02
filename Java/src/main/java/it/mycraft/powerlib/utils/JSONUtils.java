package it.mycraft.powerlib.utils;


import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JSONUtils {

    public static JSONObject getJSON(String url) {
        try {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                String s = read(rd);
                return new JSONObject(s);
            } finally {
                is.close();
            }
        } catch (IOException ignored) {
            return null;
        }
    }

    public static boolean isValidJSON(String url) {
        try {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                read(rd);
                return true;
            } finally {
                is.close();
            }
        } catch (IOException ignored) {
            return false;
        }
    }

    private static String read(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}

