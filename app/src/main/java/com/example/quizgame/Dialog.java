package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Dialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_dialog);

        String topic = getIntent().getStringExtra("topic");


        Button g5 = (Button) findViewById(R.id.g5);
        Button g10 = (Button) findViewById(R.id.g10);
        Button g15 = (Button) findViewById(R.id.g15);



        g5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_g5 = new Intent(Dialog.this,Loading.class);
                intent_g5.putExtra("pack","5");
                intent_g5.putExtra("topic",topic);
                startActivity(intent_g5);
            }
        });
        g10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_g10 = new Intent(Dialog.this,Loading.class);
                intent_g10.putExtra("pack","10");
                intent_g10.putExtra("topic",topic);
                startActivity(intent_g10);
            }
        });
        g15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_g15 = new Intent(Dialog.this,Loading.class);
                intent_g15.putExtra("pack","15");
                intent_g15.putExtra("topic",topic);
                startActivity(intent_g15);
            }
        });





    }
}