package com.example.myastronomy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class NewsFragment extends Fragment {
    private static final String TAG = "FIREBASE TAG";
    NewsRecyclerViewAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> newsCover= new ArrayList<String>();
    ArrayList<String> text = new ArrayList<String>();

    //    File content;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container,false);
        RecyclerView recyclerView = view.findViewById(R.id.rvNews);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));

        db.collection("news").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        newsCover.add(document.getString("news_cover"));
                        titles.add(document.getString("title"));
                        text.add(document.getString("text"));
                        adapter = new NewsRecyclerViewAdapter(titles, newsCover);
                        adapter.OnRecyclerViewClickListener(new NewsRecyclerViewAdapter.OnRecyclerViewClickListener() {
                            @Override
                            public void OnItemClick(int position) {
                                String n_title = titles.get(position).toString();
                                String n_cover = newsCover.get(position).toString();
                                String n_text = text.get(position).toString();

                                Log.d(TAG, "onClick titles position " + n_title + "cover " + n_cover);

                                Intent intent = new Intent(getActivity(), NewsArticleActivity.class);
                                intent.putExtra("title", n_title);
                                intent.putExtra("news_cover", n_cover);
                                intent.putExtra("text", n_text);
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
