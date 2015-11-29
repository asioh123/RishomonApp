package com.example.assy.rishomon;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.example.assy.rishomon.tools.GlobalConstants;

public class SplashScreenActivity extends Activity {

    Activity mActivity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            // This method will be executed once the timer is over
            public void run() {
                moveToMainActivity();
            }
        }, GlobalConstants.SPLASH_TIME_OUT);
    }

    private void moveToMainActivity() {
        // Start main activity
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        SplashScreenActivity.this.startActivity(intent);
        finish();
    }
}