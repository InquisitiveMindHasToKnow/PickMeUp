package org.ohmstheresistance.pickmeup.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import org.ohmstheresistance.pickmeup.R;

public class SplashScreenActivity extends AppCompatActivity {

    public static final String KEY_PREFS_FIRST_LAUNCH = "first_launch";

    private TextView splashScreenTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashScreenTextView = findViewById(R.id.pick_me_up_splash_textview);

        Animation blinkingAnimation = new AlphaAnimation(0.0f, 1.0f);
        blinkingAnimation.setDuration(100);
        blinkingAnimation.setStartOffset(300);
        blinkingAnimation.setRepeatMode(Animation.REVERSE);
        blinkingAnimation.setRepeatCount(Animation.INFINITE);
        splashScreenTextView.startAnimation(blinkingAnimation);


        final SharedPreferences firstLaunchCheck = PreferenceManager.getDefaultSharedPreferences(this);

        if (firstLaunchCheck.getBoolean(KEY_PREFS_FIRST_LAUNCH, true)) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent toGetUserNameIntent = new Intent(SplashScreenActivity.this, UserNameActivity.class);
                    startActivity(toGetUserNameIntent);
                    SplashScreenActivity.this.finish();

                    firstLaunchCheck.edit().putBoolean(KEY_PREFS_FIRST_LAUNCH, false).apply();

                }
            }, 4000);

                }else

                {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent toDisplayFragmentIntent = new Intent(SplashScreenActivity.this, FragmentHolderActivity.class);
                            startActivity(toDisplayFragmentIntent);
                            SplashScreenActivity.this.finish();
                        }
                    }, 4000);
        }
    }
}


