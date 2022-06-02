package com.example.myastronomy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class ProfileCircleFragment extends Fragment {

    View view;
    FirebaseAuth mAuth;
    TextView title_prof;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_circle_profile, container, false);
        title_prof = view.findViewById(R.id.title_prof);
        ImageButton profBtn = view.findViewById(R.id.prof_c);
        final int radius = 100;
        final int margin =1 ;
        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            title_prof.setText("Здраствуйте, "+ name);
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
//            Log.d("PHOTO", photoUrl.toString());
            if (photoUrl != null) {Picasso.with(getContext())
                    .load(photoUrl)
                    .error(R.drawable.ic_profile_placeholder)
                    .fit()
                    .transform(transformation)
                    .into(profBtn);} else if (photoUrl == null){
                Picasso.with(getContext())
                        .load(R.drawable.ic_profile_placeholder)
                        .placeholder(R.drawable.ic_profile_placeholder)
                        .fit()
                        .transform(transformation)
                        .into(profBtn);
            }

        }

        profBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ProfileFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
            return view;
    }



    public void setTitleText(String title) {
        title_prof.setText(title);
    }



}