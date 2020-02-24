package com.example.customprogressview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends AppCompatActivity {

    private CustomProgressView customProgressView;
    private int curProgress;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

        }
    };
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customProgressView=findViewById(R.id.progress_view);
        customProgressView.setMaxProgress(100);

        runnable = new Runnable() {
           @Override
           public void run() {
               curProgress += 1;
               customProgressView.setCurProgress(curProgress);
               handler.postDelayed(runnable,50);
           }
       };
        handler.postDelayed(runnable,1000
        );

    }
}
