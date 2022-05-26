package com.example.smile_ukraine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.util.HashMap;

public class Register_activity extends AppCompatActivity {

    private EditText username, email, password, passwordAgain, phone_number;

    private ProgressBar progressBar;
    Button btn_register;
    TextView text_log;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Toast.makeText(Register_activity.this, "You can register", Toast.LENGTH_SHORT).show();

        username = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone_number = findViewById(R.id.phoneNumber);
        passwordAgain = findViewById(R.id.password_again);

        text_log = findViewById(R.id.text_log);

        text_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();

        btn_register = findViewById(R.id.regist_button);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(Register_activity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_username = username.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String str_phoneNumber = phone_number.getText().toString();
                String str_passwordAgain = passwordAgain.getText().toString();

                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_email)
                        || TextUtils.isEmpty(str_password) || TextUtils.isEmpty(str_phoneNumber)){
                    Toast.makeText(Register_activity.this, "Write your data", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else if (str_password.length() < 6){
                    Toast.makeText(Register_activity.this, "Password is short. Enter more 6 chars", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else if (!str_passwordAgain.equals(str_password)){
                    Toast.makeText(Register_activity.this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else {
                    registerd(str_username, str_email, str_password, str_phoneNumber);
                    //registerd(username, email, password, phone_number);
                }
            }
        });
    }

    private void registerd(final String username, String email, String password, String phone_number) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap<String, Object> hasMap = new HashMap<>();
                            hasMap.put("id", userid);
                            hasMap.put("username", username.toLowerCase());
                            hasMap.put("bio", "first");
                            hasMap.put("emotion", "https://s1.hostingkartinok.com/uploads/images/2022/05/5a64fb4aa9b19b790122046cc721f649.png");
                            hasMap.put("phone_number", phone_number.toLowerCase());
                            hasMap.put("password", password.toLowerCase());
                            hasMap.put("email", email.toLowerCase());

                            reference.setValue(hasMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent = new Intent(Register_activity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else {
                            pd.dismiss();
                            Toast.makeText(Register_activity.this, "You can't register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*private void registerUser(String textName, String textEmail, String textPassword, String textPhoneNumber) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPassword)
                .addOnCompleteListener(Register_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser;

                            *//*UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(textName).build();
                            firebaseUser.updateProfile(profileChangeRequest);*//*

                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textName).build();
                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textName, textEmail, textPassword, textPhoneNumber);

                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");

                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(Register_activity.this, "User register", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(Register_activity.this, MainActivity.class);
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
    }*/
}