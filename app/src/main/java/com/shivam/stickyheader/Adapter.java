package com.shivam.stickyheader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder>{
    Context context;
    private List<User> userList;

    public Adapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvDate;

        public Holder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvDate = view.findViewById(R.id.tv_date);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final User user = userList.get(position);

        holder.tvName.setText(user.getName());
        holder.tvDate.setText(user.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, user.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }


}
