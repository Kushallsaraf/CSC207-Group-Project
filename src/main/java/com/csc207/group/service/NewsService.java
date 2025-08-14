package com.csc207.group.service;

import java.util.ArrayList;
import java.util.List;

import com.csc207.group.data_access.NewsClient;
import com.csc207.group.model.News;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public final class NewsService {

    public static final int SIX = 6;
    public static final int TEN = 10;

    /**
     * Builds a news article using the api.
     * @return returns a list of news articles.
     */
    public List<News> articleBuilder() {
        NewsClient response = new NewsClient();
        JSONObject jsonObj = response.getGamingNews().getObject();
        JSONArray articles = jsonObj.getJSONArray("articles");
        List<News> newsList = new ArrayList<>();

        for (int i = 0; i < articles.length(); i++) {
            // Stop once we have 6 valid articles
            if (newsList.size() >= SIX) {
                break;
            }

            JSONObject article = articles.getJSONObject(i);
            String source = article.getJSONObject("source").getString("name");
            String title = article.getString("title");
            String author = article.optString("author", "Unknown Author");
            String content = article.optString("content", "No content available.");

            // Get the URL for the article page
            String url = article.optString("url", "");

            // Get the URL for the article's image
            String imageUrl = article.optString("urlToImage", "");

            String date = article.getString("publishedAt").substring(0, TEN);

            // Only add the article if it has both a webpage URL and an image URL
            if (!url.isEmpty() && !imageUrl.isEmpty()) {
                newsList.add(new News(source, author, title, content, date, url, imageUrl));
            }
        }
        return newsList;
    }
}
