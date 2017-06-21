package com.weirdresonance.android.newsapp;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;


/**
 * Created by Steev on 19/06/2017.
 */

public class NewsItem extends RecyclerView.ViewHolder {

    /**
     * URL of the location for the image of the book
     */
    private Bitmap thumbnail;

    /**
     * Title of the book
     */
    private String newsTitle;

    /**
     * Author of the book
     */
    private String newsAuthor;

    /**
     * Url of the full page on the book
     */
    private String selectedNewsUrl;

    public NewsItem(Bitmap thumbnail, String newsTitle, String newsAuthors, String individualNewsUrl) {

        this.thumbnail = thumbnail;
        this.newsTitle = newsTitle;
        this.newsAuthor = newsAuthors;
        this.selectedNewsUrl = individualNewsUrl;
    }

    /**
     * Returns the location of the thumbnail image for the book
     *
     * @return
     */
    public Bitmap getThumbnail() {
        return thumbnail;
    }

    /**
     * Returns the author of the book
     *
     * @return
     */
    public String getNewsAuthor() {
        return newsAuthor;
    }


    /**
     * Returns the title of the book
     *
     * @return
     */
    public String getNewsTitle() {
        return newsTitle;
    }

    /**
     * Returns the URL for the selected book
     *
     * @return
     */
    public String getSelectedNewsUrl() {
        return selectedNewsUrl;
    }

}
