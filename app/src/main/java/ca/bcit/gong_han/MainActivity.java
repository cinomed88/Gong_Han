package ca.bcit.gong_han;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the Activity that is launched when the application is started.
 *
 * This application allows the user to search for news by keyword,
 * up to a limit of 20 articles per search.
 *
 * @author Lucas Gong, David Han
 * @version 2020
 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private List<String> items = Arrays.asList("Donald Trump", "asdfdgh", "asdzxcu", "sdfzxcis", "asdawvwx1");
    private SearchView searchView;
    private TextView resultTextView;

    /**
     * Called when the activity is starting.
     *
     * @param   savedInstanceState  contains the data most recently saved in onSaveInstanceState()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);
        resultTextView = findViewById(R.id.keyword_list);
        // TODO: Implement AutoCompleteTextView instead.
        resultTextView.setText(filterKeywords(""));

        // Add listener to search view
        searchView.setOnQueryTextListener(this);
    }

    /**
     * Called when the user submits the query.
     *
     * @param query a String representing the query text that is to be submitted
     * @return      true if the query has been handled by the listener,
     *              false to let the SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
        intent.putExtra("keyword", query);
        startActivity(intent);
        return true;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText   a String representing the new content of the query text field.
     * @return          true if the query has been handled by the listener,
     *                  false to let the SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        resultTextView.setText(filterKeywords(newText));
        return true;
    }

    /* Filter keywords */
    private String filterKeywords(String query){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++){
            String item = items.get(i);
            if (item.toLowerCase().contains(query.toLowerCase())) {
                sb.append(item);
                if (i != items.size() - 1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }
}