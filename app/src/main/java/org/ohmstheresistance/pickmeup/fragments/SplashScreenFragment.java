package org.ohmstheresistance.pickmeup.fragments;


import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.ohmstheresistance.pickmeup.R;



public class SplashScreenFragment extends Fragment {

    public static final String KEY_PREFS_FIRST_LAUNCH = "first_launch";

    private View rootView;
    private TextView splashScreenTextView;

    public SplashScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        splashScreenTextView = rootView.findViewById(R.id.pick_me_up_splash_textview);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));

        splashScreenTextView.setPaintFlags(splashScreenTextView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


        Animation blinkingAnimation = new AlphaAnimation(0.0f, 1.0f);
        blinkingAnimation.setDuration(100);
        blinkingAnimation.setStartOffset(300);
        blinkingAnimation.setRepeatMode(Animation.REVERSE);
        blinkingAnimation.setRepeatCount(Animation.INFINITE);
        splashScreenTextView.startAnimation(blinkingAnimation);


        final SharedPreferences firstLaunchCheck = PreferenceManager.getDefaultSharedPreferences(getContext());

        if (firstLaunchCheck.getBoolean(KEY_PREFS_FIRST_LAUNCH, true)) {

            new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                UsersNameFragment usersNameFragment = new UsersNameFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_container, usersNameFragment);
                fragmentTransaction.commit();

                firstLaunchCheck.edit().putBoolean(KEY_PREFS_FIRST_LAUNCH, false).apply();

            }
        }, 2000);


            }else{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    DisplayQuotesFragment displayQuotesFragment = new DisplayQuotesFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_container, displayQuotesFragment);
                    fragmentTransaction.commit();

                }
            }, 2000);
        }
    }
}
