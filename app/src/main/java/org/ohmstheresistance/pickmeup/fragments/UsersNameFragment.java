package org.ohmstheresistance.pickmeup.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.ohmstheresistance.pickmeup.R;



public class UsersNameFragment extends Fragment {

    public static final String KEY_PREFS_FIRST_LAUNCH = "first_launch";
    private static final String SHARED_PREFS_KEY = "userNameSharedPref";

    private FragmentNavigation fragmentNavigation;
    private View rootView;

    public UsersNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_users_name, container, false);


        return rootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences firstLaunchCheck = PreferenceManager.getDefaultSharedPreferences(getContext());

        if (firstLaunchCheck.getBoolean(KEY_PREFS_FIRST_LAUNCH, true)) {


            final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

            final EditText getUserNameEdittext = new EditText(getContext());
            alert.setTitle("Hello ");
            alert.setMessage("Please enter your name.");
            alert.setCancelable(false);
            alert.setView(getUserNameEdittext);

            alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    Bundle userNameBundle = new Bundle();

                    if (userNameBundle == null) {

                        firstLaunchCheck.edit().putBoolean(KEY_PREFS_FIRST_LAUNCH, true).apply();

                        fragmentNavigation = (FragmentNavigation) getContext();


                    } else {

                        firstLaunchCheck.edit().putBoolean(KEY_PREFS_FIRST_LAUNCH, false).apply();


                        SharedPreferences userNameSharedPrefs = getActivity().getSharedPreferences(SHARED_PREFS_KEY, 0);
                        SharedPreferences.Editor editor = userNameSharedPrefs.edit();
                        if (!TextUtils.isEmpty(getUserNameEdittext.getText().toString())) {

                            editor.putString("USER_NAME", getUserNameEdittext.getText().toString());
                            editor.apply();
                        }

                            fragmentNavigation = (FragmentNavigation) getContext();
                            fragmentNavigation.getUserName(getUserNameEdittext.getText().toString());


                        }

                }
            });

            alert.show();

        }
    }

}

