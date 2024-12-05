import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Utils {

    private static void displayAvailableShowsAndGenres(Database db) {
        System.out.println("Available TV Shows:");
        for (TVShow tvShow : db.tvShows) {
            System.out.println(tvShow);
        }

        System.out.println("\nAvailable Genres:");
        for (String genre : db.getGenres()) {
            System.out.println(genre);
        }
    }

    private static int getAvailableTime(Scanner scanner) {
        System.out.println("\nEnter your available time in hours:");
        return scanner.nextInt() * 60;
    }

    private static List<String> getPreferredGenres(Scanner scanner) {
        System.out.println("Enter your preferred genres (comma separated):");
        scanner.nextLine(); // Consume newline
        String[] preferredGenres = scanner.nextLine().split(", ");
        List<String> preferredGenreList = new ArrayList<>();
        for (String genre : preferredGenres) {
            preferredGenreList.add(genre.trim());
        }
        return preferredGenreList;
    }

    private static void displayOptimalSchedule(List<TVShow> optimalSchedule) {
        System.out.println("\nOptimal Binge-Watching Schedule:");
        int count = 1;
        for (TVShow show : optimalSchedule) {
            System.out.println(count + ") " + show.title);
            count++;48
        }
        System.out.println("Total Value: " + computeTotalValue(optimalSchedule)); //I'm not sure this is needed 
    }



    public static void main(String[] args) {
        Database db = new Database();
        Scanner scanner = new Scanner(System.in);

        // Display available contents (movies and genres)
        displayAvailableShowsAndGenres(db);

        // Get user inputs
        int availableTime = getAvailableTime(scanner);
        List<String> preferredGenres = getPreferredGenres(scanner);

        // Compute weighted values for the contents
        computeShowValues(db.getTVShows(), preferredGenres);

        // Optimal Schedule
        List<TVShow> optimalSchedule = getOptimalSchedule(db.getTVShows(), availableTime);

        // Display the results
        displayOptimalSchedule(optimalSchedule);
    }
}

