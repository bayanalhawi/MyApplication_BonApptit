package com.example.user.myapplication_bonapptit;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class creategroupActivity extends AppCompatActivity {

    private RecyclerView mUserList;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayoutManager;
    ArrayList<UserObject> userList, contactList;
    EditText groupname;
    Button mCreate, mCreategroup;
    AlertDialog dialog;

    public void init1() {
        mCreate = findViewById(R.id.create);

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(creategroupActivity.this);
                View view = getLayoutInflater().inflate(R.layout.activity_dialoggroup_name, null);

                groupname = view.findViewById(R.id.editgroup);

                mCreategroup = view.findViewById(R.id.createdgroup);

                mCreategroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        creategroup();
                    }
                });

                builder.setView(view);
                dialog = builder.create();
                dialog.show();

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);

        //finding toolbar
        Toolbar toolbar = findViewById(R.id.toolbarotherpages);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contactList = new ArrayList<>();
        userList = new ArrayList<>();

        initializeRecyclerView();
        getContactList();
        init1();

        //
    }
    /////////////////////////////////

    private void creategroup() {

        String key = groupname.getText().toString();

        DatabaseReference groupInfoDb = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("group").child(key).child("info");
        DatabaseReference userDb = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("users");

        String userphone=SharedPrefs.readSharedSetting(getApplicationContext(), "userphone", "").toString();

        HashMap newgroupMap = new HashMap();
        newgroupMap.put("groupName",key);
        newgroupMap.put("users/" +userphone, true);

        Boolean validgroup = false;
        for(UserObject mUser : userList){
            if(mUser.getSelected()){
                validgroup = true;
                newgroupMap.put("users/" +mUser.getUid(), true);
                userDb.child(mUser.getUid()).child("group").child(key).setValue(true);
            }
        }

        if(validgroup){
            groupInfoDb.updateChildren(newgroupMap);
            userDb.child(userphone).child("group").child(key).setValue(true);
        }

        Intent but = new Intent(creategroupActivity.this, groupActivity.class);
        startActivity(but);

    }


    ///////////////////////////////////

    private void getContactList() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while(phones.moveToNext()){
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            UserObject mContact = new UserObject("", name, phone);
            contactList.add(mContact);
            getUserDetails(mContact);
            //userList.add(mContact);
            // mUserListAdapter.notifyDataSetChanged();
        }
    }

//////////////////////////////////////////////////

    private void getUserDetails(UserObject mContact) {
        DatabaseReference mUserDB = FirebaseDatabase.getInstance("https://bonappetit-808c5.firebaseio.com").getReference("users");

        if ((mContact.getPhone().startsWith("+966"))) {
            StringBuilder str = new StringBuilder(mContact.getPhone());
            String p = (str.insert(5, 0).deleteCharAt(8).deleteCharAt(11)).toString();

            Query query = mUserDB.orderByChild("phonenumber").equalTo(p);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String phone = "",
                                name = "",
                                bphone = "";
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            if (childSnapshot.child("phonenumber").getValue() != null)
                                phone = childSnapshot.child("phonenumber").getValue().toString();
           /*bphone=childSnapshot.child("phonenumber").getValue().toString();
            StringBuilder str2=new StringBuilder(bphone);
            phone=(str2.deleteCharAt(5)).toString();*/
                            if (childSnapshot.child("username").getValue() != null)
                                name = childSnapshot.child("username").getValue().toString();

                            UserObject mUser = new UserObject(childSnapshot.getKey(),name,phone);
                            if (name.equals(phone))
                                for(UserObject mContactIterator : contactList) {
                                    if (mContactIterator.getPhone().equals(mUser.getPhone())) {
                                        mUser.setName(mContactIterator.getName());
                                    }
                                }

                            userList.add(mUser);
                            mUserListAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }


        if ((mContact.getPhone().startsWith("05"))) {
            StringBuilder str = new StringBuilder(mContact.getPhone());
            String p = (str.insert(0,"+").insert(1,9).insert(2,6).insert(3,6).insert(4," ").deleteCharAt(8).deleteCharAt(11)).toString();

            Query query = mUserDB.orderByChild("phonenumber").equalTo(p);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String phone = "",
                                name = "",
                                bphone = "";
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            if (childSnapshot.child("phonenumber").getValue() != null)
                                phone = childSnapshot.child("phonenumber").getValue().toString();
           /*bphone=childSnapshot.child("phonenumber").getValue().toString();
            StringBuilder str2=new StringBuilder(bphone);
            phone=(str2.deleteCharAt(5)).toString();*/
                            if (childSnapshot.child("username").getValue() != null)
                                name = childSnapshot.child("username").getValue().toString();

                            UserObject mUser = new UserObject(childSnapshot.getKey(),name,phone);
                            if (name.equals(phone))
                                for(UserObject mContactIterator : contactList) {
                                    if (mContactIterator.getPhone().equals(mUser.getPhone())) {
                                        mUser.setName(mContactIterator.getName());
                                    }
                                }

                            userList.add(mUser);
                            mUserListAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

/////////////////////////////////////////////////////

    private void initializeRecyclerView() {
        mUserList= findViewById(R.id.userList);
        mUserList.setNestedScrollingEnabled(false);
        mUserList.setHasFixedSize(false);
        mUserListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mUserList.setLayoutManager(mUserListLayoutManager);
        mUserListAdapter = new UserListAdapter(userList);
        mUserList.setAdapter(mUserListAdapter);
    }

    //////
}
