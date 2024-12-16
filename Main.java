import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class that facilitates user interaction with the TV show selection system. 
 * It allows users to input their preferences and computes an optimal binge-watching schedule based on available time and selected genres/shows.
 */
public class Main extends Utils {

    private static void printSeparator() {
        System.out.println("\n========================================");
    }

    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";

     /**
     * Displays a highlighted header with cyan color.
     *
     * @param text The text to be displayed in the header.
     */
    private static void displayHighlightedHeader(String text) {
        System.out.println(CYAN + text + RESET);
    }

    /**
     * Truncates a string to a specified maximum length, appending "..." if the string exceeds the maximum length.
     *
     * @param input The input string to be truncated.
     * @param maxLength The maximum allowed length for the string.
     * @return A truncated string if it exceeds the specified length, otherwise returns the input string.
     */
    private static String truncate(String input, int maxLength) {
        if (input.length() > maxLength) {
            return input.substring(0, maxLength - 3) + "...";
        }
        return input;
    }

    /**
     * Generates a separator line dynamically based on the total column width for formatting tables.
     *
     * @param totalWidth The total width of the table.
     * @return A string containing the separator line.
     */
    private static String generateSeparator(int totalWidth) {
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < totalWidth; i++) {
            separator.append("-");
        }
        return separator.toString();
    }

     /**
     * Displays the available TV shows in a formatted table.
     *
     * @param db The database containing the TV shows.
     */
    private static void displayTVShowsInTable(Database db) {
        displayHighlightedHeader("\nAvailable TV Shows:");
        System.out.println(String.format("%-4s | %-35s | %-30s | %-10s | %-15s", 
            "No.", "Title", "Genre", "IMDb", "Duration (min)"));

        // Calculate total width of the table and generate separator
        int totalWidth = 4 + 35 + 30 + 10 + 15 + (4 * 3); // Column widths + separators
        System.out.println(generateSeparator(totalWidth));

        int count = 1;
        for (TVShow tvShow : db.getTVShows()) {
            System.out.println(String.format("%-4d | %-35s | %-30s | %-10.1f | %-15d", 
                count, tvShow.title, truncate(tvShow.genre, 25), tvShow.imdbRating, tvShow.totalDuration));
            count++;
        }
    }

     /**
     * Displays the available genres in a formatted column layout.
     *
     * @param genres The list of genres to be displayed.
     */
    private static void displayGenresInColumns(List<String> genres) {
        displayHighlightedHeader("\nAvailable Genres:");
        int columns = 3;
        int count = 0;

        for (String genre : genres) {
            System.out.print(String.format("%-20s", genre));
            count++;
            if (count % columns == 0) {
                System.out.println();
            }
        }
        if (count % columns != 0) {
            System.out.println();
        }
    }

    /**
     * Prompts the user to input their available time in hours and converts it to minutes.
     *
     * @param scanner The Scanner instance used to read user input.
     * @return The available time in minutes.
     */
    private static int getAvailableTime(Scanner scanner) {
        int time = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("\nEnter your available time in hours:");
            if (scanner.hasNextInt()) {
                time = scanner.nextInt() * 60;
                validInput = true;
            } else {
                System.out.println("\n >> Invalid input. Please enter digits only.");
                scanner.next();
            }
        }
        return time;
    }

    /**
     * Prompts the user to input their preferred genres and uses the edit distance algorithm to suggest corrections.
     *
     * @param scanner The Scanner instance used to read user input.
     * @param editDistance The EditDistance instance used for genre suggestion.
     * @param db The database containing the TV shows.
     * @return A list of the user's preferred genres, possibly corrected based on suggestions.
     */
    private static List<String> getPreferredGenres(Scanner scanner, EditDistance editDistance, Database db) {
        System.out.println("\nEnter your preferred genres (comma separated):");
        scanner.nextLine();
        String[] preferredGenres = scanner.nextLine().split(", ");
        List<String> preferredGenreList = new ArrayList<>();

        for (String pref : preferredGenres) {
            pref = pref.trim();
            String suggestedGenre = editDistance.suggestGenre(pref, db);

            if (!suggestedGenre.equals("No close match found.") && !suggestedGenre.equalsIgnoreCase(pref)) {
                System.out.println(" >> Did you mean: " + suggestedGenre + "? (y/n)");
                String response = scanner.nextLine().trim();
                if (response.equalsIgnoreCase("y")) {
                    preferredGenreList.add(suggestedGenre);
                } else {
                    preferredGenreList.add(pref);
                }
            } else {
                preferredGenreList.add(suggestedGenre);
            }
        }
        return preferredGenreList;
    }

     /**
     * Prompts the user to input their preferred shows and uses the edit distance algorithm to suggest corrections.
     *
     * @param scanner The Scanner instance used to read user input.
     * @param editDistance The EditDistance instance used for show suggestion.
     * @param db The database containing the TV shows.
     * @return A list of the user's preferred TV shows, possibly corrected based on suggestions.
     */
    private static List<String> getPreferredShows(Scanner scanner, EditDistance editDistance, Database db) {
        System.out.println("\nEnter your list of shows you want to watch (comma separated):");
        String input = scanner.nextLine().trim();

        String[] preferredShows = input.split("\\s*,\\s*");
        List<String> preferredShowsList = new ArrayList<>();

        for (String pref : preferredShows) {
            pref = pref.trim();
            String suggestedShow = editDistance.suggestShow(pref, db);

            if (!suggestedShow.equals("No close match found.") && !suggestedShow.equalsIgnoreCase(pref)) {
                System.out.println(" >> Did you mean: " + suggestedShow + "? (y/n)");
                String response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("y")) {
                    preferredShowsList.add(suggestedShow);
                } 
            } else if (!suggestedShow.equals("No close match found.")) {
                preferredShowsList.add(suggestedShow);
            } 
        }

        return preferredShowsList;
    }

     /**
     * Displays the optimal binge-watching schedule based on the selected shows and available time.
     *
     * @param optimalSchedule The list of TV shows selected for the optimal schedule.
     */
    private static void displayOptimalSchedule(List<TVShow> optimalSchedule) {
        displayHighlightedHeader("\nOptimal Binge-Watching Schedule:");
        // Print the header
        System.out.println(String.format("%-4s | %-35s | %-30s | %-10s | %-15s", 
            "No.", "Title", "Genre", "IMDb", "Duration (min)"));
    
        // Calculate total width of the table and generate separator
        int totalWidth = 4 + 35 + 30 + 10 + 15 + (4 * 3);
        System.out.println(generateSeparator(totalWidth));
    
        // Print each row
        int totalDuration = 0;
        int count = 1;
        for (TVShow show : optimalSchedule) {
            System.out.println(String.format("%-4d | %-35s | %-30s | %-10.1f | %-15d", 
                count, show.title, truncate(show.genre, 25), show.imdbRating, show.totalDuration));
            totalDuration += show.totalDuration;
            count++;
        }
    
        // Print total runtime
        System.out.println("\nTotal Runtime: " + (totalDuration / 60) + " hours.");
    }
    

    /**
     * Main method to run the application. It interacts with the user to gather inputs,
     * processes the available TV shows based on preferences, and displays the optimal schedule.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Database db = new Database();
        Scanner scanner = new Scanner(System.in);
        EditDistance editDistance = new EditDistance();

        // Display available contents (shows and genres)
        displayTVShowsInTable(db);
        displayGenresInColumns(db.getGenres());

        // Get user inputs
        int availableTime = getAvailableTime(scanner);
        List<String> preferredGenres = getPreferredGenres(scanner, editDistance, db);
        List<String> preferredShows = getPreferredShows(scanner, editDistance, db);

        // Compute weighted values for the contents
        computeShowValues(db.getTVShows(), preferredGenres, preferredShows);

        // Optimal Schedule
        List<TVShow> optimalSchedule = getOptimalSchedule(db.getTVShows(), availableTime, preferredShows);

        // Display the results
        displayOptimalSchedule(optimalSchedule);
    }
}
