package ca.bcit.gong_han;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Represents a details page, showing information for a clicked news article.
 *
 * @author Lucas Gong, David Han
 * @version 2020
 */
public class NewsDetailActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState    contains the data most recently saved in onSaveInstanceState()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Intent intent = getIntent();
        News news = (News) intent.getSerializableExtra("clicked_news");
        assert news != null;

        ImageView thumbnail = findViewById(R.id.img_thumbnail);
        if (news.getUrlToImage() != null) {
            new ImageDownloaderTask(thumbnail).execute(news.getUrlToImage());
        }

        TextView name = findViewById(R.id.tv_name);
        name.setText(news.getSource().getName());

        TextView author = findViewById(R.id.tv_author);
        author.setText(news.getAuthor());

        TextView title = findViewById(R.id.tv_title);
        title.setText(news.getTitle());

        TextView description = findViewById(R.id.tv_description);
        description.setText(news.getDescription());

        // TODO: Make URL clickable (similar to hyperlink)
        TextView url = findViewById(R.id.tv_url);
        url.setText(news.getUrl());

        TextView publishedAt = findViewById(R.id.tv_published_at);
        publishedAt.setText(news.getPublishedAt());

        TextView content = findViewById(R.id.tv_content);
        content.setText(news.getContent());
    }
}