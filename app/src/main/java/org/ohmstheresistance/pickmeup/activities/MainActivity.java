package org.ohmstheresistance.pickmeup.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.database.DisplayQuotesDatabase;
import org.ohmstheresistance.pickmeup.database.NotificationTimeDatabase;
import org.ohmstheresistance.pickmeup.fragments.ChangeCardDisplayInterface;
import org.ohmstheresistance.pickmeup.fragments.CreateYourOwnMotivationFragment;
import org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment;
import org.ohmstheresistance.pickmeup.fragments.FavoriteMotivationalQuotes;
import org.ohmstheresistance.pickmeup.fragments.ShowAllQuotesFragment;
import org.ohmstheresistance.pickmeup.fragments.ShowNotification;
import org.ohmstheresistance.pickmeup.fragments.SplashScreenFragment;
import org.ohmstheresistance.pickmeup.helpers.AlertReceiver;
import org.ohmstheresistance.pickmeup.model.NotificationTime;
import org.ohmstheresistance.pickmeup.model.Quotes;
import org.ohmstheresistance.pickmeup.network.QuotesService;
import org.ohmstheresistance.pickmeup.network.RetrofitSingleton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment.quoteTextView;
import static org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment.saidByTextView;
import static org.ohmstheresistance.pickmeup.fragments.SetUpNotificationFragment.cancelNotificationButton;
import static org.ohmstheresistance.pickmeup.fragments.SetUpNotificationFragment.setUpNotificationTimeSetForTextView;
import static org.ohmstheresistance.pickmeup.fragments.SetUpNotificationFragment.setUpNotificationTimeTextView;


public class MainActivity extends AppCompatActivity implements ChangeCardDisplayInterface, TimePickerDialog.OnTimeSetListener {

    private BottomNavigationView bottomNavigationView;
    Calendar calendar;

    private NotificationTimeDatabase notificationTimeDatabase;
    private DisplayQuotesDatabase displayQuotesDatabase;

    private List<Quotes> displayQuotesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        notificationTimeDatabase =  NotificationTimeDatabase.getInstance(getApplicationContext());
        displayQuotesDatabase = DisplayQuotesDatabase.getInstance(getApplicationContext());

        calendar = Calendar.getInstance();

        onNewIntent(getIntent());

        loadBeginningFragment();

        getDisplayQuoteData();

    }

    private void loadBeginningFragment() {

        SplashScreenFragment splashScreenFragment = new SplashScreenFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, splashScreenFragment);
        fragmentTransaction.commit();
    }

    private void inflateFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment clickedNavTabFragment;

            int menuId = item.getItemId();

            switch (menuId) {
                case R.id.navigation_home:

                    clickedNavTabFragment = new DisplayQuotesFragment();
                    inflateFragment(clickedNavTabFragment);

                    break;

                case R.id.navigation_show_all:

                    clickedNavTabFragment = new ShowAllQuotesFragment();
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);


        startAlarm(calendar);
        updateTimeText(calendar);

        cancelNotificationButton.setVisibility(View.VISIBLE);


    }

    private void startAlarm(Calendar c) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlertReceiver.class);
        PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, notificationPendingIntent);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), notificationPendingIntent);
    }


    private void updateTimeText(Calendar c) {

        String timeForNotification = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        setUpNotificationTimeTextView.setText(timeForNotification);
        setUpNotificationTimeSetForTextView.setText("Daily notification set for: \n" + timeForNotification);

        notificationTimeDatabase.updateNotificationTime(NotificationTime.from(timeForNotification));
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {

            boolean notificationBoolean = intent.getBooleanExtra("FromNotification", true);

            if (notificationBoolean) {

                Fragment fragment = new ShowNotification();
                inflateFragment(fragment);
            }

            else{

                loadBeginningFragment();
            }
        }
    }

    private void getDisplayQuoteData() {

        displayQuotesList = new ArrayList<>();

        Retrofit quotesRetrofit = RetrofitSingleton.getRetrofitInstance();
        QuotesService quotesService = quotesRetrofit.create(QuotesService.class);
        quotesService.getQuotes().enqueue(new Callback<List<Quotes>>() {

            @Override
            public void onResponse(Call<List<Quotes>> call, Response<List<Quotes>> response) {

                displayQuotesList = response.body();

                if (displayQuotesList == null) {
                    Toast.makeText(getApplicationContext(), "Unable To Display Empty List", Toast.LENGTH_LONG).show();
                    return;
                }

                int index = 0;

                for (int i = 0; i <= displayQuotesList.size() - 1; i++){


                    Quotes displayQuotes = displayQuotesList.get(index);

                    String quote = displayQuotes.getQuote();
                    String quoteSaidBy = displayQuotes.getSaidby();

                    displayQuotesDatabase.addDisplayQuotes(Quotes.from(quote, quoteSaidBy));

                    index++;
                }



            }

            @Override
            public void onFailure(Call<List<Quotes>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Quote Retrofit Call Failed", Toast.LENGTH_LONG).show();
                Log.d("DISPLAY ALL QUOTES", "Quote Retrofit Call Failed: " + t.getMessage());
            }

        });

    }
}
