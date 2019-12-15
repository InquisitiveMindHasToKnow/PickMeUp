package org.ohmstheresistance.pickmeup.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.model.Quotes;

import java.util.List;

public class ShowAllQuotesAdapter extends RecyclerView.Adapter<QuotesViewHolder> {

    private List<Quotes> showAllQuotesList;

    public ShowAllQuotesAdapter(List<Quotes> showAllQuotesList) {

        this.showAllQuotesList = showAllQuotesList;
    }

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quotes_itemview, viewGroup, false);
        return new QuotesViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder quotesViewHolder, int i) {

        final Quotes quotes = showAllQuotesList.get(i);
        quotesViewHolder.onBind(quotes);
    }

    @Override
    public int getItemCount() {
        return showAllQuotesList.size();
    }
}
