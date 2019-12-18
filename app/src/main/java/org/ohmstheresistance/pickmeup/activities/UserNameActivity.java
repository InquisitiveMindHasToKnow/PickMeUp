package org.ohmstheresistance.pickmeup.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.EditText;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.database.UserInfoDatabaseHelper;
import org.ohmstheresistance.pickmeup.model.UserInfo;

public class UserNameActivity extends AppCompatActivity {

    public static String userName;

    UserInfoDatabaseHelper userInfoDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);

        userInfoDatabaseHelper = UserInfoDatabaseHelper.getInstance(this);


        final AlertDialog.Builder alert = new AlertDialog.Builder(UserNameActivity.this, R.style.UserInfoDialog);

        final EditText getUserNameEdittext = new EditText(this);

        int maxLength = 12;
        getUserNameEdittext.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        getUserNameEdittext.setTextColor(getResources().getColor(R.color.textColor));

        alert.setTitle("Hello ");
        alert.setMessage("Please enter your name.");
        alert.setCancelable(false);
        alert.setView(getUserNameEdittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (!TextUtils.isEmpty(getUserNameEdittext.getText())) {

                    userName = getUserNameEdittext.getText().toString();
                    userInfoDatabaseHelper.addUserInfo(UserInfo.from(userName));

                    Intent toDisplayFragmentIntent = new Intent(UserNameActivity.this, FragmentHolderActivity.class);
                    startActivity(toDisplayFragmentIntent);
                    UserNameActivity.this.finish();
                }

            }
        });

        alert.show();

    }

}

