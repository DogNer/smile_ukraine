package com.example.smile_ukraine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Settings_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ImageButton btn_settings_back = (ImageButton) findViewById(R.id.button_back_sett);
        //повесили на него листенера
        btn_settings_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}