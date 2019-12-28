package org.ohmstheresistance.pickmeup.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.ohmstheresistance.pickmeup.R;

public class ShowNotificationFragment extends Fragment {

    public static final String NOTIFICATION_QUOTE = "NOTIFICATION QUOTE";
    public static final String NOTIFICATION_QUOTE_SAID_BY = "NOTIFICATION QUOTE SAID BY";

    private String notificationQuote;
    private String notificationQuoteSaidBy;

    private View rootView;

    private TextView notificationQuoteTextView;
    private TextView notificationQuoteSaidByTextView;
    private ImageView showNotificationImageView;

    public ShowNotificationFragment() {
        // Required empty public constructor
    }

    public static ShowNotificationFragment getInstance(String quote, String saidBy) {
        ShowNotificationFragment showNotificationFragment = new ShowNotificationFragment();

        Bundle args = new Bundle();
        args.putString(NOTIFICATION_QUOTE, quote);
        args.putString(NOTIFICATION_QUOTE_SAID_BY, saidBy);
        showNotificationFragment.setArguments(args);
        return showNotificationFragment;
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

        Glide.with(ShowNotificationFragment.this)
                .load(R.drawable.animatedflowers)
                .into(showNotificationImageView);

         getNotificationQuoteDetails();

    }


    private void getNotificationQuoteDetails() {


            if (getArguments() != null) {
                notificationQuote = getArguments().getString(NOTIFICATION_QUOTE);
                notificationQuoteSaidBy = getArguments().getString(NOTIFICATION_QUOTE_SAID_BY);

                notificationQuoteTextView.setText(notificationQuote);
                notificationQuoteSaidByTextView.setText(notificationQuoteSaidBy);
        }
    }
}
