package com.example.user.myapplication_bonapptit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class activity_grid_item extends AppCompatActivity {

    TextView name,des,price,rname,rlocation;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_item);

        Toolbar toolbar =findViewById(R.id.toolbarotherpages);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.griddata);
        des=findViewById(R.id.textView_des);
        price=findViewById(R.id.textView_price);
        rname=findViewById(R.id.textView_rn);
        rlocation=findViewById(R.id.textView_rl);
        image =findViewById(R.id.imageView);

        Intent intent=getIntent();
        name.setText(intent.getStringExtra("name"));
        des.setText(intent.getStringExtra("des"));
        price.setText(intent.getStringExtra("price"));
        rname.setText(intent.getStringExtra("rname"));
        rlocation.setText(intent.getStringExtra("rlocation"));
        String imageName = intent.getStringExtra("image");
        Glide.with(this).asBitmap().load(imageName).into(image);

    }

}
