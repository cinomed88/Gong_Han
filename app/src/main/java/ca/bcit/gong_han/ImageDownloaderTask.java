package ca.bcit.gong_han;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Represents a worker class designed to download an image from a specified URL.
 *
 * @author Lucas Gong, David Han
 * @version 2020
 */
class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = ImageDownloaderTask.class.getSimpleName();
    private final WeakReference<ImageView> imageViewReference;

    /**
     * Constructs this ImageDownloaderTask.
     *
     * @param imageView the imageView to display the downloaded bitmap
     */
    public ImageDownloaderTask(ImageView imageView) {
        imageViewReference = new WeakReference<>(imageView);
    }

    /**
     * Downloads a Bitmap from the specified URL.
     *
     * @param params    String representing the URL of images to download
     * @return          a Bitmap if download was successful else null
     */
    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadBitmap(params[0]);
    }

    /**
     * Sets the ImageView with the downloaded Bitmap if download was successful.
     * Otherwise, set a placeholder image.
     *
     * @param bitmap    the downloaded Bitmap
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Drawable placeholder = ResourcesCompat.getDrawable(imageView.getResources(), R.drawable.placeholder, null);
                    imageView.setImageDrawable(placeholder);
                }
            }
        }
    }

    /* Download and return a bitmap from specified URL. */
    private Bitmap downloadBitmap(String reqUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            int statusCode = conn.getResponseCode();
            if (statusCode !=  HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream inputStream = conn.getInputStream();
            if (inputStream != null) {
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error downloading image from " + reqUrl);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
}

