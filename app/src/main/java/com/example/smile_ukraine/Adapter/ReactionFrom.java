package com.example.smile_ukraine.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smile_ukraine.Modals.Massage;
import com.example.smile_ukraine.Modals.User;
import com.example.smile_ukraine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ReactionFrom extends RecyclerView.Adapter<ReactionFrom.ViewHolder>{
    private Context mContext;
    private List<Massage> mMassage;

    private FirebaseUser firebaseUser;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.send_friends_reaction, parent, false);
        return new ReactionFrom.ViewHolder(view);
    }

    public ReactionFrom(Context mContext, List<Massage> mMassage) {
        this.mContext = mContext;
        this.mMassage = mMassage;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Massage massage = mMassage.get(position);

        holder.textEmotion.setText(massage.getMassage());
        holder.textSender.setText(massage.getSender());
    }

    @Override
    public int getItemCount() {
        return mMassage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textEmotion, textSender;
        //public CircleImageView image_profile;
        public Button btn_share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textSender = itemView.findViewById(R.id.from_username);
            textEmotion = itemView.findViewById(R.id.text_emotion);
        }
    }



}
