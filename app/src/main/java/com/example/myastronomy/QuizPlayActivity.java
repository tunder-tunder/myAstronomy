package com.example.myastronomy;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizPlayActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button answer1, answer2, answer3, answer4;
    TextView question, title_quiz, question_size;
    int score, index;
    final Quiz[] clone = new Quiz[1];
    String q_answer;
    int step;
    int questions_len;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);
        step = 0;

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String code = intent.getStringExtra("code");


        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
//        answer4 = findViewById(R.id.answer4);

        question = findViewById(R.id.question);
        title_quiz = findViewById(R.id.title_quiz);
        question_size = findViewById(R.id.question_size);



        final Quiz[] newQuiz = new Quiz[1];

        db.collection("tests")
                .whereEqualTo("code", code)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        DocumentSnapshot document = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);

                        ArrayList<String> qi_questions = (ArrayList<String>) document.get("questions");
                        ArrayList<String> qi_choices = (ArrayList<String>) document.get("choices");
                        ArrayList<String> qi_correctAnswer = (ArrayList<String>) document.get("correctAnswer");

                        newQuiz[0] = new Quiz(code, title, qi_questions, qi_choices, qi_correctAnswer);
                        clone[0] = Quiz.newInstance(newQuiz[0]);

                        int size = clone[0].questions.size();
                        questions_len = newQuiz[0].questions.size();
                        title_quiz.setText(clone[0].title);
                        if (step == questions_len + 1) {
//                            question_size.setText(step + " из " + questions_len);
                            goToResult(document);
                        }
                        updateQuestion(step, document);

                        answer1.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                score = CheckAnswer(answer1);
                                step++;
                                if (step < size) {
                                    updateQuestion(step, document);
                                } else { goToResult(document); }
                            }
                        });

                        answer2.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                score = CheckAnswer(answer2);
                                step++;
                                if (step < size) {
                                    updateQuestion(step, document);
                                } else { goToResult(document); }
                            }
                        });

                        answer3.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                score = CheckAnswer(answer3);
                                step++;
                                if (step < size) {
                                    updateQuestion(step, document);
                                } else { goToResult(document); }
                            }
                        });

//                        answer4.setOnClickListener(new View.OnClickListener() {
//                            public void onClick(View v) {
//                                score = CheckAnswer(answer4);
//                                step++;
//                                if (step < size) {
//                                    updateQuestion(step, document);
//                                } else { goToResult(document); }
//                            }
//                        });

                    }
                });



    }


    public void updateQuestion(int num, DocumentSnapshot document) {
        ArrayList<String> qi_questions = (ArrayList<String>) document.get("questions");
        ArrayList<String> qi_choices = (ArrayList<String>) document.get("choices");
        ArrayList<String> qi_correctAnswer = (ArrayList<String>) document.get("correctAnswer");

        Quiz current_quiz = new Quiz(document.getString("code"), document.getString("title"), qi_questions, qi_choices, qi_correctAnswer);
        if (index ==  current_quiz.questions.size()) {
            goToResult(document);
        }
        Log.d("TAG", current_quiz.toString());

        question.setText(current_quiz.getQuestions(num));

        String answers = current_quiz.getChoiceN(num);
        String[] parsedAnswers = answers.split(";");
        answer1.setText(parsedAnswers[0]);
        answer2.setText(parsedAnswers[1]);
        answer3.setText(parsedAnswers[2]);
//        answer4.setText(parsedAnswers[3]);

        q_answer = current_quiz.getCorrectAnswers(num);

        Log.d("SCORE", " " + score);
        Log.d("STEP", " " + step);
//        Log.d("q_answer", " " + q_answer);
        int step1 = step + 1;
        question_size.setText(String.format("Вопрос: %d из %d", step1, current_quiz.questions.size()));


    }

    public int CheckAnswer(Button button){
        Log.d("button.getText()", " " + button.getText());
        Log.d("q_answer", " " + q_answer);

        if (button.getText().toString().trim().equals(q_answer.toString().trim())) {
            score++;
            Log.d("SCORE", " " + score);
        }
        return score;}

    public void goToResult(DocumentSnapshot document) {
        ArrayList<String> qi_questions = (ArrayList<String>) document.get("questions");
        ArrayList<String> qi_choices = (ArrayList<String>) document.get("choices");
        ArrayList<String> qi_correctAnswer = (ArrayList<String>) document.get("correctAnswer");
        Quiz current_quiz = new Quiz(document.getString("code"), document.getString("title"), qi_questions, qi_choices, qi_correctAnswer);

        Intent intent = new Intent(QuizPlayActivity.this, ResultTestActivity.class);
        intent.putExtra("titleINTENT", current_quiz.getTitle());
        intent.putExtra("codeINTENT", current_quiz.getCode());
        intent.putExtra("scoreINTENT", String.valueOf(score));
        intent.putExtra("lengthINTENT", String.valueOf(current_quiz.questions.size()));


        startActivity(intent);
    }
}
