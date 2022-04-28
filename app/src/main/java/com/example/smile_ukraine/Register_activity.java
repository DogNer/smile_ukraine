package com.example.smile_ukraine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smile_ukraine.screen.Person_main;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_activity extends AppCompatActivity {

    private EditText editTextname, editTextemail, editTextpassword, editTextphone;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Toast.makeText(Register_activity.this, "You can register", Toast.LENGTH_SHORT).show();

        editTextname = findViewById(R.id.user_name);
        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);
        editTextphone = findViewById(R.id.phoneNumber);

        Button buttonRegister = findViewById(R.id.regist_button);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = editTextname.getText().toString();
                String textEmail = editTextemail.getText().toString();
                String textPassword = editTextpassword.getText().toString();
                String textPhoneNumber = editTextphone.getText().toString();

                if(TextUtils.isEmpty(textName)){
                    Toast.makeText(Register_activity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                    editTextname.setError("Full Nmae is requred");
                    editTextname.requestFocus();
                }
                else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(Register_activity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    editTextemail.setError("Full Email is requred");
                    editTextemail.requestFocus();
                }
                else if(TextUtils.isEmpty(textPassword)){
                    Toast.makeText(Register_activity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    editTextpassword.setError("Full password is requred");
                    editTextpassword.requestFocus();
                }
                else if(TextUtils.isEmpty(textPhoneNumber)){
                    Toast.makeText(Register_activity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextphone.setError("Full phone number is requred");
                    editTextphone.requestFocus();
                }
                else{
                    registerUser(textName, textEmail, textPassword, textPhoneNumber);
                }
            }
        });
    }

    private void registerUser(String textName, String textEmail, String textPassword, String textPhoneNumber) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPassword)
                .addOnCompleteListener(Register_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            /*UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(textName).build();
                            firebaseUser.updateProfile(profileChangeRequest);*/

                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textEmail, textPassword, textPhoneNumber);

                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Register Users");

                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(Register_activity.this, "User register", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(Register_activity.this, Person_main.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(Register_activity.this, "User register faild", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                        }
                    }
                });
    }
}