package com.adiel.gogogicha;

import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Send extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... params) {
        try {


            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(params[1]);
            wr.close();

            conn.connect();
            Log.e("response",conn.getResponseMessage()+"");

        }catch (Exception e){
            Log.d("err",e.toString());
        }
        return null;

    }
}
