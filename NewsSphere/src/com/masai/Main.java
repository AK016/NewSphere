package com.masai;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import com.masai.entities.News;
import com.masai.entities.User;
import com.masai.exceptions.DuplicateNewsException;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.services.CustomerService;
import com.masai.services.NewsService;

public class Main {

    private static final User admin = new User("Admin", "admin@example.com", "admin", "admin");
    private static final User journalist = new User("Journalist", "journalist@example.com", "journalist", "journalist");

    public static void main(String[] args) throws InvalidDetailsException, IOException {
        Scanner scanner = new Scanner(System.in);
        CustomerService customerService = new CustomerService();
        NewsService newsService = new NewsService();

        // Add initial admin details and journalist accounts
        customerService.signup(admin);
        customerService.signup(journalist);

        while (true) {
            System.out.println("Welcome to NewsSphere");
            System.out.println("Please select an option:");
            System.out.println("1. Admin Login");
            System.out.println("2. Journalist Login");
            System.out.println("3. Reader Options");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    adminLogin(scanner, customerService, newsService);
                    break;
                case 2:
                    journalistLogin(scanner, customerService, newsService);
                    break;
                case 3:
                    readerOptions(scanner, customerService, newsService);
                    break;
                case 0:
                    System.out.println("Thank you for using NewsSphere. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminLogin(Scanner scanner, CustomerService customerService, NewsService newsService) throws IOException {
        System.out.println("Admin Login");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
            adminOptions(scanner, newsService);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void adminOptions(Scanner scanner, NewsService newsService) throws IOException {
        while (true) {
            System.out.println("Admin Options:");
            System.out.println("1. Add News Article");
            System.out.println("2. Edit News Article");
            System.out.println("3. View News");
            System.out.println("0. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewsArticle(scanner, newsService);
                    break;
                case 2:
                    editNewsArticle(scanner, newsService);
                    break;
                case 3:
                    viewNews(newsService);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addNewsArticle(Scanner scanner, NewsService newsService) throws IOException {
        System.out.println("Add News Article");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Content: ");
        String content = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Category: ");
        String category = scanner.nextLine();
        Date publicationDate = new Date();

        News news = new News(title, content, author, category, publicationDate);

        try {
            newsService.addNews(news);
        } catch (DuplicateNewsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editNewsArticle(Scanner scanner, NewsService newsService) throws IOException {
        System.out.println("Edit News Article");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("New Content: ");
        String newContent = scanner.nextLine();

        newsService.editNews(title, newContent);
    }

    private static void viewNews(NewsService newsService) {
        System.out.println("View News");
        System.out.println("----- News Articles -----");
        for (News news : newsService.getNewsList()) {
            System.out.println("Title: "+news.getTitle());
            System.out.println("Author: "+news.getAuthor());
            System.out.println("Category: "+news.getCategory());
            System.out.println("Content: "+news.getContent());
            System.out.println("Published Date: "+news.getPublicationDate());
        }
        System.out.println("--------------------------------------------");
    }

    private static void journalistLogin(Scanner scanner, CustomerService customerService, NewsService newsService) throws IOException {
        System.out.println("Journalist Login");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = customerService.login(username, password);

        if (user != null && username.equals(journalist.getUsername()) && password.equals(journalist.getPassword())) {
            journalistOptions(scanner, newsService);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void journalistOptions(Scanner scanner, NewsService newsService) throws IOException {
        while (true) {
            System.out.println("Journalist Options:");
            System.out.println("1. Add News Article");
            System.out.println("2. Edit News Article");
            System.out.println("3. View News");
            System.out.println("0. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewsArticle(scanner, newsService);
                    break;
                case 2:
                    editNewsArticle(scanner, newsService);
                    break;
                case 3:
                    viewNews(newsService);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void readerOptions(Scanner scanner, CustomerService customerService, NewsService newsService) {
        while (true) {
            System.out.println("Reader Options:");
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            System.out.println("3. View News");
            System.out.println("0. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    signIn(scanner, customerService);
                    break;
                case 2:
                    signUp(scanner, customerService);
                    break;
                case 3:
                    viewNews(newsService);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void signIn(Scanner scanner, CustomerService customerService) {
        System.out.println("Sign In");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = customerService.login(username, password);

        if (user != null) {
            System.out.println("Sign in successful. Welcome, " + user.getName() + "!");
            // Perform actions for signed-in reader
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void signUp(Scanner scanner, CustomerService customerService) {
        System.out.println("Sign Up");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = new User(name, email, username, password);

        try {
            customerService.signup(user);
        } catch (InvalidDetailsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
