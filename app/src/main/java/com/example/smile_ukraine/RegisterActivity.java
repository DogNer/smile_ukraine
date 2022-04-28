package com.example.smile_ukraine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextname, editTextemail, editTextpassword, editTextphone;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_window);

        //getSupportActionBar().setTitle("Register");

        /*Toast.makeText(RegisterActivity.this, "You can register", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(RegisterActivity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                    editTextname.setError("Full Nmae is requred");
                    editTextname.requestFocus();
                }
                else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(RegisterActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    editTextemail.setError("Full Email is requred");
                    editTextemail.requestFocus();
                }
                else if(TextUtils.isEmpty(textPassword)){
                    Toast.makeText(RegisterActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    editTextpassword.setError("Full password is requred");
                    editTextpassword.requestFocus();
                }
                else if(TextUtils.isEmpty(textPhoneNumber)){
                    Toast.makeText(RegisterActivity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextphone.setError("Full phone number is requred");
                    editTextphone.requestFocus();
                }
                else{
                    registerUser(textName, textEmail, textPassword, textPhoneNumber);
                }
            }
        });*/
    }

    /*private void registerUser(String textName, String textEmail, String textPassword, String textPhoneNumber) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User register", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            firebaseUser.sendEmailVerification();

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                    }
                });
    }*/
}
