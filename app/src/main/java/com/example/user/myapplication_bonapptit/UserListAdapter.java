package com.example.user.myapplication_bonapptit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder>{

    ArrayList<UserObject> userList;

    public UserListAdapter(ArrayList<UserObject> userList){
        this.userList = userList;
    }

    @Override
    public UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        UserListViewHolder rcv = new UserListViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final UserListViewHolder holder,final int position) {
        holder.mName.setText(userList.get(position).getName());
        holder.mPhone.setText(userList.get(position).getPhone());
        holder.mAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userList.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //////////////////////////////////////////////////

    public class UserListViewHolder extends RecyclerView.ViewHolder{
        TextView mName, mPhone;
        RelativeLayout mLayout;
        CheckBox mAdd;
        UserListViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.nameus);
            mPhone = view.findViewById(R.id.phoneus);
            mAdd = view.findViewById(R.id.add);
            mLayout = view.findViewById(R.id.layout);
        }
    }

    //////
}