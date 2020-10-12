package ca.bcit.gong_han;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Provides the interface between the News object and the View that calls it.
 *
 * @author Lucas Gong, David Han
 * @version 2020
 */
public class NewsAdapter extends ArrayAdapter<News> {
    /* Debugging */
    private static final String TAG = NewsAdapter.class.getSimpleName();

    /**
     * Initializes this NewsAdapter.
     *
     * @param context   the context in which this NewsAdapter is used
     * @param news      an ArrayList of news articles
     */
    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     * You can either create a View manually or inflate it from an XML layout file.
     *
     * @param position      the position of the item whose view we want
     * @param convertView   the View to reuse, if possible
     * @param parent        the parent that this View will attach to
     * @return a View corresponding to the data at the specified position
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        assert(news != null);

        Log.d(TAG, news.getSource().getName());

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_news, parent, false);
        }

        // Lookup view for data population
        TextView tvPublishedAt = convertView.findViewById(R.id.tv_published_at);
        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        TextView tvName = convertView.findViewById(R.id.tv_name);

        // Populate the data into the template view using the data object
        tvPublishedAt.setText(news.getPublishedAt());
        tvTitle.setText(news.getTitle());
        tvName.setText(news.getSource().getName());

        ImageView imgOnePhoto = convertView.findViewById(R.id.img_thumbnail);
        if (news.getUrlToImage() != null) {
            new ImageDownloaderTask(imgOnePhoto).execute(news.getUrlToImage());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}

