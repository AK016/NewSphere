package com.masai.utility;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.masai.entities.User;
import com.masai.entities.News;

public class FileExists {
    
    public static void storeUserCredentials(Map<String, User> userMap) {
        try (PrintWriter writer = new PrintWriter("user_credentials.txt")) {
            for (Map.Entry<String, User> entry : userMap.entrySet()) {
                User user = entry.getValue();
                writer.println(user.getUsername() + "," + user.getPassword());
            }
        } catch (IOException e) {
            System.out.println("Error storing user credentials: " + e.getMessage());
        }
    }
    
    public static Map<String, User> retrieveUserCredentials() {
        Map<String, User> userMap = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("user_credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    User user = new User(username, password);
                    userMap.put(username, user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error retrieving user credentials: " + e.getMessage());
        }
        return userMap;
    }
    
    public static void storeNews(List<News> newsList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("news.txt"))) {
            oos.writeObject(newsList);
        } catch (IOException e) {
            System.out.println("Error storing news: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
	public static List<News> retrieveNews() {
        List<News> newsList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("news.txt"))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                newsList = (List<News>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error retrieving news: " + e.getMessage());
        }
        return newsList;
    }
    
}
