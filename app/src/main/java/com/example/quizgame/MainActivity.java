package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
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

import org.json.*;

import okio.Timeout;

public class MainActivity extends AppCompatActivity {

    ImageView banner;
    TextView Question;
    Button butt1;
    Button butt2;
    Button butt3;
    Button butt4;
    Helper dbHelper = new Helper(MainActivity.this, null, 1);
    SQLiteDatabase db;
    List<QuestionAndAnswer> QaA = new ArrayList<QuestionAndAnswer>();
    String pAns;
    LottieAnimationView incorrect;
    LottieAnimationView correct;
    LottieAnimationView progress_bar;
    int quest_chosen;
    List<Integer> loaded_quest = new ArrayList<Integer>();

    TextView Point;

    int wrongAns = 0;
    int correctAns = 0;
    int QuestMount = 0;








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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MediaPlayer player = MediaPlayer.create(this, R.raw.unta);
        player.setLooping(true); // Set looping
        player.setVolume(5, 5);
        player.start();

        banner = (ImageView) findViewById(R.id.imageView);
        Question = (TextView) findViewById(R.id.textViewQuestion);
        butt1 = (Button) findViewById(R.id.buttonAns1);
        butt2 = (Button) findViewById(R.id.buttonAns2);
        butt3 = (Button) findViewById(R.id.buttonAns3);
        butt4 = (Button) findViewById(R.id.buttonAns4);
        incorrect = (LottieAnimationView) findViewById(R.id.lottie_layer_name);
        correct = (LottieAnimationView) findViewById(R.id.lottie_layer_name2);
        progress_bar = (LottieAnimationView) findViewById(R.id.progress_bar);
        Point = (TextView) findViewById(R.id.textViewStarPointPlaying);

        String TOPIC = getIntent().getStringExtra("topic");
        String pack = getIntent().getStringExtra("pack");
        int PACK = Integer.parseInt(pack);



        try {

            updatePoint();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,PLAYER_STAR,Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this,"updatePoint error",Toast.LENGTH_SHORT).show();
        }




        //json parse
        dbHelper.DeleteAllQuestion();
        QaA.clear();

        if (dbHelper.isEmpty("Questions"))
        {
            try {
                JSONObject object = new JSONObject(readJSON());
                JSONArray array = object.getJSONArray("question");
                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsonObject = array.getJSONObject(i);
                    String content = jsonObject.getString("content");
                    String bait1 = jsonObject.getString("bait1");
                    String bait2 = jsonObject.getString("bait2");
                    String bait3 = jsonObject.getString("bait3");
                    String ans = jsonObject.getString("ans");
                    String topic = jsonObject.getString("topic");
                    String imgDesc = jsonObject.getString("url");
                    String isAnswered = "no";

                    String[] baits = {bait1, bait2, bait3};

                    try {
                        dbHelper.addQuestion(content, bait1, bait2, bait3, ans,topic,isAnswered);
                        int img = picPicker(imgDesc);


                        if (topic.equals(TOPIC) && QuestMount < PACK)
                        {
                            QuestMount+=1;
                            QaA.add(new QuestionAndAnswer(content, baits, ans, img));
                        }

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "data error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "parse error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        }
//        else {
//            try {
//                List<QuestionAndAnswer> temp = new ArrayList<QuestionAndAnswer>();
//                temp = dbHelper.getAllNewQuestions(TOPIC);
//                for (int i=0; i < PACK;i++){
//                    int img;
//                    switch (TOPIC) {
//                        case "Âm Nhạc":
//                            img = R.drawable.music;
//                            break;
//                        case "mrdam":
//                            img = R.drawable.damvinhhung;
//                            break;
//                        case "Văn Hóa":
//                            img = R.drawable.van_hoa;
//                            break;
//                        case "Ca Dao Tục Ngữ":
//                            img = R.drawable.treodebancho;
//                            break;
//                        case "Đời Sống":
//                            img = R.drawable.hats;
//                            break;
//                        case "Ẩm Thực":
//                            img = R.drawable.food;
//                            break;
//                        case "Cổ Tích":
//                            img = R.drawable.fairy_tale;
//                            break;
//                        case "Địa Lý":
//                            img = R.drawable.georaphy;
//                            break;
//                        case "Lịch Sử":
//                            img = R.drawable.history;
//                            break;
//                        case "Văn Học":
//                            img = R.drawable.shakespeare;
//                            break;
//                        default:
//                            img = R.drawable.ic_launcher_foreground;
//                    }
//                    QuestionAndAnswer TEMP = temp.get(i);
//                    QaA.add(new QuestionAndAnswer(TEMP.getQuestion(),TEMP.getBaits(),TEMP.getAnswer(), img));
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(MainActivity.this, "already load error", Toast.LENGTH_SHORT).show();
//            }
//        }



//        try {
//            db = dbHelper.getWritableDatabase();
//        } catch (SQLException exception) {
//            db = dbHelper.getReadableDatabase();
//        }


        List<Button> butt = new ArrayList<Button>();

        //random


        randomLoadQuest(butt,player);


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
                banner.setImageResource(android.R.color.transparent);
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
                MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.correct_answer_sound_effect);
                mp.setVolume(200,200);
                mp.start();
                correct.setVisibility(View.VISIBLE);
                incorrect.setVisibility(View.GONE);
                incorrect.clearAnimation();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                correct.setVisibility(View.GONE);
                correct.clearAnimation();
                randomLoadQuest(butt,player);
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
                randomLoadQuest(butt,player);
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

    public void updatePoint(){
        PlayerInfo PI = dbHelper.getAllPlayerStats();
        Point.setText(PLAYER_STAR+"");
    }


    public void randomLoadQuest(List<Button> butt,MediaPlayer player) {
        if (loaded_quest.size() == QaA.size()){
            loaded_quest.clear();
            String TOPIC = getIntent().getStringExtra("topic");
            String pack = getIntent().getStringExtra("pack");
            int PACK = Integer.parseInt(pack);

            Level level = new Level(TOPIC,PACK);
            level.setTotalPoint(getIntPoint());

            level.setCorrectAnswers(correctAns);


            PlayerInfo PI = dbHelper.getAllPlayerStats();
            dbHelper.updatePlayerStats(level.getTotalPoint(),PI.getLeveled() + 1, PACK);
            onDestroy(player);
            finish();

            Intent loading = new Intent(this,Loading.class);
            loading.putExtra("outputTopic",TOPIC);
            loading.putExtra("outputPoint",level.getTotalPoint());
//            loading.putExtra("outputRate",RATE);
            loading.putExtra("from","main");
            //Star point
            loading.putExtra("outputPack",pack);
            this.startActivity(loading);
        }
        else if (QaA.isEmpty()) {
            Intent loading = new Intent(this,Loading.class);
            loading.putExtra("from","main");
            loading.putExtra("done","done");
            this.startActivity(loading);
        }

        Random rand = new Random();
        int upperbound = QaA.size();

        int quest_random = rand.nextInt(upperbound);

        for (int y =0;y < loaded_quest.size();y++) {
            while (loaded_quest.contains(quest_random)) {
                quest_random = rand.nextInt(upperbound);
            }
        }

        butt.clear();
        butt.add(butt1);
        butt.add(butt2);
        butt.add(butt3);
        butt.add(butt4);
        int remain = 4;
        for (int i = 0; i < 4; i++) {
            int butt_random = rand.nextInt(remain);
            if (butt.size() > 1) {
                butt.get(butt_random).setText(QaA.get(quest_random).getBaits()[i]);
                butt.remove(butt_random);
            } else {
                butt.get(butt_random).setText(QaA.get(quest_random).getAnswer());
            }
            Question.setText(QaA.get(quest_random).getQuestion());
            banner.setBackgroundResource(QaA.get(quest_random).getImgDescription());
            remain -= 1;
        }
        loaded_quest.add(quest_random);
        quest_chosen = quest_random;
    }


    public void check(int quest_random, Button b) {
        if (pAns.equals(QaA.get(quest_random).getAnswer())) {
            correctAns += 1;
            addPoint(10);
            QaA.get(quest_random).setAnswered("yes");

            dbHelper.passedQuestion(QaA.get(quest_random).getAnswer());


            Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
            b.startAnimation(anim);
            b.setBackgroundColor(Color.parseColor("#45ff54"));
            correct.setVisibility(View.VISIBLE);
            correct.playAnimation();

            progress_bar.cancelAnimation();
            progress_bar.clearAnimation();
        } else {
            wrongAns +=1;
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.blink_anim);
            b.startAnimation(animation);
            b.setBackgroundColor(Color.parseColor("#e35242"));
            incorrect.setVisibility(View.VISIBLE);
            incorrect.playAnimation();

        }
    }

    public void addPoint(int amount){
        int currentPoint = Integer.parseInt(Point.getText().toString());
        currentPoint+=amount;
        Point.setText(""+currentPoint);
    }

    public int getIntPoint(){
        return Integer.parseInt(Point.getText().toString());
    }

    public void buttonEffect() {
        butt1.setBackgroundColor(Color.parseColor("#FFA8A8A8"));
        butt2.setBackgroundColor(Color.parseColor("#FFA8A8A8"));
        butt3.setBackgroundColor(Color.parseColor("#FFA8A8A8"));
        butt4.setBackgroundColor(Color.parseColor("#FFA8A8A8"));

        butt1.clearAnimation();
        butt2.clearAnimation();
        butt3.clearAnimation();
        butt4.clearAnimation();

        Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);
        Animation fadeInS = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadeinslow);
        Question.startAnimation(fadeIn);
        banner.startAnimation(fadeInS);

        Animation leftToRight = AnimationUtils.loadAnimation(MainActivity.this, R.anim.lefttoright);
        butt1.startAnimation(leftToRight);
        butt2.startAnimation(leftToRight);
        butt3.startAnimation(leftToRight);
        butt4.startAnimation(leftToRight);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        leftToRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public int picPicker(String imgDesc){
        int img = 0;

        switch (imgDesc) {
            case "dl_1":
                img = R.drawable.dl_1;
                break;
            case "dl_2":
                img = R.drawable.dl_2;
                break;
            case "dl_3":
                img = R.drawable.dl_3;
                break;
            case "dl_4":
                img = R.drawable.dl_4;
                break;
            case "dl_5":
                img = R.drawable.dl_5;
                break;
            case "dl_6":
                img = R.drawable.dl_6;
                break;
            case "dl_7":
                img = R.drawable.dl_7;
                break;
            case "dl_8":
                img = R.drawable.dl_8;
                break;
            case "dl_9":
                img = R.drawable.dl_9;
                break;
            case "dl_10":
                img = R.drawable.dl_10;
                break;
            case "dl_11":
                img = R.drawable.dl_11;
                break;
            case "dl_12":
                img = R.drawable.dl_12;
                break;
            case "dl_13":
                img = R.drawable.dl_13;
                break;
            case "dl_14":
                img = R.drawable.dl_14;
                break;
            case "dl_15":
                img = R.drawable.dl_15;
                break;
            case "an_1":
                img = R.drawable.an_1;
                break;
            case "an_2":
                img = R.drawable.an_2;
                break;
            case "an_3":
                img = R.drawable.an_3;
                break;
            case "an_4":
                img = R.drawable.an_4;
                break;
            case "an_5":
                img = R.drawable.an_5;
                break;
            case "an_6":
                img = R.drawable.an_6;
                break;
            case "an_7":
                img = R.drawable.an_7;
                break;
            case "an_8":
                img = R.drawable.an_8;
                break;
            case "an_9":
                img = R.drawable.an_9;
                break;
            case "an_10":
                img = R.drawable.an_10;
                break;
            case "an_11":
                img = R.drawable.an_11;
                break;
            case "an_12":
                img = R.drawable.an_12;
                break;
            case "an_13":
                img = R.drawable.an_13;
                break;
            case "an_14":
                img = R.drawable.an_14;
                break;
            case "an_15":
                img = R.drawable.an_15;
                break;
            case "ls_1":
                img = R.drawable.ls_1;
                break;
            case "ls_2":
                img = R.drawable.ls_2;
                break;
            case "ls_3":
                img = R.drawable.ls_3;
                break;
            case "ls_4":
                img = R.drawable.ls_4;
                break;
            case "ls_5":
                img = R.drawable.ls_5;
                break;
            case "ls_6":
                img = R.drawable.ls_6;
                break;
            case "ls_7":
                img = R.drawable.ls_7;
                break;
            case "ls_8":
                img = R.drawable.ls_8;
                break;
            case "ls_9":
                img = R.drawable.ls_9;
                break;
            case "ls_10":
                img = R.drawable.ls_10;
                break;
            case "ls_11":
                img = R.drawable.ls_11;
                break;
            case "ls_12":
                img = R.drawable.ls_12;
                break;
            case "ls_13":
                img = R.drawable.ls_13;
                break;
            case "ls_14":
                img = R.drawable.ls_14;
                break;
            case "ls_15":
                img = R.drawable.ls_15;
                break;
            case "ls_16":
                img = R.drawable.ls_16;
                break;
            case "vh_1":
                img = R.drawable.vh_1;
                break;
            case "vh_2":
                img = R.drawable.vh_2;
                break;
            case "vh_3":
                img = R.drawable.vh_3;
                break;
            case "vh_4":
                img = R.drawable.vh_4;
                break;
            case "vh_5":
                img = R.drawable.vh_5;
                break;
            case "vh_6":
                img = R.drawable.vh_6;
                break;
            case "vh_7":
                img = R.drawable.vh_7;
                break;
            case "vh_8":
                img = R.drawable.vh_8;
                break;
            case "vh_9":
                img = R.drawable.vh_9;
                break;
            case "vh_10":
                img = R.drawable.vh_10;
                break;
            case "vh_11":
                img = R.drawable.vh_11;
                break;
            case "vh_12":
                img = R.drawable.vh_12;
                break;
            case "vh_13":
                img = R.drawable.vh_13;
                break;
            case "vh_14":
                img = R.drawable.vh_14;
                break;
            case "vh_15":
                img = R.drawable.vh_15;
                break;
            case "at_1":
                img = R.drawable.at_1;
                break;
            case "at_2":
                img = R.drawable.at_2;
                break;
            case "at_3":
                img = R.drawable.at_3;
                break;
            case "at_4":
                img = R.drawable.at_4;
                break;
            case "at_5":
                img = R.drawable.at_5;
                break;
            case "at_6":
                img = R.drawable.at_6;
                break;
            case "at_7":
                img = R.drawable.at_7;
                break;
            case "at_8":
                img = R.drawable.at_8;
                break;
            case "at_9":
                img = R.drawable.at_9;
                break;
            case "at_10":
                img = R.drawable.at_10;
                break;
            case "at_11":
                img = R.drawable.at_11;
                break;
            case "at_12":
                img = R.drawable.at_12;
                break;
            case "at_13":
                img = R.drawable.at_13;
                break;
            case "at_14":
                img = R.drawable.at_14;
                break;
            case "at_15":
                img = R.drawable.at_15;
                break;
            default: img = R.drawable.ic_launcher_foreground;
        }


        return img;
    }

    protected void onDestroy( MediaPlayer player) {
        if (player.isPlaying()) {
            player.stop();
            player.release();
            player = null;
        }
    }
}