package ca.bcit.gong_han;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //example keywords to test search
    private List<String> items = Arrays.asList("asd", "asdfdgh", "asdzxcu", "sdfzxcis", "asdawvwx1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.search_view);
        final TextView resultTextView = findViewById(R.id.keyword_list);
        resultTextView.setText(getResult());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //some action after enter of search bar
                Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
                intent.putExtra("keyword", query);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                resultTextView.setText(search(newText));
                return true;
            }
        });

    }

    private String search(String query){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < items.size(); i++){
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

    private String getResult(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < items.size(); i++){
            String item = items.get(i);
            sb.append(item);
            if (i != items.size() -1){
                sb.append("\n");
            }

        }
        return sb.toString();
    }
}