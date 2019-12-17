package org.ohmstheresistance.pickmeup.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.database.FavoriteQuotesDatabase;
import org.ohmstheresistance.pickmeup.fragments.ChangeCardDisplayInterface;
import org.ohmstheresistance.pickmeup.model.Quotes;

import java.util.List;


public class QuotesViewHolder extends RecyclerView.ViewHolder {

    private TextView upcomingOrFavoriteQuoteTextView;
    private TextView upcomingOrFavoriteQuoteSaidByTextView;
    public ImageView favoriteUnfavoriteImageView;
    private FavoriteQuotesDatabase favoriteQuotesDatabase;

    private List<Quotes> faveQuoteList;


    public QuotesViewHolder(@NonNull View itemView) {
        super(itemView);

        upcomingOrFavoriteQuoteTextView = itemView.findViewById(R.id.upcoming_quote_textview);
        upcomingOrFavoriteQuoteSaidByTextView = itemView.findViewById(R.id.upcoming_quote_said_by_textview);
        favoriteUnfavoriteImageView = itemView.findViewById(R.id.favorite_unfavorite_imageview);
        favoriteQuotesDatabase = FavoriteQuotesDatabase.getInstance(itemView.getContext());

        faveQuoteList = favoriteQuotesDatabase.getFavorites();
    }

    public void onBind(final Quotes quotes) {


        final String upcomingOrFavoriteQuote = quotes.getQuote();
        final String upcomingOrFavoriteQuoteSaidBy = quotes.getSaidby();

        final boolean isFavorite = favoriteQuotesDatabase.isFavorite(upcomingOrFavoriteQuote);
        favoriteUnfavoriteImageView.setImageResource(isFavorite ? R.drawable.favorite_checked : R.drawable.favorite_unchecked);

        upcomingOrFavoriteQuoteTextView.setText(upcomingOrFavoriteQuote);
        upcomingOrFavoriteQuoteSaidByTextView.setText(upcomingOrFavoriteQuoteSaidBy);

        favoriteUnfavoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isFavorite = favoriteQuotesDatabase.isFavorite(upcomingOrFavoriteQuote);

                if(isFavorite){

                    favoriteUnfavoriteImageView.setImageResource(R.drawable.favorite_unchecked);
                    favoriteQuotesDatabase.deleteFavorite(upcomingOrFavoriteQuote);

                }else {

                    favoriteUnfavoriteImageView.setImageResource(R.drawable.favorite_checked);
                    favoriteQuotesDatabase.addFavorite(Quotes.from(upcomingOrFavoriteQuote, upcomingOrFavoriteQuoteSaidBy));
                }
            }
        });

    }

}