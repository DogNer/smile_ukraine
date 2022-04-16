package com.example.smile_ukraine.screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.smile_ukraine.FragmentFriendsAdd;
import com.example.smile_ukraine.R;
import com.example.smile_ukraine.Settings_activity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Person_main#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Person_main extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Person_main() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Person_main.
     */
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_main, container, false);
        ImageButton button_set = (ImageButton) view.findViewById(R.id.button_sett);
        button_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Settings_activity.class);
                startActivity(intent);
            }
        });

        ImageButton button_person = (ImageButton) view.findViewById(R.id.button_person);
        /*button_set.setOnClickListener(this);*/
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_sett:
                Fragment fragment = new FragmentFriendsAdd();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.personal_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                view.setVisibility(View.GONE);
                /*fragmentFriendsAdd = new FragmentFriendsAdd();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.personal_fragment, FragmentFriendsAdd.class, null);
                *//*fragmentTransaction.addToBackStack(null);*//*

                fragmentTransaction.commit();*/
            break;
        }
    }

}