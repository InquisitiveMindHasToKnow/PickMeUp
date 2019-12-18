package org.ohmstheresistance.pickmeup.fragments;


import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import org.ohmstheresistance.pickmeup.R;

import java.util.Calendar;


public class SetUpNotificationFragment extends Fragment {

    private View rootView;
    private TextView setUpNotificationTextView;
    private TextView setUpNotificationTimeTextView;

    private TimePickerDialog.OnTimeSetListener chooseATimeListener;
    private String timeOfDay;
    private String time;

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

        Calendar c = Calendar.getInstance();
        final int hourChosen = c.get(Calendar.HOUR_OF_DAY);
        final int minuteChosen = c.get(Calendar.MINUTE);

        setUpNotificationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog dialog = new TimePickerDialog(getContext(),R.style.CustomTimeDialog,  new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay == 0) {

                            hourOfDay += 12;
                            timeOfDay = " AM";
                        } else if (hourOfDay == 12) {

                            timeOfDay = " PM";

                        } else if (hourOfDay > 12) {

                            hourOfDay -= 12;
                            timeOfDay = " PM";

                        } else {
                            timeOfDay = " AM";
                        }

                        if (minute < 10) {
                            time = hourOfDay + ":0" + minute + timeOfDay;
                        } else
                            time = hourOfDay + ":" + minute + timeOfDay;
                        setUpNotificationTimeTextView.setText(time);

                    }
                }, hourChosen, minuteChosen, false);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        chooseATimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {


                String time = hourOfDay + " : " + minute;
                setUpNotificationTimeTextView.setText(time);

            }
        };


    }
}
