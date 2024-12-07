import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Utils {

    private static void printSeparator() {
        System.out.println("\n========================================");
    }

    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";

    private static void displayHighlightedHeader(String text) {
        System.out.println(CYAN + text + RESET);
    }

    private static String truncate(String input, int maxLength) {
        if (input.length() > maxLength) {
            return input.substring(0, maxLength - 3) + "...";
        }
        return input;
    }

    // Generates a separator line dynamically based on total column width
    private static String generateSeparator(int totalWidth) {
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < totalWidth; i++) {
            separator.append("-");
        }
        return separator.toString();
    }

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
                } else {
                    preferredShowsList.add(pref);
                }
            } else if (!suggestedShow.equals("No close match found.")) {
                preferredShowsList.add(suggestedShow);
            } else {
                System.out.println(" >> Show not recognized: " + pref);
            }
        }

        return preferredShowsList;
    }

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
        List<TVShow> optimalSchedule = getOptimalSchedule(db.getTVShows(), availableTime);

        // Display the results
        displayOptimalSchedule(optimalSchedule);
    }
}
