package com.example.myastronomy;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewsArticleActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        TextView title_n = findViewById(R.id.title_news);
        ImageView cover_n = findViewById(R.id.cover_news);
        TextView text_n= findViewById(R.id.text_news1);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        Log.d("TITLE INTENT", title);
        title_n.setText(title);

        String text = intent.getStringExtra("text");
        //format text


        text_n.setText(Html.fromHtml(text));

        String news_cover = intent.getStringExtra("news_cover");
        Log.d("NEWS COVER INTENT", news_cover + " max height: " + cover_n.getMaxHeight());
        Picasso.with(this)
                .load(news_cover)
                .centerInside()
                .resize(600, 300)
                .into(cover_n);

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
