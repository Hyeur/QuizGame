package com.example.quizgame;

public class Level {
    private String topic;
    private int totalQuestions;
    private int totalPoint;
    private int correctAnswers;

    public Level(String topic, int totalQuestions) {
        this.topic = topic;
        this.totalQuestions = totalQuestions;
    }

    public Level() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

}

