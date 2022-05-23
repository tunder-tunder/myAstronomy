package com.example.myastronomy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class QuizRecyclerViewAdapter extends RecyclerView.Adapter<QuizRecyclerViewAdapter.ViewHolder> {
    private QuizRecyclerViewAdapter.OnRecyclerViewClickListener listener;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> sub_titles = new ArrayList<String>();
    ArrayList<String> codes = new ArrayList<String>();
    Context context;


    public interface OnRecyclerViewClickListener{
        public void OnItemClick(int position);
    }
    // constructor for the interface
    public void OnRecyclerViewClickListener(QuizRecyclerViewAdapter.OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    // ----------------- end
    QuizRecyclerViewAdapter(ArrayList<String> titles, ArrayList<String> sub_titles, ArrayList<String> codes) {
        this.titles = titles;
        this.sub_titles = sub_titles;
        this.codes = codes;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public QuizRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuizRecyclerViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false));
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull QuizRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.sub_title.setText(sub_titles.get(position));
        holder.code.setText(codes.get(position));
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return titles.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView sub_title;
        TextView code;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.quiz_title);
            sub_title = itemView.findViewById(R.id.sub_title);
            code = itemView.findViewById(R.id.code);

            // USING INTERFACE -----
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null && getLayoutPosition()!=RecyclerView.NO_POSITION){
                        listener.OnItemClick(getLayoutPosition());
                        Log.d("my", "position" + getLayoutPosition());
                    }
                }
            });
        }
    }
}
