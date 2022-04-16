package com.example.smile_ukraine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DemoFragment extends Fragment {
    private TextView textView;

    public DemoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        textView = view.findViewById(R.id.txt_display);
        textView.setText(getArguments().getString("message"));

        return view;
    }

}