package com.example.user.myapplication_bonapptit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class groupListAdapter extends RecyclerView.Adapter<groupListAdapter.groupListViewHolder>{

    ArrayList<groupObject> groupList;

    public groupListAdapter(ArrayList<groupObject> groupList){
        this.groupList = groupList;
    }

    @Override
    public groupListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        groupListViewHolder rcv = new groupListViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final groupListViewHolder holder,final int position) {
        holder.mTitle.setText(groupList.get(position).getgroupId());
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),grouprActivity.class);
              /* Bundle bundle=new Bundle();
               bundle.putString("groupID",groupList.get(holder.getAdapterPosition()).getgroupId());
               intent.putExtras(bundle);*/
               v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    //////////////////////////////////////////////////

    public class groupListViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        RelativeLayout mLayout;
        groupListViewHolder(View view){
            super(view);
            mTitle = view.findViewById(R.id.title);
            mLayout = view.findViewById(R.id.layoutg);
        }
    }

    //////

}