package com.example.myastronomy;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Quiz implements Serializable {
    public final String collection = "tests";
    public String title;
    public String code;
    public ArrayList<String> questions;
    public ArrayList<String> choices;
    public ArrayList<String> correctAnswer;

    public Quiz(String code,
                String title,
                ArrayList<String> questions,
                ArrayList<String> choices,
                ArrayList<String> correctAnswer) {
        this.code = code;
        this.title = title;
        this.questions = questions;
        this.choices =  choices;
        this.correctAnswer =correctAnswer;
    }
    Quiz(Quiz c) {
        this.code = c.code;
        this.title = c.title;
        this.questions = c.questions;
        this.choices = c.choices;
        this.correctAnswer = c.correctAnswer;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuestions(int a) {
        return questions.get(a);
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public String setQuestions(int a) {
        String question = questions.get(a);
        return question;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "collection='" + collection + '\'' +
                ", code='" + code + '\'' +
                ", questions=" + Arrays.toString(new ArrayList[]{questions}) +
                ", choices=" + Arrays.toString(new ArrayList[]{choices}) +
                ", correctAnswer=" + Arrays.toString(new ArrayList[]{correctAnswer}) +
                '}';
    }


    // to refactor later i cant think of anything better

    public String getChoiceN(int a) {
        String choice = choices.get(a);
        return choice;
    }

    public String getCorrectAnswer(int a) {
        return correctAnswer.get(a);
    }

    // Copy factory
    public static Quiz newInstance(Quiz quiz) {
        return new Quiz(quiz);
    }
}
