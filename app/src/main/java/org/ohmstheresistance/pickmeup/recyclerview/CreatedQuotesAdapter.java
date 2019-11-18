package org.ohmstheresistance.pickmeup.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.model.CreatedQuotes;

import java.util.List;

public class CreatedQuotesAdapter extends RecyclerView.Adapter<CreatedQuotesViewHolder> {

    private List<CreatedQuotes> createdQuotesList;
    private int lastPosition = -1;

    public CreatedQuotesAdapter(List<CreatedQuotes> createdQuotesList) {

        this.createdQuotesList = createdQuotesList;
    }

    @NonNull
    @Override
    public CreatedQuotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.created_quote_itemview, viewGroup, false);
        return new CreatedQuotesViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(@NonNull CreatedQuotesViewHolder createdQuotesViewHolder, int i) {

        final CreatedQuotes createdQuotes = createdQuotesList.get(i);
        createdQuotesViewHolder.onBind(createdQuotes);

        animateAddedQuote(createdQuotesViewHolder.itemView, i);

    }

    @Override
    public int getItemCount() {

        return createdQuotesList.size();
    }


    public void updateCreatedQuotesData(List<CreatedQuotes> newCreatedQuotesList) {
        this.createdQuotesList = newCreatedQuotesList;
        notifyDataSetChanged();
    }

    private void animateAddedQuote(View viewToAnimate, int position) {

        if (position == 0) {

            viewToAnimate.startAnimation(AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.scaleup));
            lastPosition = position;
        }

    }
}
