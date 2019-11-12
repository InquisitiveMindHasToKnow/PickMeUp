package org.ohmstheresistance.pickmeup.fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ohmstheresistance.pickmeup.R;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class CreateYourOwnMotivationFragment extends Fragment {

    protected static final int RESULT_SPEECH = 1;

    private View rootView;
    private FloatingActionButton speechFab;
    private EditText speechTextView;
    private Button saveButton;

    public CreateYourOwnMotivationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_create_your_own_quote, container, false);

        speechFab = rootView.findViewById(R.id.activate_speech_fab_button);
        speechTextView = rootView.findViewById(R.id.user_speech_edittext);
        saveButton = rootView.findViewById(R.id.save_speech_button);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        speechFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent speechToTextIntent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                speechToTextIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(speechToTextIntent, RESULT_SPEECH);
                    speechTextView.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    speechTextView.setText(text.get(0));
                }
                break;
            }

        }
    }
}
