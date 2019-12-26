package org.ohmstheresistance.pickmeup.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.model.Quotes;
import org.ohmstheresistance.pickmeup.network.QuotesService;
import org.ohmstheresistance.pickmeup.network.RetrofitSingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowNotification extends Fragment {

    private static final String TAG = "Quotes.TAG";


    private String notificationQuote;
    private  String notificationQuoteSaidBy;

    private View rootView;

    private TextView notificationQuoteTextView;
    private TextView notificationQuoteSaidByTextView;
    private ImageView showNotificationImageView;

    private List<Quotes>notificationQuotesList;


    public ShowNotification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_show_notification, container, false);

        notificationQuoteTextView = rootView.findViewById(R.id.sent_notification_quote_textview);
        notificationQuoteSaidByTextView = rootView.findViewById(R.id.sent_notification_quote_said_by_textview);
        showNotificationImageView = rootView.findViewById(R.id.show_notification_imageview);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getQuoteData();

        Glide.with(ShowNotification.this)
                .load(R.drawable.animatedflowers)
                .into(showNotificationImageView);


    }


    private void getQuoteData() {

        notificationQuotesList = new ArrayList<>();

        Retrofit quotesRetrofit = RetrofitSingleton.getRetrofitInstance();
        QuotesService quotesService = quotesRetrofit.create(QuotesService.class);
        quotesService.getQuotes().enqueue(new Callback<List<Quotes>>() {

            @Override
            public void onResponse(Call<List<Quotes>> call, Response<List<Quotes>> response) {

                notificationQuotesList = response.body();
                Collections.shuffle(notificationQuotesList);

                if (notificationQuotesList == null) {
                    Toast.makeText(getContext(), "Unable To Display Empty List", Toast.LENGTH_LONG).show();
                    return;
                }

                Random randomNumber = new Random();

                Quotes quoteForNotification = notificationQuotesList.get(randomNumber.nextInt(notificationQuotesList.size() - 1) + 1);

                Log.d(TAG, notificationQuotesList.get(5).getQuote());

                notificationQuote = quoteForNotification.getQuote();
                notificationQuoteSaidBy = quoteForNotification.getSaidby();

                notificationQuoteTextView.setText(notificationQuote);
                notificationQuoteSaidByTextView.setText(notificationQuoteSaidBy);

            }

            @Override
            public void onFailure(Call<List<Quotes>> call, Throwable t) {

                Toast.makeText(getContext(), "Quote Retrofit Call Failed", Toast.LENGTH_LONG).show();
            }

        });

    }

}
