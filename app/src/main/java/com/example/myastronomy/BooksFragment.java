package com.example.myastronomy;

import android.content.Context;
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

import com.folioreader.FolioReader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BooksFragment extends Fragment {
    private static final String TAG = "FIREBASE TAG";
    BookRecyclerViewAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> authors= new ArrayList<String>();
    ArrayList<Bitmap> coverImg= new ArrayList<Bitmap>();
    File content;


    public void getEpubFile(String title) {
        FolioReader folioReader = FolioReader.get();

        db.collectionGroup("books")
                .whereEqualTo("title", title)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {

                        DocumentSnapshot document = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                        Log.d(TAG, document.getString("conent"));
                        String contentURL = document.getString("conent");
                        StorageReference contentReference = storage.getReferenceFromUrl(contentURL);
                        Context context = getContext();
                        File path = context.getFilesDir();
                        File file = new File(path, title + ".epub");
                        contentReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                //from bytes to a  epub file
                                FileOutputStream stream = null;
                                try {
                                    stream = new FileOutputStream(file);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    stream.write(bytes);
                                }
                                catch (Exception e){
                                    System.out.println("Error " + e.getMessage());
                                } finally {
                                    try {
                                        stream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                folioReader.openBook(file.getPath());
                            }
                        });
//        return content;
                    }
                });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // data to populate the RecyclerView with
        View view = inflater.inflate(R.layout.fragment_books, container,false);
        RecyclerView recyclerView = view.findViewById(R.id.rvBooks);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));


        db.collection("books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {



                        String coverImgURL = document.getString("cover_img");
                        StorageReference gsReference = storage.getReferenceFromUrl(coverImgURL);



                        gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                titles.add(document.getString("title"));
                                authors.add(document.getString("author"));
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                coverImg.add(bitmap);
                                Log.d(TAG, "coverimgurl size " + coverImg.size());
//                                Log.d(TAG, "testing epub file" + content);
                                adapter = new BookRecyclerViewAdapter(titles, authors, coverImg);
                                adapter.OnRecyclerViewClickListener(new BookRecyclerViewAdapter.OnRecyclerViewClickListener() {
                                    @Override
                                    public void OnItemClick(int position) {
                                        String book = titles.get(position).toString();
                                        Log.d(TAG, "onClick titles position" + book);
                                        getEpubFile(book);
                                    }
                                });
                                recyclerView.setAdapter(adapter);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });



        return view;
    }

}
