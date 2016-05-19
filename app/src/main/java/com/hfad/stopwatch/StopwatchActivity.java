package com.hfad.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class StopwatchActivity extends AppCompatActivity {
    private int seconds; //seconds passed
    private boolean running; //is the stopwatch running?
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        runtimer(); //start running when activity is created
        //restore the state
        if (savedInstanceState != null) { //if there is a saved state
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            if (wasRunning) {
                running = true;
            }
        }
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) { //stop from running and reset to 0
        running = false;
        seconds = 0;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    public void onStop() {
        super.onStop();
        wasRunning = running; //record whether stopwatch was running when onStop() was called
        running = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (wasRunning) {
            running = true; //if it was running, set it running again
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    public void runtimer() {
        final TextView textView = (TextView) findViewById(R.id.textView);
        final Handler handler = new Handler(); //create new Handler to schedule stuff
        handler.post(new Runnable() { //post will immediately run the code
            @Override
            public void run() { //code you want to run
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                textView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000); //run code again after 1 second
            }
        });
    }


}
