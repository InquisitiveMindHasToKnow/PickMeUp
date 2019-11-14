package org.ohmstheresistance.pickmeup.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.fragments.ChangeCardDisplayInterface;
import org.ohmstheresistance.pickmeup.model.Quotes;

public class QuotesViewHolder extends RecyclerView.ViewHolder {

    private TextView upcomingQuoteTextView;
    private TextView upcomingQuoteSaidByTextView;

    public QuotesViewHolder(@NonNull View itemView) {
        super(itemView);

        upcomingQuoteTextView = itemView.findViewById(R.id.upcoming_quote_textview);
        upcomingQuoteSaidByTextView = itemView.findViewById(R.id.upcoming_quote_said_by_textview);
    }

    public void onBind(final Quotes quotes) {

        final String upcomingQuote = quotes.getQuote();
        final String upcomingQuoteSaidBy = quotes.getSaidby();

        upcomingQuoteTextView.setText(upcomingQuote);
        upcomingQuoteSaidByTextView.setText(upcomingQuoteSaidBy);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ChangeCardDisplayInterface changeCardDisplayInterface = (ChangeCardDisplayInterface) itemView.getContext();
                changeCardDisplayInterface.updateMainQuoteDisplayed(upcomingQuote, upcomingQuoteSaidBy);

                    // itemView.startAnimation(AnimationUtils.loadAnimation(itemView.getContext(), R.anim.fade));
                }
        });
    }

}