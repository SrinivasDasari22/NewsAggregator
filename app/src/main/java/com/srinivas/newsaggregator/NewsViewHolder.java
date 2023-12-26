package com.srinivas.newsaggregator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    TextView author;
    TextView date;
    TextView title;
    ImageView imageUrl;
    TextView desc;
    TextView counter;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        author = itemView.findViewById(R.id.news_author);
        date = itemView.findViewById(R.id.news_date);
        title = itemView.findViewById(R.id.news_headline);
        imageUrl = itemView.findViewById(R.id.news_image);
        desc = itemView.findViewById(R.id.news_desc);
        counter = itemView.findViewById(R.id.counter);

    }
}
