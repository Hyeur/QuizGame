package com.example.quizgame;

public class Topic {
    private String topicName;
    private int StarGained;
    private String percent;

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getStarGained() {
        return StarGained;
    }

    public void setStarGained(int starGained) {
        StarGained = starGained;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
