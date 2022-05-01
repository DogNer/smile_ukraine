package com.example.smile_ukraine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings_activity extends AppCompatActivity {

    private TextView username, textEmail;
    private String name;
    private FirebaseAuth authProfile;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String USERS = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ImageButton btn_settings_back = (ImageButton) findViewById(R.id.button_back_sett);
        //повесили на него листенера
        btn_settings_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void showUserProfile(FirebaseUser firebaseUser) {
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users").child(firebaseUser.getUid());
        referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    name = firebaseUser.getDisplayName();
                    username.setText(name);
                    Toast.makeText(Settings_activity.this, "All is correct", Toast.LENGTH_SHORT).show();
                } else {
                    username.setText("xxxx");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Settings_activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}