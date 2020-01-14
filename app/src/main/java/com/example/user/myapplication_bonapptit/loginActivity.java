package com.example.user.myapplication_bonapptit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
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

public class loginActivity extends AppCompatActivity {

    EditText addphone2;
    Button btnlogin;
    ProgressBar pbbar1;
    String username= "";
    List<users> users;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbarotherpages);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseUser = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("users");

        addphone2=findViewById(R.id.editTextphone1);
        btnlogin =findViewById(R.id.buttonlogin2);
        pbbar1 =findViewById(R.id.pbbar1);
        pbbar1.setVisibility(View.GONE);

        users = new ArrayList<>();

         btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Login doLogin = new Login(); // this is the Asynctask
                    doLogin.execute("");
                }
            });

        }

    //*************************************************
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    users user = postSnapshot.getValue(users.class);
                    users.add(user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class Login extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String phone = "+966 "+addphone2.getText().toString();

        @Override
        protected void onPreExecute() {
            pbbar1.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar1.setVisibility(View.GONE);
            Toast.makeText(loginActivity.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                SharedPrefs.saveSharedSetting(loginActivity.this, "logged", "false");
                SharedPrefs.saveSharedSetting(getApplicationContext(), "userphone", phone);
                Intent i = new Intent(loginActivity.this, menur1Activity.class);
                startActivity(i);
                finish();

            }

        }

        @Override
        protected String doInBackground(String... params) {
            if (!isPhoneNumberValid(phone, "+966") && !(phone.trim().equals(""))) {
                z += "أدخل رقم جوال صحيح";
            } else {
                users user=null;
                for ( users user2 : users ) {
                    if ( user2.getPhonenumber().equals(phone)){
                        user=user2;
                        user.getUsername();
                    }
                }
                try{
                    username=user.getUsername();
                    z = "تم";
                    isSuccess = true;
                }
                catch(Exception ex) {
                    z = "الرقم غير موجود";
                    isSuccess = false;
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


}
