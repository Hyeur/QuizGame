package com.example.quizgame;

public class PlayerInfo {

    private int id;
    private String name;
    private int star;
    private int leveled;
    private String joindate;
    private int QuestionsAnswered;
    private int rate;

    public PlayerInfo(int id, String name, int star, int leveled, String joindate, int questionsAnswered, int rate) {
        this.id = id;
        this.name = name;
        this.star = star;
        this.leveled = leveled;
        this.joindate = joindate;
        QuestionsAnswered = questionsAnswered;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getLeveled() {
        return leveled;
    }

    public void setLeveled(int leveled) {
        this.leveled = leveled;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public int getQuestionsAnswered() {
        return QuestionsAnswered;
    }

    public void setQuestionsAnswered(int questionsAnswered) {
        QuestionsAnswered = questionsAnswered;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
