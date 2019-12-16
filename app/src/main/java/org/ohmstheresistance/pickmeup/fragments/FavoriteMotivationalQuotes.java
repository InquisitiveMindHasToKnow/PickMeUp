package org.ohmstheresistance.pickmeup.fragments;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.database.FavoriteQuotesDatabase;
import org.ohmstheresistance.pickmeup.model.Quotes;
import org.ohmstheresistance.pickmeup.recyclerview.FavoriteQuotesAdapter;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class FavoriteMotivationalQuotes extends Fragment {



    private View rootView;
    RecyclerView favoriteQuotesRecyclerView;
    private TextView favoriteQuotesHeaderTextView;
    private TextView noFavoritesYetHeaderTextView;
    FavoriteQuotesDatabase favoriteQuotesDatabase;
    FavoriteQuotesAdapter favoriteQuotesAdapter;
    private List<Quotes> favoritesQuotesList;

    public FavoriteMotivationalQuotes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_favorite_quotes, container, false);

        favoriteQuotesRecyclerView = rootView.findViewById(R.id.favorite_quotes_recycler_view);
        favoriteQuotesHeaderTextView = rootView.findViewById(R.id.favorite_quotes_header);
        noFavoritesYetHeaderTextView = rootView.findViewById(R.id.no_favorites_yet_header);

        favoriteQuotesAdapter = new FavoriteQuotesAdapter(favoritesQuotesList);
        favoriteQuotesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteQuotesRecyclerView.setAdapter(favoriteQuotesAdapter);
        new ItemTouchHelper(swipeLeftOrRightToDeleteFavorites).attachToRecyclerView(favoriteQuotesRecyclerView);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteQuotesDatabase = new FavoriteQuotesDatabase(getContext());
        favoritesQuotesList = favoriteQuotesDatabase.getFavorites();

        checkWhatHeaderToUse();

    }

    ItemTouchHelper.SimpleCallback swipeLeftOrRightToDeleteFavorites = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            String quoteToRemoveFromDatabase = favoritesQuotesList.get(viewHolder.getAdapterPosition()).getQuote();
            Log.e("quoteToDeleteFromDB", quoteToRemoveFromDatabase);

            favoriteQuotesDatabase.deleteFavorite(quoteToRemoveFromDatabase);
            favoritesQuotesList.remove(viewHolder.getAdapterPosition());
            favoriteQuotesAdapter.notifyDataSetChanged();
            checkWhatHeaderToUse();

        }
    };

    private void checkWhatHeaderToUse(){

        if (favoritesQuotesList.isEmpty()) {
            favoriteQuotesRecyclerView.setVisibility(GONE);
            noFavoritesYetHeaderTextView.setVisibility(VISIBLE);
            favoriteQuotesHeaderTextView.setVisibility(GONE);
        } else {

            favoriteQuotesHeaderTextView.setVisibility(VISIBLE);
            favoriteQuotesRecyclerView.setVisibility(VISIBLE);
            noFavoritesYetHeaderTextView.setVisibility(View.INVISIBLE);
            favoriteQuotesAdapter.setData(favoritesQuotesList);
        }
    }
}

