package org.ohmstheresistance.pickmeup.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ohmstheresistance.pickmeup.R;


public class CreateYourOwnMotivationFragment extends Fragment {

    private View rootView;
    private FloatingActionButton createQuoteFab;
    private EditText quoteEditText;
    private TextView dateMadeTextView;
    private Button saveButton;
    private CardView createdQuoteCardView;

    public CreateYourOwnMotivationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_create_your_own_quote, container, false);

        createdQuoteCardView = rootView.findViewById(R.id.create_quote_cardview);
        createQuoteFab = rootView.findViewById(R.id.add_quote_fab_button);
        quoteEditText = rootView.findViewById(R.id.user_speech_edittext);
        saveButton = rootView.findViewById(R.id.save_speech_button);
        dateMadeTextView = rootView.findViewById(R.id.date_quote_was_made);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        createdQuoteCardView.setVisibility(View.INVISIBLE);

        createQuoteFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createdQuoteCardView.setVisibility(View.VISIBLE);

            }
        });


    }

}
