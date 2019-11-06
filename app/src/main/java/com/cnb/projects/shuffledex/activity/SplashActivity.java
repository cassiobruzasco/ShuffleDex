package com.cnb.projects.shuffledex.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cnb.projects.shuffledex.R;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends Activity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new ScheduledThreadPoolExecutor(1).schedule(this, 2, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
