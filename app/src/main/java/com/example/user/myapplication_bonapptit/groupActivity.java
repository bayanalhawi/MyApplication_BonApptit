package com.example.user.myapplication_bonapptit;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class groupActivity extends AppCompatActivity {

//////////////////////////////////////////////////////////

    public Button buttonaddusers;

    public void init1()
    {
        buttonaddusers=findViewById(R.id.addusers);
        buttonaddusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent but= new Intent(groupActivity.this,creategroupActivity.class);
                startActivity(but);
            }
        });
    }
//////////////////////////////////////////////////////

    private RecyclerView mgroupList;
    private RecyclerView.Adapter mgroupListAdapter;
    private RecyclerView.LayoutManager mgroupListLayoutManager;
    ArrayList<groupObject> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        //finding toolbar
        Toolbar toolbar =findViewById(R.id.toolbarotherpages);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupList= new ArrayList<>();

        init1();

        getPermissions();
        initializeRecyclerView();
        getUsergroupList();

    }

    /////////////////////////////////////////////////////

    private void getUsergroupList(){

        final String userphone=SharedPrefs.readSharedSetting(getApplicationContext(), "userphone", "").toString();

        DatabaseReference mUsergroupDB = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("users").child(userphone).child("group");

        mUsergroupDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                        groupObject mgroup = new groupObject(childSnapshot.getKey());
                        boolean  exists = false;
                        for (groupObject mChatIterator : groupList){
                            if (mChatIterator.getgroupId().equals(mgroup.getgroupId()))
                                exists = true;
                        }
                        if (exists)
                            continue;
                        groupList.add(mgroup);
                       mgroupListAdapter.notifyDataSetChanged();
                        //getgroupData(mgroup.getgroupId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //////////////////////////////////////////////////////////////////

    private void getgroupData(String groupId) {
        DatabaseReference mgroupDB = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("group").child(groupId).child("info");
        mgroupDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String groupId= "";

                    if(dataSnapshot.child("groupName").getValue() != null)
                        groupId= dataSnapshot.child("groupName").getValue().toString();

                    for(DataSnapshot userSnapshot : dataSnapshot.child("users").getChildren()){
                        for(groupObject mgroup : groupList){
                            if( mgroup.getgroupId().equals(groupId)){
                                UserObject mUser = new UserObject(userSnapshot.getKey());
                                mgroup.addUserToArrayList(mUser);
                               // getUserData(mUser);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //////////////////////////////////////////////////////

    private void getUserData(UserObject mUser) {
        DatabaseReference mUserDb = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("users").child(mUser.getUid());
        mUserDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserObject mUser = new UserObject(dataSnapshot.getKey());

                if(dataSnapshot.child("phonenumber").getValue() != null)
                    mUser.setPhone(dataSnapshot.child("phonenumber").getValue().toString());

                for(groupObject mgroup : groupList){
                    for (UserObject mUserIt :mgroup .getUserObjectArrayList()){
                        if(mUserIt.getUid().equals(mUser.getUid())){
                            mUserIt.setPhone(mUser.getPhone());
                        }
                    }
                }
                mgroupListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /////////////////////////////////////////////////////////////

    private void initializeRecyclerView() {
        mgroupList= findViewById(R.id.groupList);
        mgroupList.setNestedScrollingEnabled(false);
        mgroupList.setHasFixedSize(false);
        mgroupListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mgroupList.setLayoutManager(mgroupListLayoutManager);
        mgroupListAdapter = new groupListAdapter(groupList);
        mgroupList.setAdapter(mgroupListAdapter);
    }

    ///////////////////////////////////////

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, 1);
        }
    }

////////////////
}
