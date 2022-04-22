package com.example.smile_ukraine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.smile_ukraine.Modals.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class first_loadig_menu extends AppCompatActivity {

    Button buttonLogn, buttonRegister;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    RelativeLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_loadig_menu);

        buttonLogn = findViewById(R.id.ButtonLogn);
        buttonRegister = findViewById(R.id.ButtonRegister);

        root = findViewById(R.id.root_element);

        auth =FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterWindow();
            }
        });

        buttonLogn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSingInWindow();
            }
        });

    }

    private void showSingInWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Long in");
        dialog.setMessage("Add your danie");

        LayoutInflater inflater = LayoutInflater.from(this);
        View singIn_window = inflater.inflate(R.layout.sing_in_vindow, null);
        dialog.setView(singIn_window);

        final MaterialEditText email = singIn_window.findViewById(R.id.emailField);
        final MaterialEditText password = singIn_window.findViewById(R.id.passwordField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("In", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Add your email", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().length() < 5){
                    Snackbar.make(root, "Add your password more 5 chars", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(first_loadig_menu.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Error conection. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Register");
        dialog.setMessage("Введите дданые");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(register_window);

        final MaterialEditText email = register_window.findViewById(R.id.emailField);
        final MaterialEditText name = register_window.findViewById(R.id.nameField);
        final MaterialEditText password = register_window.findViewById(R.id.passwordField);
        final MaterialEditText phoneNumber = register_window.findViewById(R.id.phoneField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Add your email", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name.getText().toString())){
                    Snackbar.make(root, "Add your Name", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phoneNumber.getText().toString())){
                    Snackbar.make(root, "Add your phone", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().length() < 5){
                    Snackbar.make(root, "Add your password more 5 chars", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //register user
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setEmail(email.getText().toString());
                                user.setEmail(name.getText().toString());
                                user.setEmail(password.getText().toString());
                                user.setEmail(email.getText().toString());

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Snackbar.make(root, "User add", Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Error registration. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }
}