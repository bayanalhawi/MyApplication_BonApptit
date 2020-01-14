package com.example.user.myapplication_bonapptit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editprofileActivity extends AppCompatActivity {

    EditText username, healthstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        //finding toolbar
        Toolbar toolbar =findViewById(R.id.toolbarotherpages);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username= findViewById(R.id.editTextname);
        healthstatus= findViewById(R.id.editTextstatu);

        //
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuredit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            String name = username.getText().toString();
            String  health_status = healthstatus.getText().toString();

            final String userphone=SharedPrefs.readSharedSetting(getApplicationContext(), "userphone", "").toString();

            SharedPrefs.saveSharedSetting(getApplicationContext(), "logged", "false");
            SharedPrefs.saveSharedSetting(getApplicationContext(), "username",name);
            SharedPrefs.saveSharedSetting(getApplicationContext(), "user_health_status",health_status);

           FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("users").child(userphone).child("username").setValue(name);
            FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("users").child(userphone).child("health_status").setValue(health_status);

            Intent but= new Intent(editprofileActivity.this,profileActivity.class);
            startActivity(but);
        }

        return super.onOptionsItemSelected(item);
    }
}
