package ca.bcit.gong_han;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class NewsListActivity extends AppCompatActivity {
    static String keyword;
    private String TAG = NewsListActivity.class.getSimpleName();
    private ListView lv;
    // URL to get contacts JSON
    private static String SERVICE_URL;
    private ArrayList<News> newsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        keyword = (String) getIntent().getExtras().get("keyword");
        SERVICE_URL = "http://newsapi.org/v2/everything?" +
                        "q=" + keyword + "&" +
                        "from=2020-10-01&" +
                        "sortBy=popularity&" +
                        "apiKey=83f8aa04a2214ff6a8d1f3ccaa46ff41";
        /**
         * Async task class to get json by making HTTP call
         */
        class GetContacts extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                HttpHandler sh = new HttpHandler();
                String jsonStr = null;

                // Making a request to url and getting response
                jsonStr = sh.makeServiceCall(SERVICE_URL);
                Log.e(TAG, "Response from url: " + jsonStr);

                if (jsonStr != null) {
                    Log.d(TAG, "Json: " + jsonStr);
                    // this step is needed to wrap the JSON array inside
                    Gson gson = new Gson();
                    BaseNews baseNews = gson.fromJson(jsonStr, BaseNews.class);
                    newsList = baseNews.getNews();
                } else {
                    Log.e(TAG, "Couldn't get json from server.");
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

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                NewsAdapter adapter = new NewsAdapter(NewsListActivity.this, newsList);

                // Attach the adapter to a ListView
                lv.setAdapter(adapter);
            }
        }

        newsList = new ArrayList<News>();
        lv = findViewById(R.id.newsList);
        new GetContacts().execute();
    }


}