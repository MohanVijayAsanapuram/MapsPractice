package com.spiders.maps.mapspractice.intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spiders.maps.mapspractice.R;

public class IntentServiceActivity extends AppCompatActivity {

    private Context context;
    private TextView tv;

    private BroadcastReceiver DownloadReceiver=new BroadcastReceiver(){
        public void onReceive(Context context,Intent intent){
            // Display message from DownloadService
            Bundle b=intent.getExtras();
            if(b!=null){

                tv.setText(b.getString(MyIntentService.EXTRA_MESSAGE));
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        context=this;
        tv=(TextView)findViewById(R.id.txtmessage);
        final Button btDownload=(Button)findViewById(R.id.btdownload);
        btDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText url=(EditText)findViewById(R.id.txturl);

                String urlStr=url.getText().toString();
                if(!urlStr.equals("")){

                    Intent newIntent=new Intent(context,MyIntentService.class);
                    newIntent.setAction(MyIntentService.ACTION_DOWNLOAD);
                    newIntent.putExtra(MyIntentService.EXTRA_URL,urlStr);
                    // Start Download Service
                    tv.setText("Downloading...");
                    context.startService(newIntent);
                }
            }
        });
    }

    protected void onResume(){
        super.onResume();
        // Register receiver to get message from DownloadService
        registerReceiver(DownloadReceiver, new IntentFilter(MyIntentService.ACTION_DOWNLOAD));

    }

    protected void onPause(){
        super.onPause();
        // Unregister the receiver
        unregisterReceiver(DownloadReceiver);

    }


}
