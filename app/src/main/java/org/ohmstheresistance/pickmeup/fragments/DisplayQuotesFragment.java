package org.ohmstheresistance.pickmeup.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.model.Quotes;
import org.ohmstheresistance.pickmeup.network.QuotesService;
import org.ohmstheresistance.pickmeup.network.RetrofitSingleton;
import org.ohmstheresistance.pickmeup.recyclerview.QuotesAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DisplayQuotesFragment extends Fragment {

    private static final String SHARED_PREFS_KEY = "userNameSharedPref";


    private View rootView;
    private static final String TAG = "Quotes.TAG";
    private List<Quotes> quotesList;
    public static TextView quoteTextView;
    public static TextView saidByTextView;
    private TextView greetingTextView;
    private CardView quoteCardView;
    private TextView userNameTextView;


    private RecyclerView upcomingQuotesRecyclerView;
    private QuotesAdapter quotesAdapter;

    private String usersName;

    public DisplayQuotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.fragment_display_quotes, container, false);

        quoteTextView = rootView.findViewById(R.id.chosen_quote_textview);
        saidByTextView = rootView.findViewById(R.id.quote_said_by_textview);
        quoteCardView = rootView.findViewById(R.id.quote_cardview);
        greetingTextView = rootView.findViewById(R.id.greeting_textview);
        userNameTextView = rootView.findViewById(R.id.user_name_textview);
        upcomingQuotesRecyclerView = rootView.findViewById(R.id.upcoming_quotes_recycler_view);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getQuoteData();

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            greetingTextView.setText(getString(R.string.good_morning));

        }else if(timeOfDay >= 12 && timeOfDay < 16){
            greetingTextView.setText(getString(R.string.good_afternoon));

        }else if(timeOfDay >= 16 && timeOfDay < 21){
            greetingTextView.setText(getString(R.string.good_evening));

        }else if(timeOfDay >= 21 && timeOfDay < 24){
            greetingTextView.setText(getString(R.string.good_night));
        }


        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS_KEY, 0);
        preferences.getString("USER_NAME", " ");

        if (getArguments() != null) {

            // usersName = getArguments().getString("USER_NAME", " ");
            usersName = preferences.getString("USER_NAME", " ");
            userNameTextView.setText(usersName);


        }else {


            userNameTextView.setText(" ");
            Toast.makeText(getContext(), "Bundle is empty, son", Toast.LENGTH_LONG).show();
        }


    }


    private void getQuoteData() {

        quotesList = new ArrayList<>();

        Retrofit quotesRetrofit = RetrofitSingleton.getRetrofitInstance();
        QuotesService quotesService = quotesRetrofit.create(QuotesService.class);
        quotesService.getQuotes().enqueue(new Callback<List<Quotes>>() {

            @Override
            public void onResponse(Call<List<Quotes>> call, Response<List<Quotes>> response) {

                quotesList = response.body();
                Collections.shuffle(quotesList);

                if (quotesList == null) {
                    Toast.makeText(getContext(), "Unable To Display Empty List", Toast.LENGTH_LONG).show();
                    return;
                }

                quotesAdapter = new QuotesAdapter(quotesList);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                upcomingQuotesRecyclerView.setLayoutManager(gridLayoutManager);
                upcomingQuotesRecyclerView.setAdapter(quotesAdapter);

                Random randomNumber = new Random();
                Quotes quoteToDisplay = quotesList.get(randomNumber.nextInt(quotesList.size() - 1) + 1);

                Log.d(TAG, quotesList.get(6).getQuote());

                quoteTextView.setText(quoteToDisplay.getQuote());
                saidByTextView.setText(quoteToDisplay.getSaidby());

                changeQuote();


            }

            @Override
            public void onFailure(Call<List<Quotes>> call, Throwable t) {

                Toast.makeText(getContext(), "Quote Retrofit Call Failed", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Quote Retrofit Call Failed: " + t.getMessage());
            }

        });

    }

    public static DisplayQuotesFragment getInstance(String userName) {
        DisplayQuotesFragment displayQuotesFragment = new DisplayQuotesFragment();

        Bundle args = new Bundle();
        args.putString("USER_NAME", userName);
        displayQuotesFragment.setArguments(args);
        return displayQuotesFragment;
    }

    private void changeQuote() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

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
        }, 60000);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }


}

