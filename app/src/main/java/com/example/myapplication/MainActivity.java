package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_startCountdown;
    EditText et_workTime, et_restTime, et_countdownIntervals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_startCountdown = findViewById(R.id.btn_startCountdown);
        et_workTime = findViewById(R.id.et_workTime);
        et_restTime = findViewById(R.id.et_restTime);
        et_countdownIntervals = findViewById(R.id.et_countdownIntervals);

        btn_startCountdown.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TimerActivity.class);

            intent.putExtra("WorkoutTime", et_workTime.getText().toString());
            intent.putExtra("RestTime", et_restTime.getText().toString());
            intent.putExtra("CountdownIntervals", et_countdownIntervals.getText().toString());
            startActivity(intent);
        });
    }
}