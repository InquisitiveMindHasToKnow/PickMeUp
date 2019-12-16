package org.ohmstheresistance.pickmeup.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.model.Quotes;

public class FavoriteQuotesViewHolder extends RecyclerView.ViewHolder {

    private TextView favoriteQuoteTextView;
    private TextView favoriteQuoteSaidByTextView;

    public FavoriteQuotesViewHolder(@NonNull View itemView) {
        super(itemView);

        favoriteQuoteTextView = itemView.findViewById(R.id.favorite_quote_textview);
        favoriteQuoteSaidByTextView = itemView.findViewById(R.id.favorite_quote_said_by_textview);

    }

    public void onBind(final Quotes quotes) {


        final String favoriteQuote = quotes.getQuote();
        final String favoriteQuoteSaidBy = quotes.getSaidby();

        favoriteQuoteTextView.setText(favoriteQuote);
        favoriteQuoteSaidByTextView.setText(favoriteQuoteSaidBy);

    }
}
