package org.ohmstheresistance.pickmeup.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ohmstheresistance.pickmeup.R;


public class GetUsersNameFragment extends Fragment {

    private View rootView;

    public GetUsersNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_get_users_name, container, false);


        return rootView;
    }

}
