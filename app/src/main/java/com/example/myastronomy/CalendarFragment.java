package com.example.myastronomy;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "FIREBASE CALENDAR";

    String[] month_arr = {"january", "february", "march",
    "april", "may", "june",
    "july", "august", "september",
    "october", "november", "december"};

    String[] dayOfMonth_arr = {"января ", "февраля ", "марта ",
    "апреля ", "мая ", "июня ",
    "июля ", "августа ", "сентября ",
    "октября ", "ноября ", "декабря"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container,false);
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        ListView listView = view.findViewById(R.id.listView);
        TextView date_tv = view.findViewById(R.id.date_tv);

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd MM");
        Calendar cal = Calendar.getInstance();
        String date_now = dateFormat.format(cal.getTime());
        String[] split_date = date_now.split(" ");
        date_tv.setText(split_date[0] + " " + dayOfMonth_arr[Integer.parseInt(split_date[1]) - 1]);

        Log.d("TAG", split_date[0]);
        Log.d("TAG", dayOfMonth_arr[Integer.parseInt(split_date[1]) - 1]);
        Log.d("TAG", month_arr[Integer.parseInt(split_date[1]) - 1]);

        DocumentReference docRef1 = db.collection("events").document(month_arr[Integer.parseInt(split_date[1]) - 1]);
        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String key = "";
                        if(Integer.parseInt(split_date[0]) < 10) {
                            key = split_date[0].replace("0", "") + " "+ dayOfMonth_arr[Integer.parseInt(split_date[1]) - 1];
                        }
                        else {
                             key = split_date[0] + " "+ dayOfMonth_arr[Integer.parseInt(split_date[1]) - 1];
                        }
                        String key_1 = " " + key + " ";

                        String nothing = "ничего особенного не происходило";
                        Log.d("TAG", key + key_1);
                        String output = document.get(key_1).toString();


                        if (output.contains(nothing)){
                            String[] group = {(String) document.get(key_1)};
                            ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                                    R.layout.list_item, group);
                            listView.setAdapter(adapter);
                        } else if(output.equals(null)){
                            String[] group = {(String) nothing};
                            ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                                    R.layout.list_item, group);
                            listView.setAdapter(adapter);
                        }
                        else {
                            List<String> group = (List<String>) document.get(key_1);
                            ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                                    R.layout.list_item, group);
                            listView.setAdapter(adapter);
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            int mMonth = month;
            int mDay = dayOfMonth;

            String key = mDay + " "+ dayOfMonth_arr[mMonth];
            Log.d("KEY", key);

            date_tv.setText(key);

            DocumentReference docRef = db.collection("events").document(month_arr[mMonth]);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            String key_1 = " " + key + " ";

                            String nothing = "ничего особенного не происходило";
                            String output = document.get(key_1).toString();

                            if (output.contains(nothing)){
                                String[] group = {(String) document.get(key_1)};
                                ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                                        R.layout.list_item, group);
                                listView.setAdapter(adapter);
                            } else if(output.equals(null)){
                                String[] group = {(String) nothing};
                                ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                                        R.layout.list_item, group);
                                listView.setAdapter(adapter);
                            }
                            else {
                                List<String> group = (List<String>) document.get(key_1);
                                ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                                        R.layout.list_item, group);
                                listView.setAdapter(adapter);
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        });

        return view;
    }
}
