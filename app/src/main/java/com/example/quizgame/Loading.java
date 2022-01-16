package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.Toast;

import static com.example.quizgame.config.AM_NHAC_SCORE;
import static com.example.quizgame.config.AM_THUC_SCORE;
import static com.example.quizgame.config.DIA_LY_SCORE;
import static com.example.quizgame.config.LICH_SU_SCORE;
import static com.example.quizgame.config.PLAYER_STAR;
import static com.example.quizgame.config.VAN_HOA_SCORE;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

        Helper dbHelper = new Helper(Loading.this, null, 1);
        SQLiteDatabase db;

        String topic = getIntent().getStringExtra("topic");
        String pack = getIntent().getStringExtra("pack");
        String main = getIntent().getStringExtra("from");


        CountDownTimer Timer = new CountDownTimer(1500, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                if (main != null){
                    String TOPIC = getIntent().getStringExtra("outputTopic");
                    String PACK = getIntent().getStringExtra("outputPack");
                    int Point = getIntent().getIntExtra("outputPoint",0);
                    int correctAns = getIntent().getIntExtra("outputCorrect",0);
                    Intent save = new Intent(Loading.this,Main_menu.class);

                    dbHelper.updateTopicStarByTopic(TOPIC,correctAns);
                    PLAYER_STAR = Point;
                    switch (TOPIC) {
                        case "Địa Lý":
                            DIA_LY_SCORE = correctAns;
                            break;
                        case "Âm Nhạc":
                            AM_NHAC_SCORE = correctAns;
                            break;
                        case "Lịch Sử":
                            LICH_SU_SCORE = correctAns;
                            break;
                        case "Văn Hóa":
                            VAN_HOA_SCORE = correctAns;
                            break;
                        case "Ẩm Thực":
                            AM_THUC_SCORE = correctAns;
                            break;
                    }
                    finish();
                    startActivity(save);

                }
                else {
                    Intent play = new Intent(Loading.this,MainActivity.class);
                    play.putExtra("topic",topic);
                    play.putExtra("pack",pack);
                    finish();
                    startActivity(play);
                }

            }
        }.start();



    }
}