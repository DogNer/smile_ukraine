package com.example.smile_ukraine.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smile_ukraine.Modals.User;
import com.example.smile_ukraine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    private FirebaseUser firebaseUser;
    TextView text_to_user;
    String friend_name, friend_id, usernames;
    Button btn_embrace, btn_kiss, btn_wave;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.friend_item, parent, false);
        return new FriendsAdapter.ViewHolder(view);
    }

    public FriendsAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = mUsers.get(position);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        holder.username.setText(user.getUsername());
        friend_name = user.getUsername();
        friend_id = user.getId();

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogReact();
            }
        });

    }

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mContext != null){
                    User user = snapshot.getValue(User.class);

                    usernames = user.getUsername();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showDialogReact() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_react);

        text_to_user = dialog.findViewById(R.id.textWithUser);
        text_to_user.setText("Share your reaction to " + friend_name);

        btn_embrace = dialog.findViewById(R.id.buttonEmbrace);
        btn_kiss = dialog.findViewById(R.id.buttonKiss);
        btn_wave = dialog.findViewById(R.id.buttonwave);

        userInfo();

        btn_embrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMassage(usernames, friend_id, "ambrace");
                Toast.makeText(mContext, "You ambrace to " + friend_name, Toast.LENGTH_SHORT).show();
            }
        });

        btn_kiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMassage(usernames, friend_id, "kiss");
                Toast.makeText(mContext, "You kiss " + friend_name, Toast.LENGTH_SHORT).show();
            }
        });

        btn_wave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMassage(usernames, friend_id, "wave");
                Toast.makeText(mContext, "You wave to " + friend_name, Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void sendMassage(String sender, String receiver, String massage){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("massage", massage);

        reference.child("Massages").child(friend_id).push().setValue(hashMap);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        //public CircleImageView image_profile;
        public Button btn_share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            btn_share = itemView.findViewById(R.id.btn_share);
        }
    }
}
