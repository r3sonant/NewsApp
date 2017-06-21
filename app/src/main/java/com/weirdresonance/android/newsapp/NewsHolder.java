package com.weirdresonance.android.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Steev on 20/06/2017.
 */

public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    private final ImageView thumbnailHolderView;
    private final TextView newsTitleView;
    private final TextView newsAuthorView;

    private NewsItem newsItem;
    private Context context;

    public NewsHolder(Context context, View itemView) {
        super(itemView);

        // 1. Set the context
        this.context = context;

        // 2. Inflate the UI widgets of the holder
        this.thumbnailHolderView = (ImageView) itemView.findViewById(R.id.thumbnailView);
        this.newsTitleView = (TextView) itemView.findViewById(R.id.newsTitle);
        this.newsAuthorView = (TextView) itemView.findViewById(R.id.newsAuthor);
/*        this.website = (TextView) itemView.findViewById(R.id.bakery_website);*/

        // 3. Set the "onClick" listener of the holder
        itemView.setOnClickListener(this);
    }

    public void bindNews(NewsItem newsItem) {

        // 4. Bind the data to the ViewHolder
        this.newsItem = newsItem;
        this.newsTitleView.setText(newsItem.getNewsTitle());
        this.newsAuthorView.setText(newsItem.getNewsAuthor());
/*        this.phone.setText(bakery.phoneNumber);
        this.website.setText(bakery.websiteUrl);
        this.description.setText(bakery.description);
        this.bakeryLogo.setImageBitmap(bakery.logo);*/
    }


    @Override
    public void onClick(View v) {

        // 5. Handle the onClick event for the ViewHolder
        if (this.newsItem != null) {


        }
    }
}
