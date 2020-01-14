package com.example.user.myapplication_bonapptit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import java.util.ArrayList;
import java.util.List;

public class signupActivity extends AppCompatActivity {
    EditText addusername,addphone,addhealthstatus;
    Button btnsignup;
    ProgressBar pbbar2;
    List<users> users;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar =findViewById(R.id.toolbarotherpages);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        databaseUser = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("users");
        users = new ArrayList<>();
        addusername= findViewById(R.id.editTextname);
        addphone=findViewById(R.id.editTextphone2);
        addhealthstatus=findViewById(R.id.editTexthealthstatus);

        SharedPrefs.saveSharedSetting(signupActivity.this, "logged", "false");
        SharedPrefs.saveSharedSetting(signupActivity.this, "userphone", addphone.getText().toString());

        btnsignup =findViewById(R.id.buttonsignup2);
        pbbar2 = findViewById(R.id.pbbar2);
        pbbar2.setVisibility(View.GONE);


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup dosignup = new Signup(); // this is the Asynctask
                dosignup.execute("");

            }
        });
    }

    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                users.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    users user = postSnapshot.getValue(users.class);
                    //adding artist to the list
                    users.add(user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class Signup extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String username = addusername.getText().toString();
        String phone = "+966 " + addphone.getText().toString();
        String healthstatus = addhealthstatus.getText().toString();

        @Override
        protected void onPreExecute() {
            pbbar2.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar2.setVisibility(View.GONE);
            Toast.makeText(signupActivity.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                SharedPrefs.saveSharedSetting(signupActivity.this, "logged", "false");
                SharedPrefs.saveSharedSetting(getApplicationContext(), "userphone", phone);
                Intent i = new Intent(signupActivity.this, menur1Activity.class);
                startActivity(i);
                finish();

            }

        }

        @Override
        protected String doInBackground(String... params) {
            if (username.trim().equals("") || phone.trim().equals("")) {
                z += "الرجاء تعبئة جميع الحقول";
            } else if (!isPhoneNumberValid(phone, "+966") && !(phone.trim().equals(""))) {
                z += "أدخل رقم جوال صحيح";
            } else {
                int count = 0;
                for (users user2 : users) {
                    if (user2.getPhonenumber().equals(phone)) {
                        count = 1;
                    }
                }
                if (count == 0) {
                    try {

                        users user = new users(username, phone, healthstatus);
                        databaseUser.child(phone).setValue(user);
                        addusername.setText("");
                        addphone.setText("");
                        addhealthstatus.setText("");

                        z = "تم";
                        isSuccess = true;
                    }
                    catch (Exception ex) {
                        z = "رقم الجوال  مستخدم";
                        isSuccess = false;
                    }
                }
            }
            return z;
        }
    }

    public boolean isPhoneNumberValid(String phoneNumber, String countryCode)
    {
        //NOTE: This should probably be a member variable.
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try
        {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryCode);
            return phoneUtil.isValidNumber(numberProto);
        }
        catch (NumberParseException e)
        {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        return false;
    }
    /////
        }