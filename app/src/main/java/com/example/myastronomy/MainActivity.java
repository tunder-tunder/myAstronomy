package com.example.myastronomy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalendarFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_prof, new ProfileCircleFragment()).commit();


        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                ProfileCircleFragment prof = (ProfileCircleFragment) getSupportFragmentManager().findFragmentById(R.id.container_prof);
                switch (item.getItemId()) {
                    case R.id.nav_map:
                        prof.setTitleText("Календарь событий");
                        selectedFragment = new CalendarFragment();
                        break;
                    case R.id.nav_news:
                        prof.setTitleText("Новости");
                        selectedFragment = new NewsFragment();
                        break;
                    case R.id.nav_book:
                        prof.setTitleText("Книги");
                        selectedFragment = new BooksFragment();
                        break;
                    case R.id.nav_test:
                        prof.setTitleText("Проверка знаний");
                        selectedFragment = new TestsFragment();
                        break;
                    case R.id.nav_acc:
                        prof.setTitleText("Профиль");
                        selectedFragment = new ProfileFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
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