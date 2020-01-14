package com.example.user.myapplication_bonapptit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class activity_grid_item2 extends AppCompatActivity {

    private static final String TAG = "activity_grid_item2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_item2);

        Toolbar toolbar =findViewById(R.id.toolbarotherpages);
        setSupportActionBar(toolbar);

        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")
                && getIntent().hasExtra("image_des")&& getIntent().hasExtra("image_price")
                && getIntent().hasExtra("image_rname")&& getIntent().hasExtra("image_rlocation")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
            String imagedes = getIntent().getStringExtra("image_des");
            String imageprice = getIntent().getStringExtra("image_price");
            String reuname = getIntent().getStringExtra("image_rname");
            String reulocation = getIntent().getStringExtra("image_rlocation");

            setImage(imageUrl, imageName,imagedes,imageprice,reuname,reulocation);
        }
    }


    private void setImage(String imageUrl, String imageName,String imagedes,String imageprice
            ,String rename,String relocation){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.image_description);
        name.setText(imageName);

        TextView des = findViewById(R.id.image_desyes);
        des.setText(imagedes);

        TextView price = findViewById(R.id.textView_price);
        price.setText(imageprice);

        TextView rname = findViewById(R.id.textView_rn);
        rname.setText(rename);

        TextView rlocation = findViewById(R.id.textView_rl);
        rlocation.setText(relocation);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

}