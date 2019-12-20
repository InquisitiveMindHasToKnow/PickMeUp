package org.ohmstheresistance.pickmeup.helpers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.activities.MainActivity;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), R.style.CustomTimeDialog, (MainActivity)getActivity(), hour, minute, false);
        }
    }




















//    public static void setNotificationTime(final Context context, final TextView notificationTimeTextview) {
//
//        final Calendar calendar = Calendar.getInstance();
//        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
//        int minuteOfDay = calendar.get(Calendar.MINUTE);
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.CustomTimeDialog, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int hourPicked, int minutePicked) {
//
//                Time time = new Time(hourPicked, minutePicked, 0);
//
//                SimpleDateFormat simpleDateFormat = new
//                        SimpleDateFormat("hh:mm aa", Locale.getDefault());
//                String notificationTime = simpleDateFormat.format(time);
//                notificationTimeTextview.setText(notificationTime);
//
//                Toast.makeText(context, "Time is set for: " + notificationTime, Toast.LENGTH_LONG).show();
//                Log.d("TRIGGER IF WORKS" , notificationTime);
//
//
//            }
//        }, hourOfDay, minuteOfDay, false);
//
//        timePickerDialog.show();
//    }
//
//
//}
//
//
