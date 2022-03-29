package com.example.myastronomy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new GreetingsFragment()).commit();

        EditText username = findViewById(R.id.username);
        EditText passw = findViewById(R.id.password);

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = username.getText().toString().trim();
                String password = passw.getText().toString().trim();
                if (email == null || password == null){
                    Toast.makeText(LoginActivity.this, "Empty field(s)",
                            Toast.LENGTH_SHORT).show();
                }
                signIn(email, password);
            }
        });

        Button register_btn = findViewById(R.id.register_btn);
        Intent intent_register = new Intent(this, RegisterActivity.class);
        register_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent_register);
            }
        });

    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        mAuth = FirebaseAuth.getInstance();
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


    public void signIn(String email, String password) {
        // [START sign_in_with_email]
        Intent intent = new Intent(this, MainActivity.class);
        Log.d(TAG, "signInWithEmail" + email);
        Log.d(TAG, "signInWithEmail" + password);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userDisplayName = user.getDisplayName();
                            Toast.makeText(LoginActivity.this, "Hello, " + userDisplayName,
                                    Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed. " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

//TODO: forgot password feature
//FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//String newPassword = "SOME-SECURE-PASSWORD";
//
//user.updatePassword(newPassword)
//            .addOnCompleteListener(new OnCompleteListener<Void>() {
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//            if (task.isSuccessful()) {
//                Log.d(TAG, "User password updated.");
//            }
//        }
//    });
}