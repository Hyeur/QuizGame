package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import org.json.*;

import okio.Timeout;

public class MainActivity extends AppCompatActivity {

    ImageView banner;
    TextView Question;
    Button butt1;
    Button butt2;
    Button butt3;
    Button butt4;
    Helper dbHelper = new Helper(MainActivity.this,null,1);
    SQLiteDatabase db;
    List<QuestionAndAnswer> QaA = new ArrayList<QuestionAndAnswer>();
    String pAns;
    LottieAnimationView incorrect;
    LottieAnimationView correct;
    LottieAnimationView progress_bar;
    int quest_chosen;



    public String readJSON() {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = getAssets().open("questions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        return json;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        banner = (ImageView) findViewById(R.id.imageView);
        Question = (TextView) findViewById(R.id.textViewQuestion);
        butt1 = (Button) findViewById(R.id.buttonAns1);
        butt2 = (Button) findViewById(R.id.buttonAns2);
        butt3 = (Button) findViewById(R.id.buttonAns3);
        butt4 = (Button) findViewById(R.id.buttonAns4);
        incorrect = (LottieAnimationView) findViewById(R.id.lottie_layer_name);
        correct = (LottieAnimationView) findViewById(R.id.lottie_layer_name2);
        progress_bar = (LottieAnimationView) findViewById(R.id.progress_bar);




        //json parse
        dbHelper.DeleteAllQuestion();
        try {
            JSONObject object = new JSONObject(readJSON());
            JSONArray array = object.getJSONArray("question");
            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);
                String content = jsonObject.getString("content");
                String bait1 = jsonObject.getString("bait1");
                String bait2 = jsonObject.getString("bait2");
                String bait3 = jsonObject.getString("bait3");
                String diff = jsonObject.getString("diff");
                String ans = jsonObject.getString("ans");

                String[] baits ={bait1,bait2,bait3};

                try {
                    dbHelper.addQuestion(content,bait1,bait2,bait3,ans,diff);
                    QaA.add(new QuestionAndAnswer(content,baits,ans,diff));
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this,"data error",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this,"parse error",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        try {
            db = dbHelper.getWritableDatabase();
        }
        catch (SQLException exception){
            db = dbHelper.getReadableDatabase();
        }


        List<Button> butt =new ArrayList<Button>();

        //random




        randomLoadQuest(butt);



        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pAns = butt1.getText().toString();
                check(quest_chosen, butt1);

            }
        });
        butt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pAns = butt2.getText().toString();
                check(quest_chosen, butt2);
            }
        });

        butt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pAns = butt3.getText().toString();
                check(quest_chosen, butt3);
            }
        });


        butt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pAns = butt4.getText().toString();
                check(quest_chosen, butt4);
            }
        });

        //animation Listener
        incorrect.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                incorrect.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                incorrect.setVisibility(View.GONE);
                incorrect.clearAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                incorrect.setVisibility(View.GONE);
                incorrect.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        correct.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                correct.setVisibility(View.VISIBLE);
                incorrect.setVisibility(View.GONE);
                incorrect.clearAnimation();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                correct.setVisibility(View.GONE);
                correct.clearAnimation();
                randomLoadQuest(butt);
                buttonEffect();
                progress_bar.playAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                correct.setVisibility(View.GONE);
                correct.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        progress_bar.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                randomLoadQuest(butt);
                buttonEffect();
                progress_bar.playAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });



    }


    public void randomLoadQuest(List<Button> butt){
        Random rand = new Random();
        int upperbound = QaA.size();
        int quest_random = rand.nextInt(upperbound);
        butt.clear();
        butt.add(butt1);
        butt.add(butt2);
        butt.add(butt3);
        butt.add(butt4);
        int remain = 4;
        for (int i = 0;i <4;i++)
        {
            int butt_random=rand.nextInt(remain);
            if (butt.size()>1)
            {
                butt.get(butt_random).setText(QaA.get(quest_random).getBaits()[i]);
                butt.remove(butt_random);
            }
            else {
                butt.get(butt_random).setText(QaA.get(quest_random).getAnswer());
            }
            Question.setText(QaA.get(quest_random).getQuestion());
            remain-=1;
        }
        quest_chosen = quest_random;
    }




    public void check(int quest_random,Button b) {
        if (pAns.equals(QaA.get(quest_random).getAnswer())){

            Animation anim= AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounce);
            b.startAnimation(anim);
            b.setBackgroundColor(Color.parseColor("#45ff54"));
            correct.setVisibility(View.VISIBLE);
            correct.playAnimation();

            progress_bar.cancelAnimation();
            progress_bar.clearAnimation();
        }
        else {
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.blink_anim);
            b.startAnimation(animation);
            b.setBackgroundColor(Color.parseColor("#e35242"));
            incorrect.setVisibility(View.VISIBLE);
            incorrect.playAnimation();

        }
    }
    public void buttonEffect(){
        butt1.setBackgroundColor(Color.parseColor("#FFA8A8A8"));
        butt2.setBackgroundColor(Color.parseColor("#FFA8A8A8"));
        butt3.setBackgroundColor(Color.parseColor("#FFA8A8A8"));
        butt4.setBackgroundColor(Color.parseColor("#FFA8A8A8"));

        butt1.clearAnimation();
        butt2.clearAnimation();
        butt3.clearAnimation();
        butt4.clearAnimation();

    }
}