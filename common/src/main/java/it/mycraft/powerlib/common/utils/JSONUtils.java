package it.mycraft.powerlib.common.utils;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JSONUtils {

    public static JsonObject getJSON(String url) {
        try {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                return Json.createReader(rd).readObject();
            } finally {
                is.close();
            }
        } catch (IOException ignored) {
            return null;
        }
    }

    public static boolean isValidJSON(String url) {
        try {
            try (InputStream is = new URL(url).openStream()) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                read(rd);
                return true;
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

