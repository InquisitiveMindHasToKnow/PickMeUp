package org.ohmstheresistance.pickmeup.fragments;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    private MenuItem menuItem;

    private ConstraintLayout favoriteCountriesConstraintLayout;

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

        favoriteCountriesConstraintLayout = rootView.findViewById(R.id.favorite_quotes_constraint_layout);

        favoriteQuotesAdapter = new FavoriteQuotesAdapter(favoritesQuotesList);
        favoriteQuotesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteQuotesRecyclerView.setAdapter(favoriteQuotesAdapter);
        new ItemTouchHelper(swipeLeftOrRightToDeleteFavorites).attachToRecyclerView(favoriteQuotesRecyclerView);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        favoriteQuotesDatabase = new FavoriteQuotesDatabase(getContext());
        favoritesQuotesList = favoriteQuotesDatabase.getFavorites();

        checkWhatHeaderToUse();

    }

    ItemTouchHelper.SimpleCallback swipeLeftOrRightToDeleteFavorites = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
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

    private void checkWhatHeaderToUse() {

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (!favoritesQuotesList.isEmpty())
            inflater.inflate(R.menu.clear_favorites_menu, menu);
        menuItem = menu.findItem(R.id.remove_all_favorite_quotes);
    }


    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.remove_all_favorite_quotes:

                clearAllFavoriteQuotes();

                break;
        }
        return true;
    }

    private void clearAllFavoriteQuotes() {

        if (favoritesQuotesList.isEmpty()) {

            Snackbar noFavoritesToDeleteSnackbar = Snackbar.make(favoriteCountriesConstraintLayout, "Nothing to delete.", Snackbar.LENGTH_LONG);
            View snackbarView = noFavoritesToDeleteSnackbar.getView();
            TextView snackBarTextView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);

            snackBarTextView.setBackgroundResource(R.drawable.custom_snackbar);
            snackBarTextView.setTextColor(getResources().getColor(R.color.textColor));
            snackbarView.setBackgroundColor(Color.TRANSPARENT);

            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
            layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = Gravity.CENTER | Gravity.BOTTOM;

            layoutParams.bottomMargin = 200;

            snackbarView.setLayoutParams(layoutParams);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                snackBarTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else
                snackBarTextView.setGravity(Gravity.CENTER_HORIZONTAL);

            noFavoritesToDeleteSnackbar.show();
        } else {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), R.style.UserInfoDialog);

            alertDialog.setTitle("Removing all favorites");
            alertDialog.setMessage("Are you sure you want to remove all your favorite quotes?");
            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            favoriteQuotesDatabase.clearFavoriteQuotesDatabase();
                            favoriteQuotesAdapter.setData(favoritesQuotesList);

                            favoriteQuotesRecyclerView.setVisibility(View.INVISIBLE);
                            favoriteQuotesHeaderTextView.setVisibility(View.INVISIBLE);
                            noFavoritesYetHeaderTextView.setVisibility(VISIBLE);
                            menuItem.setVisible(false);


                        }

                    });
            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            alertDialog.show();
        }

    }

}