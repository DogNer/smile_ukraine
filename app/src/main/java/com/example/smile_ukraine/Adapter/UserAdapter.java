package com.example.smile_ukraine.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smile_ukraine.R;
import com.example.smile_ukraine.ReadWriteUserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context mContext;
    private List<ReadWriteUserDetails> mUsers;

    private FirebaseUser firebaseUser;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    public UserAdapter(Context mContext, List<ReadWriteUserDetails> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final ReadWriteUserDetails user = mUsers.get(position);

        holder.btn_follow.setVisibility(View.VISIBLE);

        holder.username.setText(user.getUsername());*/
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        //public CircleImageView image_profile;
        public Button btn_follow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            btn_follow = itemView.findViewById(R.id.btn_add);
        }
    }
}
