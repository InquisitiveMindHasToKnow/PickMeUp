package org.ohmstheresistance.pickmeup.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;

import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.model.Quotes;
import org.ohmstheresistance.pickmeup.network.QuotesService;
import org.ohmstheresistance.pickmeup.network.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "Quotes.TAG";
    private List<Quotes> quotesList;
    private TextView quoteTextView;
    private TextView saidByTextView;
    private CardView quoteCardView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteTextView = findViewById(R.id.chosen_quote_textview);
        saidByTextView = findViewById(R.id.quote_said_by_textview);
        quoteCardView = findViewById(R.id.quote_cardview);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
      //  bottomNavigationView.setBackgroundColor(Color.parseColor("#F7E633"));

        quotesList = new ArrayList<>();

        getQuoteData();

    }

    private void getQuoteData(){

        Retrofit quotesRetrofit = RetrofitSingleton.getRetrofitInstance();
        QuotesService quotesService = quotesRetrofit.create(QuotesService.class);
        quotesService.getQuotes().enqueue(new Callback<List<Quotes>>() {

            @Override
            public void onResponse(Call<List<Quotes>> call, Response<List<Quotes>> response) {

                quotesList = response.body();

                if (quotesList == null) {
                    Toast.makeText(getApplicationContext(), "Unable To Display Empty List", Toast.LENGTH_LONG).show();
                    return;
                }

                Random randomNumber = new Random();
                Quotes quoteToDisplay = quotesList.get(randomNumber.nextInt(quotesList.size() - 1) + 1);

                Log.d(TAG, quotesList.get(5).getQuote());

                quoteTextView.setText(quoteToDisplay.getQuote());
                saidByTextView.setText(quoteToDisplay.getSaidby());

                changeQuote();

            }

            @Override
            public void onFailure(Call<List<Quotes>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Country Retrofit Call Failed", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Country Retrofit Call Failed: " + t.getMessage());
            }

        });

    }

    private void changeQuote(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //quoteCardView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade));

                final ObjectAnimator quoteCardObjectAnimator = ObjectAnimator.ofFloat(quoteCardView, "scaleX", 1f, 0f);
                final ObjectAnimator quoteCardObjectAnimator1 = ObjectAnimator.ofFloat(quoteCardView, "scaleX", 0f, 1f);
                quoteCardObjectAnimator.setInterpolator(new DecelerateInterpolator());
                quoteCardObjectAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
                quoteCardObjectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        quoteCardObjectAnimator.setDuration(1000);
                        quoteCardObjectAnimator1.setDuration(1000);

                        quoteCardObjectAnimator1.start();
                    }
                });
                quoteCardObjectAnimator.start();


                getQuoteData();
            }
        },10000);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment clickedNavTabFragment;

            int mMenuId = item.getItemId();


            switch (mMenuId) {
                case R.id.navigation_home:
//                clickedNavTabFragment = new UserProfileFragment();
//                inflateFragment(clickedNavTabFragment);
                    break;

                case R.id.navigation_create:

//                clickedNavTabFragment = new ViewAllPrivateMessagesFragment();
//                inflateFragment(clickedNavTabFragment);
                    break;

                case R.id.navigation_favorites:

//                clickedNavTabFragment = new UserViewPagerFragment();
//                inflateFragment(clickedNavTabFragment);
                    break;

            }

            return true;
        }
    };

}
