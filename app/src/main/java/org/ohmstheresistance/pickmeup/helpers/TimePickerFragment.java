package org.ohmstheresistance.pickmeup.helpers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;

import org.ohmstheresistance.pickmeup.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimePickerFragment extends DialogFragment {

    public static void setNotificationTime(Context context, final TextView notificationTimeTextview) {

        final Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteOfDay = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.CustomTimeDialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourPicked, int minutePicked) {

                Time time = new Time(hourPicked, minutePicked, 0);

                SimpleDateFormat simpleDateFormat = new
                        SimpleDateFormat("hh:mm aa", Locale.getDefault());
                String notificationTime = simpleDateFormat.format(time);
                notificationTimeTextview.setText(notificationTime);
            }
        }, hourOfDay, minuteOfDay, false);

        timePickerDialog.show();
    }
}


