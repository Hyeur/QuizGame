package com.example.quizgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Helper extends SQLiteOpenHelper {

    String str_tblQuestions = "create table Quetions(id integer primary key autoincrement, content text not null, bait1 text,bait2 text,bait3 text,ans text not null, diff text not null, topic text);";
    String str_tblTopic = "create table Topic(id integer primary key autoincrement, topic text , star integer, percent integer);";
    String str_tblPlayer = "create table Player(id integer primary key autoincrement,name text not null,star integer,leveled integer, joindate text, questions integer, rate integer);";

    Calendar calendar = Calendar.getInstance();
    String currentMonthAndYear = "" + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR);

    public Helper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "database", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(str_tblQuestions);
        db.execSQL(str_tblTopic);
        db.execSQL(str_tblPlayer);

//        if (getCountTopic("Địa Lý") <= 0) {
//            db.execSQL("INSERT INTO Topic (topic) VALUES('Địa Lý');");
//        }
//        if (getCountTopic("Âm Nhạc") <= 0) {
//            db.execSQL("INSERT INTO Topic (topic) VALUES('Âm Nhạc');");
//        }
//        if (getCountTopic("Lịch Sử") <= 0) {
//            db.execSQL("INSERT INTO Topic (topic) VALUES('Lịch Sử');");
//        }
//        if (getCountTopic("Văn Hóa") <= 0) {
//            db.execSQL("INSERT INTO Topic (topic) VALUES('Văn Hóa');");
//        }
//        if (getCountTopic("Ẩm Thực") <= 0) {
//            db.execSQL("INSERT INTO Topic (topic) VALUES('Ẩm Thực');");
//        }

        db.execSQL("INSERT INTO Player (name, star, leveled, joindate, questions, rate) VALUES('You',1,1,'test',1,1);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updatePlayerStats(String name, int Star, int leveled, int questions) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("star", Star);
        contentValues.put("leveled", leveled);
        contentValues.put("questions", questions);

        int rate = (readPercentByTopic("Địa Lý") + readPercentByTopic("Âm Nhạc") + readPercentByTopic("Lịch Sử")
                + readPercentByTopic("Văn Hóa") + readPercentByTopic("Ẩm Thực")) / 5;

        contentValues.put("rate", rate);
        String whereClause = "id=?";
        db.update("Topic", contentValues, whereClause, new String[]{"1"});
        db.close();
    }

    public PlayerInfo getAllPlayerStats(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from Player", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                PlayerInfo playerInfo = new PlayerInfo(c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3),c.getString(4),c.getInt(5),c.getInt(6));
                System.out.println(c.getInt(0));
                c.moveToNext();
                return playerInfo;
            }
        }
        c.close();
        return null;
    }



    public void addStar(int starPoint){

    }
    public void addleveled(int levelesd){

    }
    public void addQuestionsAnswered(int amount){

    }


    public boolean havePlayer(String tableName){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + tableName,null);
        return c.moveToFirst();
    }


    public int getCountTopic(String name) {
        int count;
        SQLiteDatabase db = getWritableDatabase();
        String query = "select count(*) from Topic where topic =" + name;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        count = c.getInt(0);
        if (!c.isClosed()) {
            c.close();
        }
        return count;
    }

    public void updateTopicStarByTopic(String topic, int star, String percent) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("star", star);
        contentValues.put("percent", percent);
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

    public void addQuestion(String Question, String bait1, String bait2, String bait3, String ans, String diff) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", Question);
        contentValues.put("bait1", bait1);
        contentValues.put("bait2", bait2);
        contentValues.put("bait3", bait3);
        contentValues.put("ans", ans);
        contentValues.put("diff", diff);
        db.insert("Quetions", null, contentValues);
        db.close();
    }

    public String readQuestion(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select content from Quetions where id =" + id;
        String currentQuestion = "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                currentQuestion += c.getString(0);
                c.moveToNext();
            }
        }
        c.close();
        return currentQuestion;
    }

    public String ReadAllQuestion() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select content from Quetions";
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
        db.execSQL("delete from Quetions");
        db.close();
    }
}
