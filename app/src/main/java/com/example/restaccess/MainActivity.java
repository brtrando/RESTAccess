package com.example.restaccess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView lv;
//    FieldString, DataString
    ArrayList<HashMap<String,String>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.mylist);
        dataList = new ArrayList<>();
        Log.d("REST","Start Application");
        new getJSON().execute();
    }

    private class getJSON extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            ListAdapter a = new SimpleAdapter(
                    MainActivity.this,dataList,R.layout.list_item,
                    new String[]{"id","nama","email"},
                    new int[]{R.id.id,R.id.nama,R.id.email}
                    );
            lv.setAdapter(a);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HTTPHandler h = new HTTPHandler();
            String u = "http://192.168.0.231:3000/reviewsDesktop";
            String s = h.getAccess(u);
            Log.d("REST","Response : " + s);
            if (s!=null){
                try{
                    JSONObject job = new JSONObject(s);
                    JSONArray js = new JSONArray(job);
                    for (int i = 0 ; i <js.length() ; i++){
                        JSONObject jo = js.getJSONObject(i);
                        String nama = jo.getString("title");
                        int id = jo.getInt("id");
                        String email = jo.getString("comment");
                        Log.d("REST","Nama "+id+": "+nama);
                        HashMap<String,String> hm = new HashMap<>();
                        hm.put("id",Integer.toString(id));
                        hm.put("nama",nama);
                        hm.put("email",email);
                        dataList.add(hm);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            return null;
        }
    }
}


