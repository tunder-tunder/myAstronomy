package com.example.myastronomy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ConstructorQuiz extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constructor);

        TextView code_new = findViewById(R.id.code_here);
        String code_generate = Quiz.generateUniqueCode();
        code_new.setText(code_generate);

        EditText et_title = findViewById(R.id.et_name_add);
        EditText et_questions_len = findViewById(R.id.et_name_add2);


//        startActivity(intent);
        Button create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_title.getText().toString().equals(null) || et_questions_len.getText().toString().equals(null) ){
                    Log.d("TAG", " null edittext fields");
                }

                String get_title = String.valueOf(et_title.getText());
                int get_length = Integer.parseInt(et_questions_len.getText().toString());
                Intent intent = new Intent(ConstructorQuiz.this, QuizFillForm.class);
                intent.putExtra("title", get_title);
                intent.putExtra("code", code_generate);
                intent.putExtra("len", get_length);

                startActivity(intent);
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
