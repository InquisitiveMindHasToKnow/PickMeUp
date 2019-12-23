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

import android.view.MenuItem;
import android.widget.TimePicker;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.fragments.ChangeCardDisplayInterface;
import org.ohmstheresistance.pickmeup.fragments.CreateYourOwnMotivationFragment;
import org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment;
import org.ohmstheresistance.pickmeup.fragments.FavoriteMotivationalQuotes;
import org.ohmstheresistance.pickmeup.fragments.ShowAllQuotesFragment;
import org.ohmstheresistance.pickmeup.fragments.ShowNotification;
import org.ohmstheresistance.pickmeup.fragments.SplashScreenFragment;
import org.ohmstheresistance.pickmeup.helpers.AlertReceiver;

import java.text.DateFormat;
import java.util.Calendar;

import static org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment.quoteTextView;
import static org.ohmstheresistance.pickmeup.fragments.DisplayQuotesFragment.saidByTextView;
import static org.ohmstheresistance.pickmeup.fragments.SetUpNotificationFragment.setUpNotificationTimeTextView;


public class MainActivity extends AppCompatActivity implements ChangeCardDisplayInterface, TimePickerDialog.OnTimeSetListener {

    private BottomNavigationView bottomNavigationView;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        calendar = Calendar.getInstance();

        onNewIntent(getIntent());

        loadBeginningFragment();

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
}
