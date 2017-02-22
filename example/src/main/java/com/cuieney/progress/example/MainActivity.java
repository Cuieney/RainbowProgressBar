package com.cuieney.progress.example;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cuieney.progress.library.RainbowProgressBar;

public class MainActivity extends AppCompatActivity {

    private RainbowProgressBar progress;
    private int progressValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = ((RainbowProgressBar) findViewById(R.id.progress));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressValue < 100) {
                    progressValue += 1;
                    SystemClock.sleep(40);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress(progressValue);
                        }
                    });
                }
            }
        }).start();
    }
}
