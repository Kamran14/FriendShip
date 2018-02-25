package com.hackthevalley.friendship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import org.w3c.dom.Text;

import java.util.HashMap;

public class MainRegisteration extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase;

    private static String TAG = "Tester";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registeration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView userName = (TextView) findViewById(R.id.nameField);
                UserProfileChangeRequest nameUpdate = new UserProfileChangeRequest.Builder().setDisplayName(userName.getText().toString()).build();
                user.updateProfile(nameUpdate).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task){
                        if (task.isSuccessful()){
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
                final TextView userCampus = (TextView) findViewById(R.id.campusField);
                final TextView userProgram = (TextView) findViewById(R.id.programField);
                final TextView userDesc = (TextView) findViewById(R.id.descriptionField);
                String[] userInfo = {userName.getText().toString(), user.getEmail().toString(), userCampus.getText().toString(), userProgram.getText().toString(), userDesc.getText().toString()};
                final String myData = String.format("%s, %s, %s", userCampus.getText().toString(), userProgram.getText().toString(), userDesc.getText().toString());
//                HashMap<String, String> info = new HashMap<String, String>();
//                info.put(userName.getText().toString(), userName.getText().toString());
//                info.put(user.getEmail().toString(), user.getEmail().toString());
//                info.put(userCampus.getText().toString(), userCampus.getText().toString());
//                info.put(userProgram.getText().toString(), userProgram.getText().toString());
//                info.put(userDesc.getText().toString(), userDesc.getText().toString());
//                mDatabase.setValue(info);

                mDatabase.child(userName.getText().toString()).setValue(String.format("%s, %s, %s, %s", user.getEmail().toString(),userCampus.getText().toString(), userProgram.getText().toString(), userDesc.getText().toString()));
                Intent toHome = new Intent(MainRegisteration.this, homeActivity.class);
                startActivity(toHome);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }
    public void hideKeyboard(){
        try  {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

//    @IgnoreExtraProperties
//    class User{
//        public String user_email;
//        public String user_campus;
//        public String user_program;
//        public String user_desc;
//        public User(){
//
//        }
//        TextView userCampus = (TextView) findViewById(R.id.campusField);
//        TextView userProgram = (TextView) findViewById(R.id.programField);
//        TextView userDesc = (TextView) findViewById(R.id.descriptionField);
//        String[] interests = {"radioButton", "radioButton3", "radioButton4", "radioButton5"};
//
//        public User(String user_email, String user_campus, String user_program, String user_desc){
//            this.user_email = user_email;
//            this.user_campus = userCampus.getText().toString();
//            this.user_program = userProgram.getText().toString();
//            this.user_desc = userDesc.getText().toString();
//
//        }
//    }
//    private void writeNewUser(String user_email, String user_campus, String user_program, String user_desc){
//        User user = new User(user_email, user_campus, user_program, user_desc);
//        mDatabase.child("email").child(user.user_email).child("campus").child(user.user_campus).child("program").child(user.user_program).child("user_desc").child(user.user_desc);
//    }

}
