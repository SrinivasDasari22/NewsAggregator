package com.srinivas.newsaggregator;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NewsAdapter extends
        RecyclerView.Adapter<NewsViewHolder> {


    private final MainActivity mainActivity;
    private final ArrayList<News> newsArrayList;

    private SimpleDateFormat format,format1;

    public NewsAdapter(MainActivity mainActivity, ArrayList<News> newsArrayList) {
        this.mainActivity = mainActivity;
        this.newsArrayList = newsArrayList;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_entry,parent,false);
//        itemView.setOnClickListener(mainActivity);


        return new NewsViewHolder(itemView);

    }

    public String dateFormat(String date){

        if(date!=null) {

            String date1 = null;

            String dateString = (date.replace("T", " ").split(":")[0]) + ":" + (date.replace("T", " ").split(":")[1].split(":")[0]);

            format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            System.out.println(dateString);
            format1 = new SimpleDateFormat("LLL dd, yyyy HH:mm");
            try {
                date1 = format1.format(format.parse(dateString));


            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("prinitng date :" + date1);

            return date1;
        }
        else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = newsArrayList.get(position);

        if(news.getAuthor().equals("null") || news.getAuthor() ==null){
            holder.author.setText("");
        } else{
            holder.author.setText(news.getAuthor());
        }

        if(news.getDate().equals("null") ||news.getDate()==null){
            holder.date.setText("");
        }else{

            holder.date.setText(dateFormat(news.getDate()));
        }

        if(news.getTitle().equals("null") ||news.getTitle()==null){
            holder.title.setText("");
        }else{
            holder.title.setText(news.getTitle());
        }

        if(news.getDescription().equals("null") ||news.getDescription()==null){
            holder.desc.setText("");
        }else{
            holder.desc.setText(news.getDescription());
        }

        holder.counter.setText((position+1) +" of "+ (newsArrayList.size()));
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                } catch (Exception e) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                }
                v.getContext().startActivity(intent);
            }
        });

        holder.desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                } catch (Exception e) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                }
                v.getContext().startActivity(intent);
            }
        });

        holder.imageUrl.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                                try {
                                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                                                } catch (Exception e) {
                                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                                                }
                                                v.getContext().startActivity(intent);
                                            }
                                        });


        downloadImage(position,holder);
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    private void downloadImage(int pos, NewsViewHolder holder) {

        if (!newsArrayList.get(pos).getImageUrl().equals("")) {
            Picasso.get().load(newsArrayList.get(pos).getImageUrl())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.brokenimage)
                    .into(holder.imageUrl);
        } else {
            holder.imageUrl.setImageResource(R.drawable.noimage);
        }
    }

}
