package com.csc207.group.service;

import com.csc207.group.data_access.NewsClient;
import com.csc207.group.model.News;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class NewsService {
    public List<News> ArticleBuilder() {
        NewsClient response = new NewsClient();
        JSONObject jsonObj = response.getGamingNews().getObject();
        JSONArray articles = jsonObj.getJSONArray("articles");
        List<News> newsList = new ArrayList<>();

        for (int i = 0; i < articles.length(); i++) {
            String source = articles.getJSONObject(i).getJSONObject("source").getString("name");
            String title = articles.getJSONObject(i).getString("title");
            String author = articles.getJSONObject(i).getString("author");
            String content = articles.getJSONObject(i).getString("content");
            String url = articles.getJSONObject(i).getString("url");
            String date = articles.getJSONObject(i).getString("publishedAt").substring(0, 10);
            newsList.add(new News(source, author, title, content, date, url));
        }

        return newsList;
    }
}
