package org.ohmstheresistance.pickmeup.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.model.Quotes;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesViewHolder> {

    private List<Quotes> quotesList;
    private final int amountOfQuotesToShow = 6;


    public QuotesAdapter(List<Quotes> quotesList) {

        this.quotesList = quotesList;
    }

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quotes_itemview, viewGroup, false);
        return new QuotesViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder quotesViewHolder, int i) {

        final Quotes quotes = quotesList.get(i);
        quotesViewHolder.onBind(quotes);
    }

    @Override
    public int getItemCount() {

        if(quotesList.size() > amountOfQuotesToShow){
            return amountOfQuotesToShow;
        }
        else {
            return quotesList.size();
        }

    }

}
