package com.example.quizgame;

import static androidx.navigation.Navigation.findNavController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static com.example.quizgame.config.AM_NHAC_SCORE;
import static com.example.quizgame.config.AM_THUC_SCORE;
import static com.example.quizgame.config.DIA_LY_SCORE;
import static com.example.quizgame.config.JOIN_DATE;
import static com.example.quizgame.config.LEVEL_COMPLETED;
import static com.example.quizgame.config.LICH_SU_SCORE;
import static com.example.quizgame.config.PLAYER_NAME;
import static com.example.quizgame.config.PLAYER_STAR;
import static com.example.quizgame.config.QUESTION_ANSWERED;
import static com.example.quizgame.config.RATE;
import static com.example.quizgame.config.VAN_HOA_SCORE;

public class Main_menu extends AppCompatActivity {


    Helper dbHelper = new Helper(Main_menu.this, null, 1);
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);






        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.fragmentContainerView);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);



        try {

            PlayerInfo PI = dbHelper.getAllPlayerStats();
            Topic TP1 = dbHelper.getTopicsByName("Địa Lý");
            Topic TP2 = dbHelper.getTopicsByName("Âm Nhạc");
            Topic TP3 = dbHelper.getTopicsByName("Lịch Sử");
            Topic TP4 = dbHelper.getTopicsByName("Văn Hóa");
            Topic TP5 = dbHelper.getTopicsByName("Ẩm Thực");
            config_getPlayerStats(PI);

            config_getAllTopicStasts(TP1,TP2,TP3,TP4,TP5);

        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Main_menu.this,"Main_menu error",Toast.LENGTH_SHORT).show();
        }


    }

    private void config_getAllTopicStasts(Topic tp1,Topic tp2,Topic tp3,Topic tp4,Topic tp5) {
        DIA_LY_SCORE = tp1.getStarGained();
        AM_NHAC_SCORE = tp2.getStarGained();
        LICH_SU_SCORE = tp3.getStarGained();
        VAN_HOA_SCORE = tp4.getStarGained();
        AM_THUC_SCORE = tp5.getStarGained();
    }

    public void config_getPlayerStats(PlayerInfo PI){
        PLAYER_NAME = PI.getName();
        PLAYER_STAR = PI.getStar();
        LEVEL_COMPLETED = PI.getLeveled();
        JOIN_DATE = PI.getJoindate();
        QUESTION_ANSWERED = PI.getQuestionsAnswered();
        RATE = PI.getRate();

    }

}