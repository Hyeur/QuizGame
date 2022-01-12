package com.example.quizgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Helper extends SQLiteOpenHelper {

    String str_tblQuestions = "create table Quetions(id integer primary key autoincrement, content text not null, bait1 text,bait2 text,bait3 text,ans text not null, diff text not null, topic text);";
    String str_tblArchive = "create table Archive(id integer primary key, Questions integer not null, Point integer, ratio text not null,aveSpeed text);";


    public Helper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "database", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(str_tblQuestions);
        db.execSQL(str_tblArchive);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
