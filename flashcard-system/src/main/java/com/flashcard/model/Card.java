package com.flashcard.model;

public class Card {

    private String question;
    private String answer;

    private int correctCount;
    private int wrongCount;

    // өмнөх логик
    private boolean lastWasWrong;

    // хамгийн сүүлд хэд дэх алдаа байсан
    private long lastWrongOrder;

    public Card(String question, String answer) {

        this.question = question;
        this.answer = answer;

        this.correctCount = 0;
        this.wrongCount = 0;

        this.lastWasWrong = false;
        this.lastWrongOrder = -1;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public boolean isLastWasWrong() {
        return lastWasWrong;
    }

    public void setLastWasWrong(boolean lastWasWrong) {
        this.lastWasWrong = lastWasWrong;
    }

    public long getLastWrongOrder() {
        return lastWrongOrder;
    }

    public void setLastWrongOrder(long lastWrongOrder) {
        this.lastWrongOrder = lastWrongOrder;
    }

    public void incrementCorrect() {
        correctCount++;
    }

    public void incrementWrong() {
        wrongCount++;
    }
}