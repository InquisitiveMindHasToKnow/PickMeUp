package org.ohmstheresistance.pickmeup.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.database.UserInfoDatabaseHelper;
import org.ohmstheresistance.pickmeup.model.UserInfo;


public class UsersNameFragment extends Fragment {

    public static String userName;
    private View rootView;

    UserInfoDatabaseHelper userInfoDatabaseHelper;

    public UsersNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_users_name, container, false);

        userInfoDatabaseHelper = UserInfoDatabaseHelper.getInstance(getContext());


        return rootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

            final EditText getUserNameEdittext = new EditText(getContext());

            int maxLength = 12;
            getUserNameEdittext.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

            alert.setTitle("Hello ");
            alert.setMessage("Please enter your name.");
            alert.setCancelable(false);
            alert.setView(getUserNameEdittext);

            alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    if (!TextUtils.isEmpty(getUserNameEdittext.getText())) {

                        userName = getUserNameEdittext.getText().toString();
                        userInfoDatabaseHelper.addUserInfo(UserInfo.from(userName));

                        DisplayQuotesFragment displayQuotesFragment = new DisplayQuotesFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_fragment_container, displayQuotesFragment);
                        fragmentTransaction.commit();
                    }

                }
            });

            alert.show();

        }

}
