package org.ohmstheresistance.pickmeup.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.model.Quotes;
import org.ohmstheresistance.pickmeup.network.QuotesService;
import org.ohmstheresistance.pickmeup.network.RetrofitSingleton;
import org.ohmstheresistance.pickmeup.recyclerview.ShowAllQuotesAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.support.constraint.Constraints.TAG;


public class ShowAllQuotesFragment extends Fragment{

    private View rootView;
    private List<Quotes> showAllQuotesList;

    private RecyclerView showAllQuotesRecyclerView;
    private ShowAllQuotesAdapter showAllQuotesAdapter;


    public ShowAllQuotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_show_all_quotes, container, false);

        showAllQuotesRecyclerView = rootView.findViewById(R.id.showall_quotes_recycler_view);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAllQuotes();

    }


    private void getAllQuotes() {

        showAllQuotesList = new ArrayList<>();

        Retrofit quotesRetrofit = RetrofitSingleton.getRetrofitInstance();
        QuotesService quotesService = quotesRetrofit.create(QuotesService.class);
        quotesService.getQuotes().enqueue(new Callback<List<Quotes>>() {

            @Override
            public void onResponse(Call<List<Quotes>> call, Response<List<Quotes>> response) {

                showAllQuotesList = response.body();
                Collections.shuffle(showAllQuotesList);

                if (showAllQuotesList == null) {
                    Toast.makeText(getContext(), "Unable To Display Empty List", Toast.LENGTH_LONG).show();
                    return;
                }

                showAllQuotesAdapter = new ShowAllQuotesAdapter(showAllQuotesList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

                showAllQuotesRecyclerView.setLayoutManager(linearLayoutManager);
                showAllQuotesRecyclerView.setAdapter(showAllQuotesAdapter);

            }

            @Override
            public void onFailure(Call<List<Quotes>> call, Throwable t) {

                Toast.makeText(getContext(), "Quote Retrofit Call Failed", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Quote Retrofit Call Failed: " + t.getMessage());
            }

        });

    }

}
