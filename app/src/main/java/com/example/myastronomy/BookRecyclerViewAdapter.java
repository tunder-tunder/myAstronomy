package com.example.myastronomy;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder>  {
    private LayoutInflater mInflater;
    // data is passed into the constructor
    int[] id;
    ArrayList<String> titles;
    ArrayList<String> authors;
    ArrayList<Bitmap> cover_img;
// INTERFACE FOR CLICKABLE ITEMS -----

    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        public void OnItemClick(int position);
    }
    // constructor for the interface
    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    // ----------------- end
    BookRecyclerViewAdapter(ArrayList<String> titles, ArrayList<String> authors, ArrayList<Bitmap> cover_img) {
        this.titles = titles;
        this.authors = authors;
        this.cover_img = cover_img;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false));
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookTitle.setText(titles.get(position));
        holder.bookAuthor.setText(authors.get(position));
        holder.imageCover.setImageBitmap(cover_img.get(position));

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return titles.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;
        TextView bookAuthor;
        ImageView imageCover;


        public ViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            imageCover = itemView.findViewById(R.id.imageView3);

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
