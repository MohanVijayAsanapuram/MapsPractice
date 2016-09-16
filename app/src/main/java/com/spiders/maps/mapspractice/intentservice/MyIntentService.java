package com.spiders.maps.mapspractice.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.transition.TransitionManager;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fotl on 12-07-2016.
 */
public class MyIntentService extends IntentService {

    public static final String ACTION_DOWNLOAD = "com.spiders.maps.mapspractice.intentservice.action.DOWNLOAD";
    //public static final String ACTION_BAZ = "com.example.dara.myapplication.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_URL = "com.spiders.maps.mapspractice.intentservice.extra.URL";
    public static final String EXTRA_MESSAGE = "com.spiders.maps.mapspractice.intentservice.extra.message";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    public MyIntentService() {
        super("");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_URL);
                Log.e("Service", url);
                downloadImage(url);


            }
        }
    }

    private void downloadImage(String urlStr) {
        FileOutputStream fos = null;
        InputStream is = null;
        String message = "Download failed.";
        try {
            // Get InputStream from the image url
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //connection.setDoInput(true);
            //connection.connect();
            is = connection.getInputStream();
            String fileName = urlStr.substring(urlStr.lastIndexOf('/') + 1);
            fos = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + fileName);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
            message = "Download completed";


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (fos != null) {
                try {
                    fos.close();

                } catch (IOException e) {
                }

            }
            if (is != null) {
                try {
                    is.close();

                } catch (IOException e) {
                }

            }
            // Send the feedback message to the MainActivity
            Intent backIntent = new Intent(MyIntentService.ACTION_DOWNLOAD);
            backIntent.putExtra(MyIntentService.EXTRA_MESSAGE, message);
            sendBroadcast(backIntent);
        }
    }

}
