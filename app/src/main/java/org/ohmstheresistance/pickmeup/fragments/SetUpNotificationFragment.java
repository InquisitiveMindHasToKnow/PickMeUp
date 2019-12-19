package org.ohmstheresistance.pickmeup.fragments;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.helpers.AlertReceiver;
import org.ohmstheresistance.pickmeup.helpers.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class SetUpNotificationFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private View rootView;
    private TextView setUpNotificationTextView;
    private static TextView setUpNotificationTimeTextView;

    public SetUpNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_set_up_notification, container, false);

        setUpNotificationTextView = rootView.findViewById(R.id.set_up_notification_textview);
        setUpNotificationTimeTextView = rootView.findViewById(R.id.set_up_notification_time_textview);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpNotificationTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerFragment.setNotificationTime(getContext(), setUpNotificationTimeTextView);

            }

        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        startAlarm(calendar);
        updateTimeText(calendar);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


    private void updateTimeText(Calendar c) {

        String timeForNotification = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        setUpNotificationTimeTextView.setText(timeForNotification);
    }
}
