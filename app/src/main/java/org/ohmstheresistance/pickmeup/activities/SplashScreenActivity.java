package org.ohmstheresistance.pickmeup.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import org.ohmstheresistance.pickmeup.R;

public class SplashScreenActivity extends AppCompatActivity {

    private View splashView;
    private Intent toMainActivityIntent;
    private TextView splashScreenTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashView = findViewById(R.id.splash_layout);
        splashScreenTextView = findViewById(R.id.pick_me_up_splash_textview);
        splashView.setVisibility(View.VISIBLE);

        splashView.startAnimation(AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.bounce));

        Animation blinkingAnimation = new AlphaAnimation(0.0f, 1.0f);
        blinkingAnimation.setDuration(100);
        blinkingAnimation.setStartOffset(300);
        blinkingAnimation.setRepeatMode(Animation.REVERSE);
        blinkingAnimation.setRepeatCount(Animation.INFINITE);
        splashScreenTextView.startAnimation(blinkingAnimation);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                toMainActivityIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(toMainActivityIntent);
                SplashScreenActivity.this.finish();

            }
        }, 4000);
}

}
