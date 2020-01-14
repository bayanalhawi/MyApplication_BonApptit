package com.example.user.myapplication_bonapptit;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class menur1Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //vars
    private static final String TAG="menur1Activity";
    private ArrayList<String>mImages=new ArrayList<>();
    private ArrayList<String>mNames=new ArrayList<>();
    private ArrayList<String>mdes=new ArrayList<>();
    private ArrayList<String>mprice=new ArrayList<>();
    private ArrayList<String>mrname=new ArrayList<>();
    private ArrayList<String>mrlocation=new ArrayList<>();
    private ArrayList<String>userItems=new ArrayList<>();
    ArrayList<String>userCollaborativePhone=new ArrayList<>();
    private ArrayList<String>itemCollaborativeID=new ArrayList<>();
    private ArrayList<Integer>userCollaborativeCount=new ArrayList<>();
    SparseArray<Integer> userCollaborative = new SparseArray<>();
    SparseArray<Integer> itemCollaborative = new SparseArray<>();
    private ArrayList<String>userCollaborativeSelected=new ArrayList<>();
    frequenceUser user;
    frequenceUser userCheck;
    String phoneNo;
    GridView gridView;
    List<items> users;
    DatabaseReference databaseUser;
    DatabaseReference databaseItem;
    TextView addPhoneNo;
    int count=0;

    private ArrayList<String>itemName=new ArrayList<>();
    private ArrayList<String>itemDesc=new ArrayList<>();
    private ArrayList<String>itemPrice=new ArrayList<>();
    private ArrayList<String>itemImages=new ArrayList<>();
    private ArrayList<String>itemRestaurantName=new ArrayList<>();
     String[] fruitrlocation = {"حي الروضة.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس.","حي الفيحاء.","حي الشرفية.","حي الحمراء.","حي البغدادية.","حي الرويس."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menur1);

        //finding toolbar
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //finding DrawerLayout
       DrawerLayout  drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        addPhoneNo = (TextView) headerView.findViewById(R.id.nev_phone);
        String userphone=SharedPrefs.readSharedSetting(getApplicationContext(), "userphone", "").toString();
        addPhoneNo.setText(userphone);

        //finding RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(menur1Activity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        Log.d(TAG, "onCreate: started.");
        getImages();

        //finding gridView
        users = new ArrayList<>();
        databaseUser = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("restaurant");
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    databaseItem = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("restaurant").child(postSnapshot.getKey());

                    databaseItem.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                //items user = postSnapshot.getValue(items.class);
                                //users.add(user);
                                itemName.add(postSnapshot.getValue(items.class).getItem_Name());

                                itemImages.add(postSnapshot.getValue(items.class).getItem_Image());

                                itemDesc.add(postSnapshot.getValue(items.class).getItem_Description());

                                itemRestaurantName.add(postSnapshot.getValue(items.class).getItem_RestaurantName());

                                itemPrice.add(postSnapshot.getValue(items.class).getItem_Price());

                            }

                            //=================================
                            gridView = findViewById(R.id.gridview);
                            CustomAdapter customAdapter=new CustomAdapter();
                            gridView.setAdapter(customAdapter);

                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                    Intent intent = new Intent(getApplicationContext(),activity_grid_item.class);
                                    intent.putExtra("name",itemName.get(i));
                                    intent.putExtra("des",itemDesc.get(i));
                                    intent.putExtra("price",itemPrice.get(i));
                                    intent.putExtra("rname",itemRestaurantName.get(i));
                                    intent.putExtra("rlocation",fruitrlocation[i]);
                                    intent.putExtra("image",itemImages.get(i));
                                    startActivity(intent);
                                }
                            });
                            //=================================
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //
    }

    //***********************************************

    protected void onStart() {
        super.onStart();
        //attaching value event listener
    }

    //finding RecyclerView
    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        final String userphone=SharedPrefs.readSharedSetting(getApplicationContext(), "userphone", "").toString();

        databaseUser = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("preferences").child(userphone);
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {

                }
                else{
                    users.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        userItems.add(postSnapshot.getKey());
                    }
                    //------
                    for (int i = 0; i < userItems.size(); i++) {
                        databaseUser = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("rate").child(userItems.get(i));
                        databaseUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    int phoneNo = Integer.parseInt(postSnapshot.getKey().substring(5));
                                    if (!postSnapshot.getKey().equals(userphone)) {
                                        if (userCollaborative.get(phoneNo) != null) {
                                            int count = userCollaborative.get(phoneNo);
                                            count++;
                                            userCollaborative.put(phoneNo, count);

                                        } else {
                                            userCollaborative.put(phoneNo, 1);
                                            userCollaborativePhone.add(postSnapshot.getKey());
                                        }
                                    }
                                }
                                for (int i = 1; i < userCollaborativePhone.size(); i++) {
                                    int phoneNoFirst = Integer.parseInt(userCollaborativePhone.get(i - 1).substring(5));
                                    int phoneNoSeconed = Integer.parseInt(userCollaborativePhone.get(i).substring(5));
                                    //System.out.println("&&&&&&&&&&&&&&&&Phone i-1"+userCollaborativePhone.get(i-1));
                                    // System.out.println("&&&&&&&&&&&&&&&&Phone i-1 count"+userCollaborative.get(phoneNoFirst));
                                    // System.out.println("&&&&&&&&&&&&&&&&Phone i"+userCollaborativePhone.get(i));
                                    //System.out.println("&&&&&&&&&&&&&&&&Phone i count"+userCollaborative.get(phoneNoSeconed));
                                    String temp;
                                    if (userCollaborative.get(phoneNoFirst) < userCollaborative.get(phoneNoSeconed)) {
                                        temp = userCollaborativePhone.get(i - 1);
                                        userCollaborativePhone.add(i - 1, userCollaborativePhone.get(i));
                                        userCollaborativePhone.add(i, temp);


                                        //  System.out.println("&&&&&&&&&&&&&&&&1"+temp);
                                    }/*
                                else{
                                  temp=userCollaborativePhone.get(i);
                                  userCollaborativePhone.add(i-1,userCollaborativePhone.get(i));
                                  userCollaborativePhone.add(i,temp);
                                  //System.out.println("&&&&&&&&&&&&&&&&2"+temp);
                                }*/
                                }
                                //____
                                int size = userCollaborativePhone.size();
                                if (userCollaborativePhone.size() > 10) {
                                    size = 10;
                                }
                                //------
                                for (int i = 0; i < size; i++) {
                                    databaseUser = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("preferences").child(userCollaborativePhone.get(i));
                                    databaseUser.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                int itemID = Integer.parseInt(postSnapshot.getKey());
                                                if (itemCollaborative.get(itemID) != null) {
                                                    int count = itemCollaborative.get(itemID);
                                                    count++;
                                                    itemCollaborative.put(itemID, count);
                                                } else {
                                                    itemCollaborative.put(itemID, 1);
                                                    int no = 0;
                                                    for (int i = 0; i < userItems.size(); i++) {
                                                        if (userItems.get(i).equals(postSnapshot.getKey())) {
                                                            no++;
                                                        }
                                                    }
                                                    if (no == 0) {
                                                        final String itemId = postSnapshot.getKey();
                                                        //itemCollaborativeID.add(postSnapshot.getKey());
                                                        databaseUser = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("restaurant");
                                                        databaseUser.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                users.clear();
                                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                    databaseItem = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("restaurant").child(postSnapshot.getKey());
                                                                    databaseItem.orderByChild("item_ID").equalTo(itemId).addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            users.clear();
                                                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                                items item = postSnapshot.getValue(items.class);

                                                                                mImages.add(item.getItem_Image());
                                                                                mNames.add(item.getItem_Name());
                                                                                mdes.add(item.getItem_Description());
                                                                                mprice.add(item.getItem_Price());
                                                                                mrname.add(item.getItem_RestaurantName());
                                                                                mrlocation.add(item.getItem_Time());

                                                                                initRecyclerView();

                                                                                //  System.out.println("????????????????" + item.getItem_Name());

                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(DatabaseError databaseError) {
                                                                        }
                                                                    });

                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {
                                                            }
                                                        });
                                                        // System.out.println("????????????????" + restaurantName.getItem_Price());

                                                        // String restaurantName=dataSnapshot.getKey();

                                                        // System.out.println("????????????????"+postSnapshot.getKey());
                                                    }////iffffff
                                                }
                                            }

                                            //----------
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                                //-----


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                    //------'=
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //RecyclerView
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImages,mdes,mprice,mrname,mrlocation);
        recyclerView.setAdapter(adapter);
    }

    //finding DrawerLayout
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menur1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifications) {
          Intent intent = new Intent(menur1Activity.this,notificationsActivity.class);
         startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_profile) {
           Intent intent = new Intent(menur1Activity.this,profileActivity.class);
           startActivity(intent);
        }
        else if (id == R.id.nav_group) {
           Intent intent = new Intent(menur1Activity.this,groupActivity.class);
           startActivity(intent);
       }
       else if (id == R.id.nav_setting) {
           Intent intent = new Intent(this, SettingsActivity.class);
           startActivity(intent);
           return true;
       }
       else if (id == R.id.nav_out) {
           SharedPrefs.saveSharedSetting(menur1Activity.this, "logged", "true");
           startActivity(new Intent(menur1Activity.this,loginActivity.class));
           SharedPrefs.deleteAllSettings(menur1Activity.this);
           finish();
       }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    //finding RecyclerView
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        public  static final String TAG="RecyclerViewAdapter";

        //Var
        private ArrayList<String>mNames=new ArrayList<>();
        private ArrayList<String>mImages=new ArrayList<>();
        private ArrayList<String>mdes=new ArrayList<>();
        private ArrayList<String>mprice=new ArrayList<>();
        private ArrayList<String>mrname=new ArrayList<>();
        private ArrayList<String>mrlocation=new ArrayList<>();
        private Context context;

        public RecyclerViewAdapter(Context context,ArrayList<String> mNames, ArrayList<String> mImages,ArrayList<String> mdes
                ,ArrayList<String> mprice,ArrayList<String> mrname,ArrayList<String> mrlocation) {
            this.mNames = mNames;
            this.mImages = mImages;
            this.mdes=mdes;
            this.mprice=mprice;
            this.mrname=mrname;
            this.mrlocation=mrlocation;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_recycler,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Log.d(TAG,"onBindViewHolder: called.");
            Glide.with(context).asBitmap().load(mImages.get(position)).into(holder.image);
            holder.name.setText(mNames.get(position));
            holder.parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: clicked on: " + mNames.get(position));
                    Intent intent = new Intent(context,activity_grid_item2.class);
                    intent.putExtra("image_url", mImages.get(position));
                    intent.putExtra("image_name", mNames.get(position));
                    intent.putExtra("image_des", mdes.get(position));
                    intent.putExtra("image_price", mprice.get(position));
                    intent.putExtra("image_rname", mrname.get(position));
                    intent.putExtra("image_rlocation", mrlocation.get(position));
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mImages.size();
        }

        ////////////////////////////////////////////

        public class ViewHolder extends RecyclerView.ViewHolder{

            CircleImageView image;
            TextView name;
            RelativeLayout parent_layout;

            public ViewHolder(View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.image);
                name=itemView.findViewById(R.id.name);
                parent_layout=itemView.findViewById(R.id.parent_layout);

            }
        }

        ///////////
    }

    //////////////////////////////////////////////////////////////////
    //finding gridView
    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return itemImages.size();
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
            name.setText(itemName.get(i));
            des.setText(itemDesc.get(i));
            Picasso.with(viewGroup.getContext()).load(itemImages.get(i)).into(image);
            return view1;
        }
    }

    /////////////

}