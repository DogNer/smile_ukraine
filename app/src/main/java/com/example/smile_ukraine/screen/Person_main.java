package com.example.smile_ukraine.screen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.smile_ukraine.CardVieww.AdapterCard;
import com.example.smile_ukraine.CardVieww.MyModelCard;
import com.example.smile_ukraine.Modals.User;
import com.example.smile_ukraine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Person_main extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static Person_main newInstance(String param1, String param2) {
        Person_main fragment = new Person_main();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView username;
    FirebaseUser firebaseUser;
    String profileid, emotion, userColor, userEnotion, emot, userEmmotion;
    User userId;

    ActionBar actionBar;
    ViewPager viewPager;

    ArrayList<MyModelCard> modelArrayList;
    AdapterCard myAdapter;

    ImageView personAppearance, personSheet;
    Button btnhappy, btnnorm, btnsad;
    ImageButton btnback;

    TextView textEmotion;

    ImageButton btn_change_star;
    ImageView imgFirstColor, imgSecondColor;
    final static String color_f_sad = "https://s1.hostingkartinok.com/uploads/images/2022/05/653c0dda8b4418ab515b9f47e814a2ad.png";
    final static String color_f_norm = "https://s1.hostingkartinok.com/uploads/images/2022/05/61f5fd5782dd5df4db93147ef559608a.png";
    final static String color_f_happy = "https://s1.hostingkartinok.com/uploads/images/2022/05/5a64fb4aa9b19b790122046cc721f649.png";
    final static String color_s_sad = "https://s1.hostingkartinok.com/uploads/images/2022/05/e213875dbdb19b317015f368a929d096.png";
    final static String color_s_norm = "https://s1.hostingkartinok.com/uploads/images/2022/05/f687360a8e78af5d716dd7ccfbad9595.png";
    final static String color_s_happy = "https://s1.hostingkartinok.com/uploads/images/2022/05/84c4b800fd74b67e71683be0d2809991.png";

    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_main, container, false);

        mContext = getContext();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileId", "none");

        username = view.findViewById(R.id.userName);

        userInfo();

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        viewPager = view.findViewById(R.id.viewPager);
        loadCard();

        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String title = modelArrayList.get(position).getTitle();
                actionBar.setTitle(title);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

        personAppearance = view.findViewById(R.id.person);

        personAppearance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEmotion();
            }
        });

        btn_change_star = view.findViewById(R.id.chanпe_star_btn);
        btn_change_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogChangeColor();
            }
        });

        return view;
    }

    private void showDialogChangeColor() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_color_pers);

        imgFirstColor = dialog.findViewById(R.id.first_color);
        imgSecondColor = dialog.findViewById(R.id.second_color);

        btnback = dialog.findViewById(R.id.button_back);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        getUserEmotion();

        imgFirstColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userEmmotion.equalsIgnoreCase(color_s_happy)) updateUserInfo(color_f_happy);
                else if (userEmmotion.equalsIgnoreCase(color_s_norm)) updateUserInfo(color_f_norm);
                else if (userEmmotion.equalsIgnoreCase(color_s_sad)) updateUserInfo(color_f_sad);
                updateUserColor("first");
                //updateUserInfo(color_f_sad);
            }
        });

        imgSecondColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(dialog.getContext(), (userEmmotion.equalsIgnoreCase("https://s1.hostingkartinok.com/uploads/images/2022/05/5a64fb4aa9b19b790122046cc721f649.png")) + "", Toast.LENGTH_SHORT).show();
                if (userEmmotion.equalsIgnoreCase(color_f_happy)) updateUserInfo(color_s_happy);
                else if (userEmmotion.equalsIgnoreCase(color_f_norm)) updateUserInfo(color_s_norm);
                else if (userEmmotion.equalsIgnoreCase(color_f_sad)) updateUserInfo(color_s_sad);
                /*updateUserInfo(em);*/
                //updateUserInfo(color_f_sad);
                updateUserColor("second");
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void showDialogEmotion() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bootom_sheet_color_pers);

        textEmotion = dialog.findViewById(R.id.text_emotion);
        personSheet = dialog.findViewById(R.id.person_sheet);

        btnback = dialog.findViewById(R.id.button_back);

        btnhappy = dialog.findViewById(R.id.btnHappy);
        btnnorm = dialog.findViewById(R.id.btnMorm);
        btnsad = dialog.findViewById(R.id.btnSad);

        setFromEmotion();

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnhappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emotion = "happy";
                //textEmotion.setText(emotion);
                if (userColor == "first")
                    updateUserInfo(color_f_happy);
                else updateUserInfo(color_s_happy);
                setFromEmotion();
            }
        });

        btnnorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emotion = "norm";
                //textEmotion.setText(emotion);
                if (userColor == "first")
                    updateUserInfo(color_f_norm);
                else updateUserInfo(color_s_norm);
                setFromEmotion();
            }
        });

        btnsad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emotion = "sad";
                setFromEmotion();
                //textEmotion.setText(emotion);
                if (userColor == "first")
                    updateUserInfo(color_f_sad);
                else updateUserInfo(color_s_sad);

            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void updateUserInfo(String emotion) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("emotion", emotion);

        reference.updateChildren(hashMap);
    }

    private void updateUserColor(String pos) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("bio", pos);

        reference.updateChildren(hashMap);
    }

    private String getUserEmotion(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() != null){
                    User user = snapshot.getValue(User.class);

                    userEmmotion = user.getEmotion();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return userEmmotion;
    }

    private void setFromEmotion() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() != null){
                    User user = snapshot.getValue(User.class);

                    userColor = user.getBio();
                    userEnotion = user.getEmotion();
                    if (color_s_sad.equalsIgnoreCase(userEnotion) || color_f_sad.equalsIgnoreCase(userEnotion)){
                        textEmotion.setText("sad");
                    }
                    else if (color_s_norm.equalsIgnoreCase(userEnotion) || color_f_norm.equalsIgnoreCase(userEnotion)){
                        textEmotion.setText("norm");
                    }
                    else if (color_s_happy.equalsIgnoreCase(userEnotion) || color_f_happy.equalsIgnoreCase(userEnotion)){
                        textEmotion.setText("happy");
                    }

                    Glide.with(getContext()).load(user.getEmotion()).into(personSheet);
                    Glide.with(getContext()).load(user.getEmotion()).into(personAppearance);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadCard() {
        modelArrayList = new ArrayList<>();

        modelArrayList.add(new MyModelCard(
                "Friend list"
        ));
        modelArrayList.add(new MyModelCard(
                "Invite friends"
        ));

        myAdapter = new AdapterCard(getActivity(), modelArrayList);
        viewPager.setAdapter(myAdapter);
        viewPager.setPadding(10, 1450, 10, 0);
    }

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() != null){
                    User user = snapshot.getValue(User.class);

                    Glide.with(getContext()).load(user.getEmotion()).into(personAppearance);
                    username.setText(user.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()){
            case R.id.button_sett:
                Fragment fragment = new FragmentFriendsAdd();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.personal_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                view.setVisibility(View.GONE);
                *//*fragmentFriendsAdd = new FragmentFriendsAdd();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.personal_fragment, FragmentFriendsAdd.class, null);
                *//**//*fragmentTransaction.addToBackStack(null);*//**//*

                fragmentTransaction.commit();*//*
            break;
        }*/
    }

}