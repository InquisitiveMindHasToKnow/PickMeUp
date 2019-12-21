package org.ohmstheresistance.pickmeup.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class TestNotificationActivity extends AppCompatActivity {


    private static final String TAG = "Quotes.TAG";


    private String notificationQuote;
    private  String notificationQuoteSaidBy;

    private TextView notificationQuoteTextView;
    private TextView notificationQuoteSaidByTextView;

    List<Quotes> notificationQuotesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_notification);

        notificationQuoteTextView = findViewById(R.id.sent_notification_quote_textview);
        notificationQuoteSaidByTextView = findViewById(R.id.sent_notification_quote_said_by_textview);

        getQuoteData();

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
                    Toast.makeText(getApplicationContext(), "Unable To Display Empty List", Toast.LENGTH_LONG).show();
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

                Toast.makeText(getApplicationContext(), "Quote Retrofit Call Failed", Toast.LENGTH_LONG).show();
            }

        });

    }
}
