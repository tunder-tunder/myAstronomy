package com.example.myastronomy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class QuizFillForm extends AppCompatActivity {
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


        for (int i = 0; i < length; i++) {

            EditText question = new EditText(this);
            question.setLayoutParams(lpView);
            EditText answ1 = new EditText(this);
            answ1.setLayoutParams(lpView);
            EditText answ2 = new EditText(this);
            answ2.setLayoutParams(lpView);
            EditText answ3 = new EditText(this);
            answ3.setLayoutParams(lpView);
            EditText answ4 = new EditText(this);
            answ4.setLayoutParams(lpView);
            EditText correct_answ = new EditText(this);
            correct_answ.setLayoutParams(lpView);

            linLayout.addView(question);
            linLayout.addView(answ1);
            linLayout.addView(answ2);
            linLayout.addView(answ3);
            linLayout.addView(answ4);
            linLayout.addView(correct_answ);
        }



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