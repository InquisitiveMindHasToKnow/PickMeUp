package org.ohmstheresistance.pickmeup.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.database.NotificationTimeDatabase;
import org.ohmstheresistance.pickmeup.database.UserInfoDatabaseHelper;
import org.ohmstheresistance.pickmeup.helpers.TimePickerFragment;
import org.ohmstheresistance.pickmeup.model.NotificationTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SetUpNotificationFragment extends Fragment {

    private View rootView;
    private TextView setUpNotificationTextView;
    public static TextView setUpNotificationTimeTextView;
    public static TextView setUpNotificationTimeSetForTextView;
    private Calendar calendar;

    private NotificationTimeDatabase notificationTimeDatabase;
    private String dailynotificationTime;
    private String currentTime;


    public SetUpNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_set_up_notification, container, false);

        setUpNotificationTextView = rootView.findViewById(R.id.set_up_notification_textview);
        setUpNotificationTimeTextView = rootView.findViewById(R.id.set_up_notification_time_textview);
        setUpNotificationTimeSetForTextView = rootView.findViewById(R.id.set_up_notification_time_set_for_textview);

        calendar = Calendar.getInstance();

        notificationTimeDatabase = NotificationTimeDatabase.getInstance(getContext());

        currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
        setUpNotificationTimeTextView.setText(currentTime);

        notificationTimeDatabase.setUpNotificationTime(NotificationTime.from(currentTime));

        dailynotificationTime = notificationTimeDatabase.getNotificationTime().get(0).getNotificationTime();


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpNotificationTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getSupportFragmentManager(), "notificationTimePicker");
            }

        });


        if(notificationTimeDatabase != null){

            setUpNotificationTimeSetForTextView.setText("Daily notification time set for: \n"+ dailynotificationTime);
        }

    }
}
