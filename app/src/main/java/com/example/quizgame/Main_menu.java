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

public class Main_menu extends AppCompatActivity {

    ImageView Background;
    ImageView AvatarProfile;
    TextView ProfilePlayerName;
    TextView ProfilePlayerStar;
    TextView LevelsCompleted;
    TextView JoinDate;
    TextView QuestionsAnswered;
    TextView CorrectAnswers;
    Helper dbHelper = new Helper(Main_menu.this, null, 1);
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);




        Background = findViewById(R.id.imageViewBackground);
        AvatarProfile  = findViewById(R.id.imageViewAvatarProfile);
        ProfilePlayerName = findViewById(R.id.textViewEditableName);
        ProfilePlayerStar = findViewById(R.id.textViewStarPointProfile);
        LevelsCompleted = findViewById(R.id.textViewLevelsCompleted);
        JoinDate = findViewById(R.id.textViewJoinDate);
        QuestionsAnswered = findViewById(R.id.questionAnswered);
        CorrectAnswers = findViewById(R.id.percenrCorrectAnswer);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.fragmentContainerView);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);


//        try {
//            db = dbHelper.getWritableDatabase();
//        } catch (SQLException exception) {
//            db = dbHelper.getReadableDatabase();
//        }

        try {
            PlayerInfo PI = dbHelper.getAllPlayerStats();
            ProfilePlayerName.setText(PI.getName());
            ProfilePlayerStar.setText(PI.getStar());
            LevelsCompleted.setText(PI.getLeveled());
            JoinDate.setText(PI.getJoindate());
            QuestionsAnswered.setText(PI.getQuestionsAnswered());
            CorrectAnswers.setText(PI.getRate()+"%");
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Main_menu.this,"PI error",Toast.LENGTH_SHORT).show();
        }

    }

}