package com.example.myastronomy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestsFragment extends Fragment {
    private static final String TAG = "FIREBASE TAG";
    QuizRecyclerViewAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<String> codes = new ArrayList<String>();
    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> sub_titles = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests, container,false);

        Button addTest = view.findViewById(R.id.addTest);
        addTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten_cons = new Intent(getActivity(), ConstructorQuiz.class);
                startActivity(inten_cons);
            }
        });

        Log.d("TEST CODE", "generated code: " + Quiz.generateUniqueCode());
        Intent intent = new Intent(getActivity(), QuizPlayActivity.class);

        RecyclerView recyclerView = view.findViewById(R.id.rvTests);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        db.collection("tests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        codes.add(document.getString("code"));

                        Log.d(TAG, document.getString("code"));
                        Log.d(TAG, document.getString("sub_title"));
                        titles.add(document.getString("title"));
                        sub_titles.add(document.getString("sub_title"));


                        adapter = new QuizRecyclerViewAdapter(titles, sub_titles, codes);

                        adapter.OnRecyclerViewClickListener(new QuizRecyclerViewAdapter.OnRecyclerViewClickListener() {
                            @Override
                            public void OnItemClick(int position) {
                                String qi_title = titles.get(position).toString();
                                String qi_subtitles = sub_titles.get(position).toString();
                                String qi_code = codes.get(position).toString();
                                intent.putExtra("title", qi_title);
                                intent.putExtra("code", qi_code);
                                ((MainActivity) getActivity()).startActivity(intent);
                            }
                        });

                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });


        return view;
    }




}
