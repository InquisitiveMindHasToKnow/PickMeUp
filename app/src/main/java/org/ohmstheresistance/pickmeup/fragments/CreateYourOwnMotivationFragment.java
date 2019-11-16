package org.ohmstheresistance.pickmeup.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.database.CreatedQuotesDatabaseHelper;
import org.ohmstheresistance.pickmeup.model.CreatedQuotes;
import org.ohmstheresistance.pickmeup.recyclerview.CreatedQuotesAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;


public class CreateYourOwnMotivationFragment extends Fragment {

    private View rootView;
    private FloatingActionButton createQuoteFab;
    private EditText quoteEditText;
    private TextView dateMadeTextView;
    private Button saveButton;
    private CardView createdQuoteCardView;
    private CreatedQuotesAdapter createdQuotesAdapter;
    private RecyclerView createdQuotesRecyclerView;
    public static String dateCreated;
    public static String quoteCreated;
    private List<CreatedQuotes> createdQuotes;

    CreatedQuotesDatabaseHelper createdQuotesDatabaseHelper;

    public CreateYourOwnMotivationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_create_your_own_quote, container, false);

        createdQuoteCardView = rootView.findViewById(R.id.create_quote_cardview);
        createQuoteFab = rootView.findViewById(R.id.add_quote_fab_button);
        quoteEditText = rootView.findViewById(R.id.created_quote_edittext);
        saveButton = rootView.findViewById(R.id.save_quote_button);
        dateMadeTextView = rootView.findViewById(R.id.date_quote_was_made);
        createdQuotesRecyclerView = rootView.findViewById(R.id.created_quotes_recycler_view);

        createdQuotes = new ArrayList<>();

        createdQuotesDatabaseHelper = CreatedQuotesDatabaseHelper.getInstance(getContext());


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        createdQuotes = createdQuotesDatabaseHelper.getCreatedQuotes();

        createdQuotesAdapter = new CreatedQuotesAdapter(createdQuotes);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        createdQuotesRecyclerView.setLayoutManager(gridLayoutManager);
        createdQuotesRecyclerView.setAdapter(createdQuotesAdapter);

        new ItemTouchHelper(swipeLeftOrRightToDeleteQuote).attachToRecyclerView(createdQuotesRecyclerView);


        checkIfUserAlreadyMadeAQuote();

        createdQuoteCardView.setVisibility(View.INVISIBLE);

        createQuoteFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createdQuoteCardView.setVisibility(View.VISIBLE);

                Calendar cal = Calendar.getInstance();
                final int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                final int day = cal.get(Calendar.DAY_OF_MONTH);

                month = month + 1;

                dateCreated = month + "/" + day + "/" + year;
                quoteCreated = quoteEditText.getText().toString();

                dateMadeTextView.setText(dateCreated);


            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkIfUserAlreadyMadeAQuote();
                createdQuotesDatabaseHelper.addCreatedQuote(CreatedQuotes.from(quoteCreated, dateCreated));

            }
        });

    }

    ItemTouchHelper.SimpleCallback swipeLeftOrRightToDeleteQuote = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            String quoteToDeleteFromDB = createdQuotes.get(viewHolder.getAdapterPosition()).getCreatedQuote();

            createdQuotesDatabaseHelper.deleteQuote(quoteToDeleteFromDB);

            createdQuotes.remove(viewHolder.getAdapterPosition());
            createdQuotesAdapter.notifyDataSetChanged();

            checkIfUserAlreadyMadeAQuote();

        }
    };

    private void checkIfUserAlreadyMadeAQuote() {


        if (createdQuotes.isEmpty()) {

            createdQuotesRecyclerView.setVisibility(GONE);
        } else {

            createdQuotesRecyclerView.setVisibility(View.VISIBLE);
           // createdQuotes = createdQuotesDatabaseHelper.getCreatedQuotes();
            createdQuotesAdapter.setData(createdQuotes);

        }

    }


}
