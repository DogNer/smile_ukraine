package com.example.smile_ukraine.screen;

import static androidx.core.app.NotificationCompat.getPublicVersion;
import static androidx.core.app.NotificationCompat.getVisibility;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smile_ukraine.LoginActivity;
import com.example.smile_ukraine.MenuInSett.UploadPhoto;
import com.example.smile_ukraine.Modals.User;
import com.example.smile_ukraine.MenuInSett.ProfileActivity;
import com.example.smile_ukraine.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Personal_settings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static Personal_settings newInstance(String param1, String param2) {
        Personal_settings fragment = new Personal_settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    TextView username, emailUser, numberUser, editNameBtn, addFriends, supportBtn, addAccountBtn, editPhotoText;
    FirebaseUser firebaseUser;
    String profileid;
    User userId;

    TextView textFindFr, textChangeEmo, textChangeColor, textSendReact, textHowFind;

    FirebaseAuth firebaseAuth;
    ImageButton btnback;
    int cnt = 0;
    boolean check = false;
    CircleImageView pers_photo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_settings, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileId", "none");

        username = view.findViewById(R.id.userName);
        emailUser = view.findViewById(R.id.emailUser);
        numberUser = view.findViewById(R.id.numberUser);
        addAccountBtn = view.findViewById(R.id.add_account_btn);
        addFriends = view.findViewById(R.id.add_friends);
        pers_photo = view.findViewById(R.id.personal_photo);
        editPhotoText = view.findViewById(R.id.edit_photo);

        addAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShareClicked();
            }
        });

        editPhotoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UploadPhoto.class);
                startActivity(intent);
            }
        });

        userInfo();

        return view;
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

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() != null){
                    User user = snapshot.getValue(User.class);

                    Glide.with(getContext()).load(user.getImageUri()).into(pers_photo);
                    username.setText(user.getUsername());
                    emailUser.setText(user.getEmail());
                    numberUser.setText(user.getPhone_number());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDialogSupport() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.support_menu);


        textFindFr = dialog.findViewById(R.id.text_find_friend);
        textChangeEmo = dialog.findViewById(R.id.text_change_emotion);
        textChangeColor = dialog.findViewById(R.id.text_change_color);
        textSendReact = dialog.findViewById(R.id.text_send_react);

        textHowFind = dialog.findViewById(R.id.text_how_find);

        btnback = dialog.findViewById(R.id.button_back);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        textFindFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cnt == 0){
                    textHowFind.setVisibility(View.VISIBLE);
                    cnt = 1;
                }
                else{
                    textHowFind.setVisibility(View.GONE);
                    cnt = 0;
                }
            }
        });



        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }



}