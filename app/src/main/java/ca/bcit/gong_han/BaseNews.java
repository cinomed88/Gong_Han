package ca.bcit.gong_han;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BaseNews {
    @SerializedName("status")
    @Expose
    private String status = new String();

    @SerializedName("totalResults")
    @Expose
    private int totalResults = -1;

    @SerializedName("articles")
    @Expose
    private ArrayList<News> articles = new ArrayList<>();

    public ArrayList<News> getNews() {
        return articles;
    }

    public void setNews(ArrayList<News> news) {
        this.articles = news;
    }


}
