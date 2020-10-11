package ca.bcit.gong_han;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    Context _context;
    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        // Get the data item for this position
        News news = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_layout, parent, false);
        }

        // Lookup view for data population
        TextView tvPublishedAt = convertView.findViewById(R.id.publishedAt);
        TextView tvTitle = convertView.findViewById(R.id.title);
        TextView tvAuthor = convertView.findViewById(R.id.author);

        // Populate the data into the template view using the data object
        tvPublishedAt.setText(news.getPublishedAt());
        tvTitle.setText(news.getTitle());
        tvAuthor.setText(news.getAuthor());

        ImageView imgOnePhoto = convertView.findViewById(R.id.urlToImage);
        if (news.getUrlToImage() != null) {
            new ImageDownloaderTask(imgOnePhoto).execute(news.getUrlToImage());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}

