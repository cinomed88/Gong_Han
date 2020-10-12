package ca.bcit.gong_han;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Represents a utility class to handle HTTP requests.
 *
 * @author Lucas Gong, David Han
 * @version 2020
 */
public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    /**
     * Performs a GET request at the specified URL.
     *
     * @param reqUrl    a String representing the URL to request the resource from
     * @return          a String representing the response
     */
    public String getResourceFromURL(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int statusCode = conn.getResponseCode();
            if (statusCode !=  HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /* Converts an InputStream to a String. */
    private String convertStreamToString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(is)) ) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
