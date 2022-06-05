package com.example.myastronomy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizFillForm extends AppCompatActivity {
    public final String collection = "tests";
    public String title;
    public String code;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ArrayList<String> questions = new ArrayList<String>();
    public ArrayList<String> choices= new ArrayList<String>();
    public ArrayList<String> correctAnswer= new ArrayList<String>();

    HashMap<String, EditText> editTexts = new HashMap<String, EditText>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fill_form);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String code = intent.getStringExtra("code");
        int length = intent.getIntExtra("len", 1);

        LinearLayout linLayout = new LinearLayout(this);
        // specifying vertical orientation
        linLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // set LinearLayout as a root element of the screen
        setContentView(linLayout, linLayoutParam);

        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpView.setMargins(28, 15, 28, 15);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.sourcesanspro_regular);

        TextView tv = new TextView(this);
        tv.setText("Заполните следующие поля: ");
        tv.setTypeface(typeface);
        tv.setId(0);
        tv.setLayoutParams(lpView);

        Button create = new Button(this);

        linLayout.addView(tv);
        for (int i = 0; i < length; i++) {

            TextView tvquestion = new TextView(this);
            tvquestion.setText("Вопрос: ");
            tvquestion.setTypeface(typeface);
            tvquestion.setLayoutParams(lpView);


            EditText question = new EditText(this);
            editTexts.put("question", question);
            question.setLayoutParams(lpView);

            TextView tva1 = new TextView(this);
            tva1.setText("1-ый вариант ответа: ");
            tva1.setTypeface(typeface);
            tva1.setLayoutParams(lpView);
            EditText answ1 = new EditText(this);
            editTexts.put("answ1", answ1);
            answ1.setLayoutParams(lpView);

            TextView tva2 = new TextView(this);
            tva2.setText("2-ый вариант ответа: ");
            tva2.setTypeface(typeface);
            tva2.setLayoutParams(lpView);
            EditText answ2 = new EditText(this);
            editTexts.put("answ2", answ2);
            answ2.setLayoutParams(lpView);

            TextView tva3 = new TextView(this);
            tva3.setText("3-ый вариант ответа: ");
            tva3.setTypeface(typeface);
            tva3.setLayoutParams(lpView);
            EditText answ3 = new EditText(this);
            editTexts.put("answ3", answ3);
            answ3.setLayoutParams(lpView);

            TextView tvaс = new TextView(this);
            tvaс.setText("Правильный ответ: ");
            tvaс.setTypeface(typeface);
            tvaс.setLayoutParams(lpView);
            EditText correct_answ = new EditText(this);
            editTexts.put("correct", correct_answ);
            correct_answ.setLayoutParams(lpView);

            linLayout.addView(tvquestion);
            linLayout.addView(question);
            linLayout.addView(tva1);
            linLayout.addView(answ1);
            linLayout.addView(tva2);
            linLayout.addView(answ2);
            linLayout.addView(tva3);
            linLayout.addView(answ3);
            linLayout.addView(tvaс);
//            linLayout.addView(answ4);
            linLayout.addView(correct_answ);

        }

        linLayout.addView(create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ch = "";
                    for (String j : editTexts.keySet()) {
                        System.out.println("key: " + j + " value: " + editTexts.get(j).getText().toString());
                        String finalchoice="";

                            if (j.equals("question")) {
                                String questionstr =editTexts.get(j).getText().toString();
                                questions.add(questionstr);
//                                Log.d("TAG", questionstr);
                            } else if (j.contains("answ")) {
                                String choicestr = editTexts.get(j).getText().toString() + "; ";
//                                Log.d("TAG", choicestr);
                                 ch = ch + choicestr;
//                                Log.d("TAG", choicestr);
                            } else if (j.equals("correct")) {
                                String correct_str = editTexts.get(j).getText().toString();
                                correctAnswer.add(correct_str);
//                                Log.d("TAG", correct_str);

                            }

                }
                choices.add(ch);
                Log.d("TAF", "questions " + questions.toString() + " choices " + choices.toString() + "correctAnswer " + correctAnswer.toString());

                Map<String, Object> data = new HashMap<>();
                data.put("choices", choices);
                data.put("code", code);
                data.put("correctAnswer", correctAnswer);
                data.put("title", title);
                data.put("sub_title", "Тест с тремя вариантами ответа");
                data.put("questions", questions);

                db.collection("tests")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                                Intent intent = new Intent(QuizFillForm.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });
            }
        });



    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}