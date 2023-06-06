package com.example.restaccess;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPHandler {
    public HTTPHandler(){

    }

    public String getAccess(String url){
        String respponse = null ;
        URL u = null;
        try {
            u = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            InputStream i = new BufferedInputStream(conn.getInputStream());
            respponse = converttostring(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respponse;
    }

    private String converttostring(InputStream i) {
        BufferedReader r = new BufferedReader(new InputStreamReader(i));
        StringBuilder sb = new StringBuilder();
        String dummy;

        try {
            while ((dummy = r.readLine()) != null){
                sb.append(dummy).append("\n");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return sb.toString();
    }
}
