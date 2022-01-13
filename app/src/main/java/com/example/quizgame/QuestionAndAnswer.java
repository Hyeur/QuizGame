package com.example.quizgame;

public class QuestionAndAnswer {
    private String difficulty;
    private String topic;
    private int point;
    private String question;
    private String[] baits; // wrong answers
    private String answer;
    private float speed;
    private int imgDescription;
    private boolean answered;


    public QuestionAndAnswer() {
    }

    public QuestionAndAnswer(String question, String[] baits, String answer, String difficulty) {
        this.difficulty = difficulty;
        this.question = question;
        this.baits = baits;
        this.answer = answer;
    }

    public QuestionAndAnswer(String question, String[] baits, String answer, String difficulty,int imgDescription, boolean answered) {
        this.difficulty = difficulty;
        this.question = question;
        this.baits = baits;
        this.answer = answer;
        this.imgDescription = imgDescription;
        this.answered = answered;
    }

    public QuestionAndAnswer(String difficulty, String topic, int point, String question, String[] baits, String answer, float speed, int imgDescription) {
        this.difficulty = difficulty;
        this.topic = topic;
        this.point = point;
        this.question = question;
        this.baits = baits;
        this.answer = answer;
        this.speed = speed;
        this.imgDescription = imgDescription;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getBaits() {
        return baits;
    }

    public void setBaits(String[] baits) {
        this.baits = baits;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getImgDescription() {
        return imgDescription;
    }

    public void setImgDescription(int imgDescription) {
        this.imgDescription = imgDescription;
    }
}
