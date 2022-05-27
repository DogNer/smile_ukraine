package com.example.smile_ukraine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.smile_ukraine.Adapter.FriendsAdapter;
import com.example.smile_ukraine.Adapter.ReactionFrom;
import com.example.smile_ukraine.Modals.Massage;
import com.example.smile_ukraine.Modals.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SendingReactionFrom extends AppCompatActivity {

    String id;

    private List<String> idList;

    FirebaseUser firebaseUser;

    RecyclerView recyclerView;
    ReactionFrom reactionFrom;
    List<Massage> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_reaction_from);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        id = firebaseUser.getUid();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        reactionFrom = new ReactionFrom(this, userList);
        recyclerView.setAdapter(reactionFrom);

        idList = new ArrayList<>();

        showUsers();
    }


    private void showUsers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Massages").child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                List<Massage> list = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Massage user = snapshot.getValue(Massage.class);

                    list.add(user);
                }
                Collections.reverse(list);

                for (int i = 0; i < list.size(); ++i){
                    userList.add(list.get(i));
                }
                reactionFrom.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}