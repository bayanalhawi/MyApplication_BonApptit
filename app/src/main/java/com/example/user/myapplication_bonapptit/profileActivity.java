package com.example.user.myapplication_bonapptit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class profileActivity extends AppCompatActivity {

    Button buttoneditprofile;
   TextView nameuser,PhoneNo,healthstatus;

    public void init1()
    {
        buttoneditprofile=findViewById(R.id.buttoneditprofile);
        buttoneditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent but= new Intent(profileActivity.this,editprofileActivity.class);
                startActivity(but);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //finding toolbar
        Toolbar toolbar =findViewById(R.id.toolbarotherpages);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameuser= findViewById(R.id.editTextnamee);
        PhoneNo= findViewById(R.id.editTextphonee2);
        healthstatus= findViewById(R.id.editTextstatuu);

        final String userphone=SharedPrefs.readSharedSetting(getApplicationContext(), "userphone", "").toString();

        DatabaseReference mUserDB = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("users").child(userphone);

        mUserDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = "",
                        health_status = "",
                        phone=" ";

                name=dataSnapshot.child("username").getValue().toString();
                phone=dataSnapshot.child("phonenumber").getValue().toString();
                health_status=dataSnapshot.child("health_status").getValue().toString();

                nameuser.setText(name);
                PhoneNo.setText(phone);
                healthstatus.setText(health_status);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        init1();

    }


}
