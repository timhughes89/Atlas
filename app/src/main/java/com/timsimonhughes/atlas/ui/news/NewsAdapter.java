package com.timsimonhughes.atlas.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timsimonhughes.atlas.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final int count = 20;
    private Context context;
    private int[] imgList = {
            R.drawable.example_image,
            R.drawable.example_image_2,
            R.drawable.example_image_2,
            R.drawable.example_image,
            R.drawable.example_image,
            R.drawable.example_image_2,
            R.drawable.example_image_2,
            R.drawable.example_image};

    private String[] titles = {
            "This is a short title",
            "This is a longer title, hence why it's goes on and on like this",
            "This is a short title",
            "This is a short title",
            "This is a longer title, hence why it's goes on and on like this",
            "This is a longer title, hence why it's goes on and on like this",
            "This is a longer title, hence why it's goes on and on like this",
            "This is a short title"
    };

    public NewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.mRoundedImageView.setImageResource(imgList[position]);
        holder.mTextView.setText(titles[position]);
    }

    @Override
    public int getItemCount() {
        return imgList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mRoundedImageView;
        TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mRoundedImageView = itemView.findViewById(R.id.image_view_news);
            mTextView = itemView.findViewById(R.id.text_view_news_title);

        }
    }
}
