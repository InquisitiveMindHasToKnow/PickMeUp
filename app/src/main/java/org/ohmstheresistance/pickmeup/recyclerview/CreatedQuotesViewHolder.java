package org.ohmstheresistance.pickmeup.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.model.CreatedQuotes;

public class CreatedQuotesViewHolder extends RecyclerView.ViewHolder {

    private TextView createdQuoteTextView;
    private TextView dateQuoteCreatedTextView;

    public CreatedQuotesViewHolder(@NonNull View itemView) {
        super(itemView);

        createdQuoteTextView = itemView.findViewById(R.id.created_quote_textview);
        dateQuoteCreatedTextView = itemView.findViewById(R.id.date_quote_was_created_textview);
    }

    public void onBind(final CreatedQuotes createdQuotes) {

        final String quoteCreated = createdQuotes.getCreatedQuote();
        final String dateCreated = createdQuotes.getDateCreated();

        createdQuoteTextView.setText(quoteCreated);
        dateQuoteCreatedTextView.setText(dateCreated);
    }

}
