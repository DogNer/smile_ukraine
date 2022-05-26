package com.example.smile_ukraine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smile_ukraine.Adapter.FriendsAdapter;
import com.example.smile_ukraine.Adapter.UserAdapter;
import com.example.smile_ukraine.Modals.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {

    String id;

    private List<String> idList;

    FirebaseUser firebaseUser;

    RecyclerView recyclerView;
    FriendsAdapter friendsAdapter;
    List<User> userList;
    AppCompatButton btn_invite;
    CardView cardInvite;
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        id = firebaseUser.getUid();

        btn_invite = findViewById(R.id.button_invite);
        cardInvite = findViewById(R.id.card_invite);

        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShareClicked();
            }
        });


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
        friendsAdapter = new FriendsAdapter(this, userList);
        recyclerView.setAdapter(friendsAdapter);

        idList = new ArrayList<>();

        getFriend();
    }


    private void getFriend(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(id).child("following");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                    ++cnt;
                    if (cnt > 4) cardInvite.setVisibility(View.GONE);
                }

                showUsers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showUsers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    for (String id : idList){
                        if (user.getId().equals(id)){
                            userList.add(user);
                        }
                    }
                }
                friendsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static Uri generateContentLink() {
        Uri baseUrl = Uri.parse("https://your-custom-name.page.link");
        String domain = "https://your-app.page.link";

        DynamicLink link = FirebaseDynamicLinks.getInstance()
                .createDynamicLink()
                .setLink(baseUrl)
                .setDomainUriPrefix(domain)
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.your.bundleid").build())
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.your.packageName").build())
                .buildDynamicLink();

        return link.getUri();
    }

    private void onShareClicked() {
        //DynamicLinksUtil.generateContentLink();
        Uri link = generateContentLink();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link.toString());

        startActivity(Intent.createChooser(intent, "Share Link"));
    }
}