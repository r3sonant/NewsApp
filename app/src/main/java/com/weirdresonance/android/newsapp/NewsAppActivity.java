package com.weirdresonance.android.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.app.LoaderManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NewsAppActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>>{


    private static final String LOG_TAG = NewsAppActivity.class.getName();

    /**
     * Test URL for Google Books API data
     */
    private static final String NEWS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    /**
     * Search entry from search box
     */
    private String searchString = null;

    /**
     * Constant for the book loader ID.
     */
    private static final int NEWS_LOADER_ID = 1;

    /**
     * Adapter for the list of books.
     */
    private NewsItemAdapter newsAdapter;

    /**
     * TextView that is displayed when the list doesn't contain any books.
     */
    private TextView emptyNewsListView;

    /**
     * ListView that will contain the list of books.
     */
    private RecyclerView newsListView;

    /**
     * EditText bar used for searching for books.
     */
    private EditText searchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_app);



        // Get the empty TextView.
        emptyNewsListView = (TextView) findViewById(R.id.empty_view);

        // Find a reference to the ListView list in the layout.
        newsListView = (RecyclerView) findViewById(R.id.list);
        //newsListView.setEmptyView(emptyNewsListView);

        // Set the text to be the noSearchYet text.
        emptyNewsListView.setText(R.string.noSearchYet);

        searchBar = (EditText) findViewById(R.id.searchBar);

        // Get the searchButton ID.
        final Button searchButton = (Button) findViewById(R.id.searchButton);

        // Set an onClickListener on the searchButton.
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO Remove the Nikki else if statement before submitting...

                searchClick();


            }
        });


    }




    private void searchClick() {

        // Get the text from the searchBar and set searchString with it.
        searchString = searchBar.getText().toString();

        // Check to see if the length of the searchString is 0, if it is show a Toast
        // message asking the user to enter a search term. If there is a search term
        // then call callLoader method to initiate the loader and get the books related
        // to the search term
        if (searchString.length() != 0) {
            callLoader(searchString);
            emptyNewsListView.setVisibility(View.GONE);
            hideSoftKeyboard(this, searchBar);
        }

        // Show a TOAST to the user informing them they need to enter a search term.
        else {
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.pleaseEnterSearchTerm);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * Calls the loader after a search term has been entered in the search bar.
     *
     * @param searchString
     */
    private void callLoader(String searchString) {

        // Restart the loader to clear it if a search has already been carried out
        getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);

        // Call FormatURL() to replace spaces with + and append the base URL to the search string
        FormatURL(searchString);

        // Create a new adapter that takes an empty list of books as input
        newsAdapter = new NewsItemAdapter(this, R.layout.newslisting_list_item, new ArrayList<NewsItem>());



// 5. Setup our RecyclerView
        newsListView = (RecyclerView)findViewById(R.id.list);

// 6. For performance, tell OS RecyclerView won't change size
        //newsListView.setHasFixedSize(true);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        //newsListView.setAdapter(newsAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
/*        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                NewsItem currentBook = newsAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentBook.getSelectedNewsUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });*/

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network.
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // Get the loadingindicator view and assing it to loadingIndicator.
        View loadingIndicator = findViewById(R.id.loading_indicator);

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

            // Make the loadingIndicator view visible.
            loadingIndicator.setVisibility(View.VISIBLE);


        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible

            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyNewsListView.setText("No internet connection");
        }
    }

    private String FormatURL(String searchString) {
        searchString = searchString.replace(" ", "+");
        searchString = NEWS_REQUEST_URL + searchString + getString(R.string.maxResults);
        return searchString;
    }


    //TODO need to pass in my custon string formed of the base URL and search text entered by the user.
    @Override
    public Loader<List<NewsItem>> onCreateLoader(int i, Bundle bundle) {
        // Call FormatURL() and pass in the searchString entered in the searchbox.
        // This will add the base url and max results and pass it to the loader.
        String searchUrl = FormatURL(searchString);
        return new NewsLoader(this, searchUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> books) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        emptyNewsListView.setText(R.string.noBooksFound);

        // Clear the adapter of previous earthquake data
        //newsAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
/*        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            newsAdapter.addAll(books);
        }*/
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        // Loader reset, so we can clear out our existing data.
        //newsAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
