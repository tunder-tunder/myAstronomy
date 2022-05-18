package com.example.myastronomy;

import android.content.Context;
import android.graphics.Bitmap;
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

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {
    private NewsRecyclerViewAdapter.OnRecyclerViewClickListener listener;
    ArrayList<String> titles = new ArrayList<String>();
    Context context;
    ArrayList<String> news_cover= new ArrayList<String>();
    File content;

    public interface OnRecyclerViewClickListener{
        public void OnItemClick(int position);
    }
    // constructor for the interface
    public void OnRecyclerViewClickListener(NewsRecyclerViewAdapter.OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    // ----------------- end
    NewsRecyclerViewAdapter(ArrayList<String> titles, ArrayList<String> news_cover) {
        this.titles = titles;
        this.news_cover = news_cover;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsRecyclerViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false));
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        Picasso.with(context)
                .load(news_cover.get(position))
                .fit()
                .into(holder.newsCover);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return titles.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView newsCover;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);;
            newsCover = itemView.findViewById(R.id.news_cover);

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
