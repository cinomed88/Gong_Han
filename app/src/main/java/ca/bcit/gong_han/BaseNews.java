package ca.bcit.gong_han;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Represents the base JSON object received from the News API.
 *
 * @author Lucas Gong, David Han
 * @version 2020
 */
public class BaseNews {
    @SerializedName("status")
    @Expose
    private String status = "";
    public String getStatus() { return status; }

    @SerializedName("totalResults")
    @Expose
    private int totalResults = -1;
    public int getTotalResults() { return totalResults; }

    @SerializedName("articles")
    @Expose
    private ArrayList<News> articles = new ArrayList<>();
    public ArrayList<News> getArticles() { return articles; }
    public void setArticles(ArrayList<News> news) { this.articles = news; }
}
