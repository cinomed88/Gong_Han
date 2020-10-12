package ca.bcit.gong_han;

import android.app.ListActivity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a scrollable list of news articles.
 *
 * @author Lucas Gong, David Han
 * @version 2020
 */
public class NewsListActivity extends ListActivity {
    private static String TAG = NewsListActivity.class.getSimpleName();
    // TODO: Hide the API endpoint and key from the code. Use Gradle build settings.
    private static String SERVICE_URL = "http://newsapi.org/v2/everything?";
    private static String API_KEY = "83f8aa04a2214ff6a8d1f3ccaa46ff41";

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState    contains the data most recently saved in onSaveInstanceState()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String reqUrl = buildNewsAPIQueryString();
        Log.d(TAG, reqUrl);
        new GetArticlesTask().execute(reqUrl);
    }

    /* Performs URL encoding on given String. */
    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    /* Build the query string to request news articles from the News API. */
    private String buildNewsAPIQueryString() {
        String keyword = (String) Objects.requireNonNull(getIntent().getExtras()).get("keyword");
        String encodedQuery = encodeValue(keyword);

        // Calculate the date that is 1 week before now.
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -7);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        return SERVICE_URL + "q=" + encodedQuery + "&from=" + formatter.format(cal.getTime()) + "&sortBy=publishedAt" + "&apiKey=" + API_KEY;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has been clicked.
     *
     * @param listView  the ListView where the click happened
     * @param view      the View within the ListView that was clicked (view provided by adapter)
     * @param position  the position of the view in adapter
     * @param id        the row id of the item that was clicked
     */
    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Intent intent = new Intent(this, NewsDetailActivity.class);
        News clickedNews = (News) getListView().getItemAtPosition(position);
        intent.putExtra("clicked_news", clickedNews);
        startActivity(intent);
    }

    /* Async task class to get json by making HTTP call */
    class GetArticlesTask extends AsyncTask<String, Void, ArrayList<News>> {
        /**
         * Requests news articles from the News API and parses the returned JSON object.
         *
         * @param params    the request URL with specified query parameters
         * @return          an ArrayList of News if successful, else null
         */
        @Override
        protected ArrayList<News> doInBackground(String... params) {
            HttpHandler httpHandler = new HttpHandler();
            String jsonStr;

            // Making a request to url and getting response
            jsonStr = httpHandler.getResourceFromURL(params[0]);
            Log.d(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                Log.d(TAG, "Json: " + jsonStr);

                // Parse the JSON data into a BaseNews object
                Gson gson = new Gson();
                BaseNews baseNews = gson.fromJson(jsonStr, BaseNews.class);
                return baseNews.getArticles();
            } else {
                Log.d(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        /**
         * Sets a NewsAdapter to this ListActivity.
         *
         * @param newsList  an ArrayList of News retrieved from the News API
         */
        @Override
        protected void onPostExecute(ArrayList<News> newsList) {
            NewsAdapter adapter = new NewsAdapter(NewsListActivity.this, newsList);
            getListView().setAdapter(adapter);
        }
    }
}