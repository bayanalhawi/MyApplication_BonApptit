package com.example.user.myapplication_bonapptit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class grouprActivity extends AppCompatActivity {

    //String groupID;
    GridView gridView;
    String[] fruitNames = {"بانكيك ديلايت","داينمايت جمبري","تشيكن ميديتيرانيان","أومليت ( دافئ )","براوني","سموكد سلمون"};
    String[] fruitdes = {"فطائر طرية وساخنة (موز، توت بري، م&م، باونتي او سادة).","جمبري، جزر، كرنب و مايونيز حار.","دجاج مشوي وجبنة الموزاريلا الطازجة.","بيض مخفوق، بطاطا بالاعشاب مشوية او هاش براون، اختياركم من جبنة الماعز.","كيكة الكاكاو بالجوز، صلصة الشوكولا.","أجود أنواع سمك السلمون النروجي، قبار مخلل ،خس، شرائح الليمون وجبنة فيلادلفيا طرية في باغيت أسمر."};
    String[] fruitprice = {"10ر.س","5ر.س","20ر.س","3ر.س","25ر.س","30ر.س"};
    String[] fruitrname = {"أبتاون.", "بيتوتي.", "العائلة.", "أرياس.", "نكهة القهوة.", "ساكورا."};
    String[] fruitrlocation = {"حي الروضة.", "حي الفيحاء.", "حي الشرفية.", "حي الحمراء.", "حي البغدادية.", "حي الرويس."};
    int[] fruitImages = {R.drawable.ph1, R.drawable.p2, R.drawable.ph3, R.drawable.p4, R.drawable.ph5, R.drawable.p6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupr);

        //finding toolbar
        Toolbar toolbar =findViewById(R.id.toolbarotherpages);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // groupID=getIntent().getExtras().getString("groupID");

        //finding gridView
        gridView = findViewById(R.id.gridview);
        grouprActivity.CustomAdapter customAdapter = new grouprActivity.CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(), activity_grid_item.class);
                intent.putExtra("name", fruitNames[i]);
                intent.putExtra("des", fruitdes[i]);
                intent.putExtra("price", fruitprice[i]);
                intent.putExtra("rname", fruitrname[i]);
                intent.putExtra("rlocation", fruitrlocation[i]);
                intent.putExtra("image", fruitImages[i]);
                startActivity(intent);
            }
        });

        //
    }

    /////////////////////////////////////////////////////////
    //finding gridView
    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return fruitImages.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);

            //getting view in row_data
            TextView name,des;
            name = view1.findViewById(R.id.fruits);
            des=view1.findViewById(R.id.des);
            ImageView image = view1.findViewById(R.id.images);
            name.setText(fruitNames[i]);
            des.setText(fruitdes[i]);
            image.setImageResource(fruitImages[i]);
            return view1;
        }
    }

    ////////

}
