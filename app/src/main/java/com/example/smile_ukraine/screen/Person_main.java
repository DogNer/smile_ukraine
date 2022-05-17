package com.example.smile_ukraine.screen;

import android.app.Dialog;
import android.content.Context;
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
    String profileid, emotion;
    User userId;

    ActionBar actionBar;
    ViewPager viewPager;

    ArrayList<MyModelCard> modelArrayList;
    AdapterCard myAdapter;

    ImageView personAppearance, personSheet;
    Button btnhappy, btnnorm, btnsad;

    TextView textEmotion;

    ImageButton btn_change_star;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_main, container, false);

        /*ImageView button_set = (ImageView) view.findViewById(R.id.button_sett);
        button_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Settings_activity.class);
                startActivity(intent);
            }
        });*/



        /*btn_change_star = view.findViewById(R.id.chande_star_btn);
        btn_change_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogChangeColor();
            }
        });*/

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

        /*Button bnt_ch = (Button)  view.findViewById(R.id.button_check);
        bnt_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("id", firebaseUser.getUid());
                startActivity(intent);
            }
        });*/

        personAppearance = view.findViewById(R.id.person);


        personAppearance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEmotion();
            }
        });

        return view;
    }

    /*private void showDialogChangeColor() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_color_pers);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }*/

    private void showDialogEmotion() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bootom_sheet_color_pers);

        textEmotion = dialog.findViewById(R.id.text_emotion);
        personSheet = dialog.findViewById(R.id.person_sheet);

        btnhappy = dialog.findViewById(R.id.btnHappy);
        btnnorm = dialog.findViewById(R.id.btnMorm);
        btnsad = dialog.findViewById(R.id.btnSad);

        setFromEmotion();

        btnhappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emotion = "happy";
                textEmotion.setText(emotion);
                updateUserInfo("https://s1.hostingkartinok.com/uploads/images/2022/05/5a64fb4aa9b19b790122046cc721f649.png");
                setFromEmotion();
            }
        });

        btnnorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emotion = "norm";
                textEmotion.setText(emotion);
                updateUserInfo("https://s1.hostingkartinok.com/uploads/images/2022/05/61f5fd5782dd5df4db93147ef559608a.png");
                setFromEmotion();
            }
        });

        btnsad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emotion = "sad";
                textEmotion.setText(emotion);
                updateUserInfo("https://s1.hostingkartinok.com/uploads/images/2022/05/653c0dda8b4418ab515b9f47e814a2ad.png");
                setFromEmotion();
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

    private void setFromEmotion() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() != null){
                    User user = snapshot.getValue(User.class);

                    Glide.with(getContext()).load(user.getEmotion()).into(personSheet);
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