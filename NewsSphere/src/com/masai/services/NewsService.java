package com.masai.services;

import java.util.ArrayList;
import java.util.List;

import com.masai.entities.News;
import com.masai.exceptions.DuplicateNewsException;
import com.masai.utility.FileUtils;

public class NewsService {
    private static final String NEWS_FILE_PATH = "news.txt";
    private List<News> newsList;

    public NewsService() {
        this.newsList = new ArrayList<>();
        loadNewsFromFile();
    }

    public void addNews(News news) throws DuplicateNewsException {
        validateNewsTitle(news.getTitle());
        newsList.add(news);
        saveNewsToFile();
        System.out.println("News article added successfully!");
    }

    public void editNews(String title, String newContent) {
        for (News news : newsList) {
            if (news.getTitle().equals(title)) {
                news.setContent(newContent);
                saveNewsToFile();
                System.out.println("News article updated successfully!");
                return;
            }
        }
        System.out.println("News article not found.");
    }

    private void validateNewsTitle(String title) throws DuplicateNewsException {
        for (News news : newsList) {
            if (news.getTitle().equals(title)) {
                throw new DuplicateNewsException("News article with the same title already exists.");
            }
        }
    }

    public List<News> getNewsList() {
        return newsList;
    }

    private void loadNewsFromFile() {
        List<String> lines = FileUtils.readFile(NEWS_FILE_PATH);
		for (String line : lines) {
		    News news = News.fromString(line);
		    if (news != null) {
		        newsList.add(news);
		    }
		}
    }

    private void saveNewsToFile() {
        List<String> lines = new ArrayList<>();
        for (News news : newsList) {
            String line = news.toString();
            lines.add(line);
        }
        FileUtils.writeFile(NEWS_FILE_PATH, lines);
    }
}
