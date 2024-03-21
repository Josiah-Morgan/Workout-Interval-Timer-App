package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MyCountdown {
    private Context context;
    private CountDownTimer countDownTimer;
    private final TextView tv_total_time;
    private final TextView tv_countdown_title;
    private final TextView tv_countdown;
    private final TextView tv_interval_counter;
    private final Button btn_pause_and_resume_countdown;
    private final Button btn_add_five;
    private final Button btn_subtract_five;
    private final long workDuration;
    private final long restDuration;
    private int countdownIntervals;
    private int intervalCounter = 0;
    private long totalTime = 0;
    private long timeRemaining; // Remaining time of the current interval
    private boolean isWorkPhase;

    public MyCountdown(Context context, TextView tv_total_time, TextView tv_countdown_title, TextView tv_countdown, TextView tv_interval_counter, Button btn_pause_and_resume_countdown, Button btn_add_five, Button btn_subtract_five, String workoutTime, String restTime, String countdownIntervals) {
        this.context = context;
        this.tv_total_time = tv_total_time;
        this.tv_countdown_title = tv_countdown_title;
        this.tv_countdown = tv_countdown;
        this.tv_interval_counter = tv_interval_counter;
        this.btn_pause_and_resume_countdown = btn_pause_and_resume_countdown;
        this.btn_add_five = btn_add_five;
        this.btn_subtract_five = btn_subtract_five;
        this.workDuration = Long.parseLong(workoutTime) * 1000; // Convert to milliseconds
        this.restDuration = Long.parseLong(restTime) * 1000; // Convert to milliseconds
        this.countdownIntervals = Integer.parseInt(countdownIntervals);

        // Set initial remaining time to work duration
        this.timeRemaining = this.workDuration;
    }


    public void backButton() {
        countDownTimer.cancel();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    public void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        updateIntervalCounter();
        isWorkPhase = true;
        startWorkTimer(timeRemaining);
        totalTimeTimer();
    }

    public void addInterval() {
        if (!(countdownIntervals == -1)) {
            countdownIntervals++;
            updateIntervalCounter();
        }
    }

    public void addFiveSeconds() {
        timeRemaining += 5000;
        countDownTimer.cancel();
        updateCountdownText();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (isWorkPhase) { // true
            startWorkTimer(timeRemaining);
        } else {
            startRestTimer(timeRemaining);
        }


    }

    public void subtractFiveSeconds() {
        if (timeRemaining >= 5000) {
            timeRemaining -= 5000;
            updateCountdownText();
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (isWorkPhase) { // true
            startWorkTimer(timeRemaining);
        } else {
            startRestTimer(timeRemaining);
        }


    }


    public void pauseResumeTimerHandler() {

        String buttonText = btn_pause_and_resume_countdown.getText().toString();
        if (buttonText.equals("Pause")) {
            countDownTimer.cancel();
            btn_pause_and_resume_countdown.setText("Resume");
        } else if (buttonText.equals("Resume")) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            if (isWorkPhase) { // true
                startWorkTimer(timeRemaining);
            } else {
                startRestTimer(timeRemaining);
            }
            btn_pause_and_resume_countdown.setText("Pause");
        }
    }

    public void resetTimer() {
        countDownTimer.cancel();

        String buttonText = btn_pause_and_resume_countdown.getText().toString();
        if (buttonText.equals("Resume")) {
            btn_pause_and_resume_countdown.setText("Pause");
        }

        countDownTimer.start();
    }


    private void startWorkTimer(long workCountdown) {
        tv_countdown_title.setText("Work Time");
        countDownTimer = new CountDownTimer(workCountdown, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                playSound(R.raw.countdown_end_sound_1_second);
                timeRemaining = restDuration;
                isWorkPhase = false;
                startRestTimer(restDuration);
            }
        }.start();
    }

    private void startRestTimer(long restCountdown) {
        tv_countdown_title.setText("Rest Time");
        countDownTimer = new CountDownTimer(restCountdown, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                playSound(R.raw.countdown_end_sound_1_second);
                intervalCounter++;
                updateIntervalCounter();
                if (intervalCounter < countdownIntervals || countdownIntervals == -1) {
                    timeRemaining = workDuration;
                    isWorkPhase = true;
                    startWorkTimer(workDuration);
                } else {
                    countDownTimer.cancel();
                }
            }
        }.start();
    }


    private void totalTimeTimer() {
        new CountDownTimer(100000000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                totalTime++;
                updateTotalTimeText();
            }

            @Override
            public void onFinish() {

            }

        }.start();
    }

    private void updateCountdownText() {
        long seconds = timeRemaining / 1000;
        long hours = seconds / 3600; // Calculate hours
        long minutes = (seconds % 3600) / 60; // Calculate remaining minutes
        seconds = seconds % 60; // Calculate remaining seconds

        // Display the time with hours, minutes, and seconds
        if (hours > 0) {
            tv_countdown.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
        } else {
            tv_countdown.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
        }
    }

    private void updateTotalTimeText() {
        long seconds = totalTime;
        long hours = seconds / 3600; // Calculate hours
        long minutes = (seconds % 3600) / 60; // Calculate remaining minutes
        seconds = seconds % 60; // Calculate remaining seconds

        // Display the time with hours, minutes, and seconds
        if (hours > 0) {
            tv_total_time.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
        } else {
            tv_total_time.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
        }
    }

    private void updateIntervalCounter() {
        if (countdownIntervals == -1) {
            tv_interval_counter.setText("Interval: " + intervalCounter);
        }
        else {
            tv_interval_counter.setText("Interval: " + intervalCounter + "/" + countdownIntervals);
        }
    }

    private void playSound(int soundResource) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, soundResource);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mediaPlayer.start();
    }




}