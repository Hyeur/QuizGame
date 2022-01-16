package com.example.quizgame;

import static com.example.quizgame.config.PLAYER_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Helper extends SQLiteOpenHelper {

    String str_tblQuestions = "create table Questions(id integer primary key autoincrement, content text not null, bait1 text,bait2 text,bait3 text,ans text not null,topic text, pass text);";
    String str_tblTopic = "create table Topic(id integer primary key autoincrement, topic text , star integer, percent integer);";
    String str_tblPlayer = "create table Player(id integer primary key autoincrement,name text not null,star integer,leveled integer, joindate text, questions integer, rate integer);";


    public Helper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "database", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(str_tblQuestions);
        db.execSQL(str_tblTopic);
        db.execSQL(str_tblPlayer);

//        if (isEmptyTopic()){
//            db.execSQL("INSERT INTO Topic (topic) VALUES('Địa Lý');");
//            db.execSQL("INSERT INTO Topic (topic) VALUES('Âm Nhạc');");
//            db.execSQL("INSERT INTO Topic (topic) VALUES('Lịch Sử');");
//            db.execSQL("INSERT INTO Topic (topic) VALUES('Văn Hóa');");
//            db.execSQL("INSERT INTO Topic (topic) VALUES('Ẩm Thực');");
//        }

        db.execSQL("INSERT INTO Player (name, star, leveled, joindate, questions, rate) VALUES('You',100,100, 'January 2022',100,100);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updatePlayerStats(int Star, int leveled, int questions) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("star", Star);
        contentValues.put("leveled", leveled);
        contentValues.put("questions", questions);
//        int rate = (readPercentByTopic("Địa Lý") + readPercentByTopic("Âm Nhạc") + readPercentByTopic("Lịch Sử")
//                + readPercentByTopic("Văn Hóa") + readPercentByTopic("Ẩm Thực")) / 5;
//
//        contentValues.put("rate", rate);
        String whereClause = "id=?";
        db.update("Player", contentValues, whereClause, new String[]{"1"});
        db.close();
    }

    public PlayerInfo getAllPlayerStats() {
        SQLiteDatabase db = this.getReadableDatabase();
        PlayerInfo playerInfo = new PlayerInfo();
        Cursor c = db.rawQuery("select * from Player where id = 1", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                playerInfo.setName(c.getString(1));
                playerInfo.setStar(c.getInt(2));
                playerInfo.setLeveled(c.getInt(3));
                playerInfo.setJoindate(c.getString(4));
                playerInfo.setQuestionsAnswered(c.getInt(5));
                playerInfo.setRate(c.getInt(6));
                c.moveToNext();
                return playerInfo;
            }
        }
        c.close();
        return playerInfo;
    }

    public Topic getALlTopics() {
        SQLiteDatabase db = this.getReadableDatabase();
        Topic tp = new Topic();
        Cursor c = db.rawQuery("select * from Topic", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                tp.setTopicName(c.getString(1));
                tp.setStarGained(c.getInt(2));
                c.moveToNext();
                return tp;
            }
        }
        c.close();
        return tp;
    }

    public Topic getTopicsByName(String TopicName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Topic tp = new Topic();
        Cursor c = db.rawQuery("select topic,star from Topic where topic= " + TopicName + "", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                tp.setTopicName(c.getString(0));
                tp.setStarGained(c.getInt(1));
                c.moveToNext();
                return tp;
            }
        }
        c.close();
        return tp;
    }


    public void addStar(int starPoint) {

    }

    public void addleveled(int levelesd) {

    }

    public void addQuestionsAnswered(int amount) {

    }



    public boolean isEmptyTopic() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM Topic";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) return false;
        else return true;
    }

    public void updateTopicStarByTopic(String topic, int star) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("star", star);
        String whereClause = "topic=?";
        db.update("Topic", contentValues, whereClause, new String[]{topic});
        db.close();
    }


    public int readPercentByTopic(String inputTopic) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select percent from Topic where topic =" + inputTopic;
        int percent = 0;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                percent += c.getInt(0);
                c.moveToNext();
            }
        }
        c.close();
        return percent;
    }

    public void addQuestion(String Question, String bait1, String bait2, String bait3, String ans, String topic, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", Question);
        contentValues.put("bait1", bait1);
        contentValues.put("bait2", bait2);
        contentValues.put("bait3", bait3);
        contentValues.put("ans", ans);
        contentValues.put("topic", topic);
        contentValues.put("pass", pass);
        db.insert("Questions", null, contentValues);
        db.close();
    }

//    public List<QuestionAndAnswer> getAllQuestByTopic(String TopicName) {
//        List<QuestionAndAnswer> arr = new ArrayList<QuestionAndAnswer>();
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "select content,bait1,bait2,bait3,ans,pass from Questions where pass = 'no' and topic =" + TopicName;
//        String currentQuestions = "";
//        Cursor c = db.rawQuery(query, null);
//        if (c.moveToFirst()) {
//            while (!c.isAfterLast()) {
//                String[] baits = {c.getString(2),c.getString(3),c.getString(4)};
//                arr.add(new QuestionAndAnswer(c.getString(1),baits,c.getString(5),c.getString(6)));
//                c.moveToNext();
//            }
//        }
//        c.close();
//        return arr;
//    }

    public String ReadAllQuestion() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select content from Questions";
        String allQuestions = "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                allQuestions += c.getString(0);
                c.moveToNext();
            }
        }
        c.close();
        return allQuestions;
    }

    public void DeleteQuestionById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String rowId = Integer.toString(id);
        db.delete("Questions", "id=?", new String[]{rowId});
        db.close();
    }

    public void DeleteAllQuestion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Questions");
        db.close();
    }

    public boolean isEmpty(String TableName) {

        SQLiteDatabase database = this.getReadableDatabase();
        long NoOfRows = DatabaseUtils.queryNumEntries(database, TableName);

        if (NoOfRows == 0) {
            return true;
        } else {
            return false;
        }
    }
}
