package org.ohmstheresistance.pickmeup.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.database.CreatedQuotesDatabaseHelper;
import org.ohmstheresistance.pickmeup.model.CreatedQuotes;
import org.ohmstheresistance.pickmeup.recyclerview.CreatedQuotesAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateYourOwnMotivationFragment extends Fragment {

    private View rootView;
    private FloatingActionButton createQuoteFab;
    private EditText quoteEditText;
    private TextView dateMadeTextView;
    private TextView noQuotesYetHeaderTextView;
    private TextView quotesYouveCreatedHeaderTextView;
    private Button saveButton;
    private CardView createdQuoteCardView;
    private CreatedQuotesAdapter createdQuotesAdapter;
    private RecyclerView createdQuotesRecyclerView;
    public static String dateCreated;
    public static String quoteCreated;
    private List<CreatedQuotes> createdQuotes;
    private LinearLayout noQuotesYetLinearLayout;

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
        noQuotesYetHeaderTextView = rootView.findViewById(R.id.no_quotes_yet_header_textview);
        noQuotesYetLinearLayout = rootView.findViewById(R.id.no_quotes_yet_linear);
        quotesYouveCreatedHeaderTextView = rootView.findViewById(R.id.created_quotes_header_textview);

        createdQuotes = new ArrayList<>();

        createdQuotesDatabaseHelper = CreatedQuotesDatabaseHelper.getInstance(getContext());


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        createdQuotes = createdQuotesDatabaseHelper.getCreatedQuotes();

        createdQuotesAdapter = new CreatedQuotesAdapter(createdQuotes);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        createdQuotesRecyclerView.setLayoutManager(gridLayoutManager);
        createdQuotesRecyclerView.setAdapter(createdQuotesAdapter);


        new ItemTouchHelper(swipeLeftOrRightToDeleteQuote).attachToRecyclerView(createdQuotesRecyclerView);


        checkIfUserAlreadyMadeAQuote();

        createdQuoteCardView.setVisibility(View.INVISIBLE);


        createQuoteFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                noQuotesYetLinearLayout.setVisibility(View.INVISIBLE);
                quotesYouveCreatedHeaderTextView.setVisibility(View.INVISIBLE);
                createdQuoteCardView.setVisibility(View.VISIBLE);

                Calendar cal = Calendar.getInstance();
                final int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                final int day = cal.get(Calendar.DAY_OF_MONTH);

                month = month + 1;

                dateCreated = month + "/" + day + "/" + year;

                dateMadeTextView.setText(dateCreated);


            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(quoteEditText.getText())) {
                    Toast.makeText(getContext(), "Sorry, cannot save an empty quote.", Toast.LENGTH_LONG).show();
                }else {
                    quoteCreated = quoteEditText.getText().toString();

                    createdQuotesDatabaseHelper.addCreatedQuote(CreatedQuotes.from(quoteCreated, dateCreated));

                    noQuotesYetLinearLayout.setVisibility(View.INVISIBLE);
                    createdQuoteCardView.setVisibility(View.INVISIBLE);
                    quotesYouveCreatedHeaderTextView.setVisibility(View.VISIBLE);
                    createdQuotesRecyclerView.setVisibility(View.VISIBLE);

                    createdQuotes.add(0, CreatedQuotes.from(quoteCreated, dateCreated));
                    createdQuotesAdapter.notifyDataSetChanged();

                }

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

            createdQuotesRecyclerView.setVisibility(View.INVISIBLE);
            quotesYouveCreatedHeaderTextView.setVisibility(View.INVISIBLE);
            noQuotesYetLinearLayout.setVisibility(View.VISIBLE);

        } else {

            createdQuotesRecyclerView.setVisibility(View.VISIBLE);
            quotesYouveCreatedHeaderTextView.setVisibility(View.VISIBLE);

            createdQuotesAdapter.updateCreatedQuotesData(createdQuotes);


        }

    }

}
