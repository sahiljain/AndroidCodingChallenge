package ca.sahiljain.androidcodingchallenge;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Sahil Jain on 13/10/2014.
 */
public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("sahil", "starting task");
        Socket socket = null;
        try {
            socket = new Socket("192.168.1.131", 1234);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            while(inputStream.available() > 0) {
                String in = inputStream.read() + "";
                Log.d("sahil", in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
