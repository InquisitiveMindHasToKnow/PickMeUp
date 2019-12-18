package org.ohmstheresistance.pickmeup.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.fragments.ChangeCardDisplayInterface;
import org.ohmstheresistance.pickmeup.fragments.CreateYourOwnMotivationFragment;
import org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment;
import org.ohmstheresistance.pickmeup.fragments.FavoriteMotivationalQuotes;
import org.ohmstheresistance.pickmeup.fragments.ShowAllQuotesFragment;
import org.ohmstheresistance.pickmeup.fragments.UpdateFavoriteMotivationalQuotes;

import static org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment.quoteTextView;
import static org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment.saidByTextView;


public class FragmentHolderActivity extends AppCompatActivity implements ChangeCardDisplayInterface, UpdateFavoriteMotivationalQuotes {

    private BottomNavigationView bottomNavigationView;

    final DisplayQuotesFragment displayQuotesFragment = new DisplayQuotesFragment();
    final ShowAllQuotesFragment showAllQuotesFragment = new ShowAllQuotesFragment();
    final CreateYourOwnMotivationFragment createYourOwnMotivationFragment = new CreateYourOwnMotivationFragment();
    final FavoriteMotivationalQuotes favoriteMotivationalQuotes = new FavoriteMotivationalQuotes();

    final FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment activeFragment = displayQuotesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.main_fragment_container, favoriteMotivationalQuotes, "favoriteMotivationalQuotes").hide(favoriteMotivationalQuotes).commit();
        fragmentManager.beginTransaction().add(R.id.main_fragment_container, createYourOwnMotivationFragment, "createYourOwnMotivationFragment").hide(createYourOwnMotivationFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_fragment_container, showAllQuotesFragment, "showAllQuotesFragment").hide(showAllQuotesFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_fragment_container, displayQuotesFragment, "displayQuotesFragment").commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            int menuId = item.getItemId();

            switch (menuId) {
                case R.id.navigation_home:

                    fragmentManager.beginTransaction().hide(activeFragment).show(displayQuotesFragment).commit();
                    activeFragment = displayQuotesFragment;

                    break;

                case R.id.navigation_show_all:

                    fragmentManager.beginTransaction().hide(activeFragment).show(showAllQuotesFragment).commit();
                    activeFragment = showAllQuotesFragment;

                    break;

                case R.id.navigation_create:

                    fragmentManager.beginTransaction().hide(activeFragment).show(createYourOwnMotivationFragment).commit();
                    activeFragment = createYourOwnMotivationFragment;

                    break;

                case R.id.navigation_favorites:

                    fragmentManager.beginTransaction().hide(activeFragment).show(favoriteMotivationalQuotes).commit();
                    activeFragment = favoriteMotivationalQuotes;

                    updateFavoriteQuotesAdapter();
                    break;

            }

            return true;
        }
    };


    @Override
    public void updateMainQuoteDisplayed(String quote, String saidBy) {

        quoteTextView.setText(quote);
        saidByTextView.setText(saidBy);
    }


    @Override
    public void updateFavoriteQuotesAdapter() {
        favoriteMotivationalQuotes.refreshAdapter();
    }
}
