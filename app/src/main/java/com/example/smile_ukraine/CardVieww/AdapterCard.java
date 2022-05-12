package com.example.smile_ukraine.CardVieww;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.smile_ukraine.FriendsActivity;
import com.example.smile_ukraine.R;
import com.example.smile_ukraine.Settings_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AdapterCard extends PagerAdapter {

    String profileid;
    FirebaseUser firebaseUser;

    private Context context;
    private ArrayList<MyModelCard> modelArrayList;

    public AdapterCard(Context context, ArrayList<MyModelCard> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.carditem, container, false);

        TextView titleCard = view.findViewById(R.id.titleCard);

        MyModelCard model = modelArrayList.get(position);
        String title = model.getTitle();

        titleCard.setText(title);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 1){
                    Intent intent = new Intent(view.getContext(), FriendsActivity.class);
                    view.getContext().startActivity(intent);
                }
                Toast.makeText(context, title , Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
