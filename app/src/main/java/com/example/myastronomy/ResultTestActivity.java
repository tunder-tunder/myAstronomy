package com.example.myastronomy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class  ResultTestActivity extends AppCompatActivity implements View.OnClickListener {
    String title, code;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView result_title = findViewById(R.id.result_title);
        TextView result_score = findViewById(R.id.result_score);
        ImageView img_result = findViewById(R.id.img_result);
        Button result_go_back= findViewById(R.id.result_go_back);
        result_go_back.setOnClickListener(this);
        Button result_go_again= findViewById(R.id.result_go_again);
        result_go_again.setOnClickListener(this);

        Intent intent = getIntent();

        title = intent.getStringExtra("titleINTENT");
        Log.d("TITLE INTENT", title);
        result_title.setText(title);

        code = intent.getStringExtra("codeINTENT");


        String score = intent.getStringExtra("scoreINTENT");
        String length = intent.getStringExtra("lengthINTENT");
        result_score.setText(String.format("Отвечено правильно на вопросы: %s из %s", score, length));


        img_result.setImageResource(R.drawable.ic_undraw_to_the_moon_re_q21i);

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

    @Override
    public void onClick(View view) {
        Intent intent_back = new Intent(ResultTestActivity.this, MainActivity.class);

        switch(view.getId()) {
            case R.id.result_go_back:
                startActivity(intent_back);
                break;
            case R.id.result_go_again:
                passIntent(code, title);
                break;
        }
    }

    public void passIntent(String code, String title) {
        Intent intent_again = new Intent(ResultTestActivity.this, QuizPlayActivity.class);
        intent_again.putExtra("title", title);
        intent_again.putExtra("code", code);
        startActivity(intent_again);
    }
}
