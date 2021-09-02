package com.example.millionaire;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionAndAnswers implements Serializable {
    private String question;
    private String rightAnswer;
    private ArrayList<String> answers;

    public QuestionAndAnswers() {
        answers = new ArrayList<>();
    }

    public QuestionAndAnswers(String question, String rightAnswer, ArrayList<String> answers) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
