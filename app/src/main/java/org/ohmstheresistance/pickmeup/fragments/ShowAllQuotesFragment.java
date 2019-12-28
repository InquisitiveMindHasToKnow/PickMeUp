package org.ohmstheresistance.pickmeup.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.database.DisplayQuotesDatabase;
import org.ohmstheresistance.pickmeup.model.Quotes;
import org.ohmstheresistance.pickmeup.recyclerview.ShowAllQuotesAdapter;

import java.util.Collections;
import java.util.List;

public class ShowAllQuotesFragment extends Fragment {

    private View rootView;
    private List<Quotes> showAllQuotesList;

    private RecyclerView showAllQuotesRecyclerView;
    private ShowAllQuotesAdapter showAllQuotesAdapter;

    private DisplayQuotesDatabase displayQuotesDatabase;

    public ShowAllQuotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_show_all_quotes, container, false);

        showAllQuotesRecyclerView = rootView.findViewById(R.id.showall_quotes_recycler_view);

        displayQuotesDatabase = DisplayQuotesDatabase.getInstance(getContext());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getQuoteDataFromDatabase();

    }

    private void getQuoteDataFromDatabase() {

        showAllQuotesList = displayQuotesDatabase.getAllQuotes();

        Collections.shuffle(showAllQuotesList);

        if (displayQuotesDatabase == null) {
            Toast.makeText(getContext(), "Unable To Display Empty List", Toast.LENGTH_LONG).show();
            return;
        }

        showAllQuotesAdapter = new ShowAllQuotesAdapter(showAllQuotesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        showAllQuotesRecyclerView.setLayoutManager(linearLayoutManager);
        showAllQuotesRecyclerView.setAdapter(showAllQuotesAdapter);

    }

    }
