package com.cuieney.progress.example;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cuieney.progress.library.RainbowProgressBar;

public class MainActivity extends AppCompatActivity {

    private RainbowProgressBar progress;
    private int progressValue = 0;
    private int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rainbow);
        colors[0] = red;
        colors[1] = orange;
        colors[2] = yellow;
        colors[3] = green;
        colors[4] = blue;
        colors[5] = indigo;
        colors[6] = purple;

        progress = ((RainbowProgressBar) findViewById(R.id.progress));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressValue < 100) {
                    progressValue += 1;

                    SystemClock.sleep(30);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress(progressValue);
                        }
                    });
                }
            }
        });
    }

    private int red = Color.RED;
    private int orange = Color.parseColor("#FF9929");
    private int yellow = Color.YELLOW;
    private int green = Color.GREEN;
    private int blue = Color.BLUE;
    private int indigo = Color.parseColor("#2D2991");
    private int purple = Color.parseColor("#9012FE");

    private int[] colors = new int[7];
}
