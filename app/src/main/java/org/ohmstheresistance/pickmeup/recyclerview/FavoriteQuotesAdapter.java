package org.ohmstheresistance.pickmeup.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.model.Quotes;

import java.util.List;

public class FavoriteQuotesAdapter extends RecyclerView.Adapter<QuotesViewHolder> {

    private List<Quotes> favoriteQuotesList;


    public FavoriteQuotesAdapter(List<Quotes> favoriteQuotesList) {

        this.favoriteQuotesList = favoriteQuotesList;
    }

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quotes_itemview, viewGroup, false);
        return new QuotesViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder quotesViewHolder, int i) {

        final Quotes favoriteQuotes = favoriteQuotesList.get(i);
        quotesViewHolder.onBind(favoriteQuotes);

    }

    @Override
    public int getItemCount() {
        return favoriteQuotesList.size();
    }

    public void setData(List<Quotes> favoriteQuotes) {
        this.favoriteQuotesList = favoriteQuotes;
        notifyDataSetChanged();
    }

}
