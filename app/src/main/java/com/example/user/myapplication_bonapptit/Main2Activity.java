package com.example.user.myapplication_bonapptit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    public Button buttonlogin;
    public TextView textsignup;

    public void init1()
    {
        buttonlogin=findViewById(R.id.buttonlogin);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent but= new Intent(Main2Activity.this,loginActivity.class);
                startActivity(but);
            }
        });
    }

    public void init2()
    {
        textsignup=findViewById(R.id.textView);
        textsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent but= new Intent(Main2Activity.this,signupActivity.class);
                startActivity(but);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init1();
        init2();
    }
}
