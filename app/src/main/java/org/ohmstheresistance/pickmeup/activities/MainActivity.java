package org.ohmstheresistance.pickmeup.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.fragments.ChangeCardDisplayInterface;
import org.ohmstheresistance.pickmeup.fragments.CreateYourOwnMotivationFragment;
import org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment;
import org.ohmstheresistance.pickmeup.fragments.FavoriteMotivationalQuotes;

import static org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment.quoteTextView;
import static org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment.saidByTextView;


public class MainActivity extends AppCompatActivity implements ChangeCardDisplayInterface {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //  bottomNavigationView.setBackgroundColor(Color.parseColor("#F7E633"));

        loadBeginningFragment();
    }

    private void loadBeginningFragment() {

        DisplayQuotesFragment displayQuotesFragment = new DisplayQuotesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, displayQuotesFragment);
        fragmentTransaction.commit();
    }

    private void inflateFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment clickedNavTabFragment;

            int mMenuId = item.getItemId();


            switch (mMenuId) {
                case R.id.navigation_home:
                clickedNavTabFragment = new DisplayQuotesFragment();
                inflateFragment(clickedNavTabFragment);
                    break;

                case R.id.navigation_create:

                clickedNavTabFragment = new CreateYourOwnMotivationFragment();
                inflateFragment(clickedNavTabFragment);
                    break;

                case R.id.navigation_favorites:

                clickedNavTabFragment = new FavoriteMotivationalQuotes();
                inflateFragment(clickedNavTabFragment);
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

}
