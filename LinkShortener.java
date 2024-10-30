import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class LinkShortener {
    private static final String BASE_URL = "http://short.ly/";
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    
    private HashMap<String, String> urlMap;
    private HashMap<String, String> reverseUrlMap;
    
    public LinkShortener() {
        urlMap = new HashMap<>();
        reverseUrlMap = new HashMap<>();
    }
    
    // Generate a random short URL
    private String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder(SHORT_URL_LENGTH);
        Random random = new Random();
        
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            shortUrl.append(CHARACTERS.charAt(index));
        }
        
        return BASE_URL + shortUrl.toString();
    }
    
    // Shorten a long URL
    public String shortenUrl(String longUrl) {
        if (reverseUrlMap.containsKey(longUrl)) {
            System.out.println("This URL has already been shortened.");
            return reverseUrlMap.get(longUrl);
        }
        
        String shortUrl = generateShortUrl();
        while (urlMap.containsKey(shortUrl)) {
            shortUrl = generateShortUrl();  // Handle collisions
        }
        
        urlMap.put(shortUrl, longUrl);
        reverseUrlMap.put(longUrl, shortUrl);
        
        System.out.println("URL shortened successfully!");
        return shortUrl;
    }
    
    // Expand a short URL to the original URL
    public String expandUrl(String shortUrl) {
        if (urlMap.containsKey(shortUrl)) {
            return urlMap.get(shortUrl);
        } else {
            System.out.println("Error: Short URL not found.");
            return null;
        }
    }
    
    public static void main(String[] args) {
        LinkShortener linkShortener = new LinkShortener();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Link Shortener!");
        
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Shorten a URL");
            System.out.println("2. Expand a short URL");
            System.out.println("3. Exit");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter the long URL: ");
                    String longUrl = scanner.nextLine();
                    String shortUrl = linkShortener.shortenUrl(longUrl);
                    System.out.println("Shortened URL: " + shortUrl);
                    break;
                
                case 2:
                    System.out.print("Enter the short URL: ");
                    String url = scanner.nextLine();
                    String originalUrl = linkShortener.expandUrl(url);
                    if (originalUrl != null) {
                        System.out.println("Original URL: " + originalUrl);
                    }
                    break;
                
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
