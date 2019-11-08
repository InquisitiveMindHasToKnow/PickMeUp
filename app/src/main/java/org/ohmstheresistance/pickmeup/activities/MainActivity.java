package org.ohmstheresistance.pickmeup.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Quotes.TAG";
    private List<Quotes> quotesList;
    private TextView quoteTextView;
    private TextView saidByTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteTextView = findViewById(R.id.chosen_quote_textview);
        saidByTextView = findViewById(R.id.quote_said_by_textview);

        quotesList = new ArrayList<>();

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


            }

            @Override
            public void onFailure(Call<List<Quotes>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Country Retrofit Call Failed", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Country Retrofit Call Failed: " + t.getMessage());
            }

        });
    }
}
