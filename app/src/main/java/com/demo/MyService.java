package com.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static com.demo.MainActivity.DOWNLOAD_ACTION;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction().equals(DOWNLOAD_ACTION)) {

            new Thread(() -> {
                try {
                    URL url = new URL("https://raw.githubusercontent.com/kotlinkarun/api/master/.gitignore/emp.json");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String s;
                    StringBuilder builder = new StringBuilder();
                    while ((s = reader.readLine()) != null) {
                        builder.append(s);
                    }
                    Log.d("TAGS", "data Service: " + builder.toString());
                    EventBus.getDefault().post(new Message(builder.toString()));
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        }
        return START_STICKY;
    }
}
