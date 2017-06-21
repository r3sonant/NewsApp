package com.weirdresonance.android.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Steev on 19/06/2017.
 */


public class NewsItemAdapter extends RecyclerView.Adapter<NewsHolder> {


    private final List<NewsItem> newsItems;
    private Context context;
    private int itemResource;


/*    public NewsItemAdapter(Context context, List<NewsItem> newsItem) {
        super(context, 0, newsItem);
    }*/



    public NewsItemAdapter(Context context, int itemResource, List<NewsItem> newsItems) {

        // 1. Initialize our adapter
        this.newsItems = newsItems;
        this.context = context;
        this.itemResource = itemResource;
    }

    // 2. Override the onCreateViewHolder method
    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 3. Inflate the view and return the new ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.itemResource, parent, false);
        return new NewsHolder(this.context, view);
    }

/*    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {

    }*/

    // 4. Override the onBindViewHolder method
    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {

        // 5. Use position to access the correct Bakery object
        NewsItem newsItem = this.newsItems.get(position);

        // 6. Bind the bakery object to the holder
        holder.bindNews(newsItem);
    }

    @Override
    public int getItemCount() {

        return this.newsItems.size();
    }





   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NewsHolder newsHolder;

        // If the view convertView doesn't already exist then inflate it
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.newslisting_list_item, parent, false);

            // Set up the NewsHolder for the news views
            newsHolder = new NewsHolder();
            newsHolder.thumbnailHolderView = (ImageView) convertView.findViewById(R.id.thumbnailView);
            newsHolder.newsTitleView = (TextView) convertView.findViewById(R.id.newsTitle);
            newsHolder.newsAuthorView = (TextView) convertView.findViewById(R.id.newsAuthor);

            // Store the NewsHolder with the view
            convertView.setTag(newsHolder);

        } else {
            newsHolder = (NewsHolder) convertView.getTag();
        }

        // Find the news item at the position in the list that ws passed into getView
        NewsItem currentNewsItem = getItem(position);

        // Get the string URL for the location of the thumbnail
        Bitmap thumbnailImage = currentNewsItem.getThumbnail();

        // Assign the thumbnail to the ImageView.
        newsHolder.thumbnailHolderView.setImageBitmap(thumbnailImage);

        // Get the news title
        String newsTitle = currentNewsItem.getNewsTitle();

        // Set the news name in the newsTitle TextView
        newsHolder.newsTitleView.setText(newsTitle);

        // Get the news author
        String newsAuthor = currentNewsItem.getNewsAuthor();

        // Set the book author on the newsAuthor TextView
        newsHolder.newsAuthorView.setText(newsAuthor);

        // Return the list item view that has now been populated with the news information
        return convertView;
    }*/

/*    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView thumbnailHolderView;
        private TextView newsTitleView;
        private TextView newsAuthorView;

    }*/

}
