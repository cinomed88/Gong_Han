package ca.bcit.gong_han;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Represents the news articles received from the News API.
 *
 * @author Lucas Gong, David Han
 * @version 2020
 */
public class News implements Serializable {
    // TODO: Write a custom deserializer.
    @SerializedName("source")
    @Expose
    private Source source;
    public Source getSource() {
        return source;
    }
    public void setSource(Source source) {
        this.source = source;
    }

    public class Source implements Serializable {
        @SerializedName("id")
        @Expose
        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        @SerializedName("name")
        @Expose
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    @SerializedName("author")
    @Expose
    private String author;
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author= author;
    }

    @SerializedName("title")
    @Expose
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("description")
    @Expose
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("url")
    @Expose
    private String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;
    public String getUrlToImage() {
        return urlToImage;
    }
    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
//    public String getPublishedAt() {
//        return publishedAt;
//    }
public String getPublishedAt() {
    Pattern p = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}).*?(\\d{2}:\\d{2})");
    Matcher matcher = p.matcher(publishedAt);
    return matcher.find() ? matcher.group(1) + " " + matcher.group(2) : "";
}
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @SerializedName("content")
    @Expose
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
