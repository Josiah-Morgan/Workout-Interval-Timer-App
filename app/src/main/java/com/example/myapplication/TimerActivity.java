package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    Button btn_backButton, btn_addFiveSeconds, btn_subtractFiveSeconds, btn_addInterval, btn_pauseAndResumeCountdown, btn_resetCountdown;
    TextView tv_totalTime, tv_countdownTitle, tv_countdown, tv_intervalsCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // Design Parts
        btn_backButton = findViewById(R.id.btn_backButton);
        btn_addFiveSeconds = findViewById(R.id.btn_addFiveSeconds);
        btn_subtractFiveSeconds = findViewById(R.id.btn_subtractFiveSeconds);
        btn_addInterval = findViewById(R.id.btn_addInterval);
        btn_pauseAndResumeCountdown = findViewById(R.id.btn_pauseAndResumeCountdown);
        btn_resetCountdown = findViewById(R.id.btn_resetCountdown);
        tv_totalTime = findViewById(R.id.tv_totalTime);
        tv_countdownTitle = findViewById(R.id.tv_countdownTitle);
        tv_countdown = findViewById(R.id.tv_countdown);
        tv_intervalsCounter = findViewById(R.id.tv_intervalsCounter);

        Intent intent = getIntent();
        String workoutTime = intent.getStringExtra("WorkoutTime");
        String restTime = intent.getStringExtra("RestTime");
        String countdownIntervals = intent.getStringExtra("CountdownIntervals");

        // add checks for data

        assert countdownIntervals != null;
        if (countdownIntervals.equals("Inf.")) {
            countdownIntervals = "-1";
            btn_addInterval.setVisibility(View.INVISIBLE);
        }


        // Starting Countdowns
        MyCountdown workoutTimer = new MyCountdown(TimerActivity.this, tv_totalTime, tv_countdownTitle, tv_countdown, tv_intervalsCounter, btn_pauseAndResumeCountdown, btn_addFiveSeconds, btn_subtractFiveSeconds,workoutTime, restTime, countdownIntervals);
        workoutTimer.startTimer();


        // Back Button
        btn_backButton.setOnClickListener(v -> workoutTimer.backButton());

        // Add Interval
        btn_addInterval.setOnClickListener(v -> workoutTimer.addInterval());

        // Add 5 Seconds
        btn_addFiveSeconds.setOnClickListener(v -> workoutTimer.addFiveSeconds());

        // Subtract Five Seconds
        btn_subtractFiveSeconds.setOnClickListener(v -> workoutTimer.subtractFiveSeconds());


        // Pause and Resume
        btn_pauseAndResumeCountdown.setOnClickListener(v -> workoutTimer.pauseResumeTimerHandler());

        // Reset
        btn_resetCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutTimer.resetTimer();
            }
        });


    }
}