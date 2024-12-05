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
        int time = 0; 
        boolean validInput = false; 

        //validating user input
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
        System.out.println("Enter your preferred genres (comma separated):");
        scanner.nextLine(); 
        String[] preferredGenres = scanner.nextLine().split(", ");
        List<String> preferredGenreList = new ArrayList<>();

        for (String genre : preferredGenres) {
            genre = genre.trim();
            String suggestedGenre = editDistance.suggestGenre(genre, db.tvShows);

            if (!suggestedGenre.equals("No close match found.") && !suggestedGenre.equalsIgnoreCase(genre)) {
                System.out.println(" >> Did you mean: " + suggestedGenre + "? (y/n)");
                String response = scanner.nextLine().trim();
                if (response.equalsIgnoreCase("y")) {
                    preferredGenreList.add(suggestedGenre);
                } else {
                    preferredGenreList.add(genre);  // sse the original genre if user disagrees
                }
            } else {
                preferredGenreList.add(suggestedGenre);  // add genre if it's a good match or no match
            }
        }
        return preferredGenreList;
    }

    private static void displayOptimalSchedule(List<TVShow> optimalSchedule) {
        System.out.println("\nOptimal Binge-Watching Schedule: " + optimalSchedule.size() + " shows to watch.");
        int count = 1;
        int runtime = 0;
        for (TVShow show : optimalSchedule) {
            System.out.println(count + ") " + show.title);
            runtime += show.totalDuration;
            count++;
        }
        System.out.println("Total Runtime is approximately is " + runtime/60 + " hours.");
        // System.out.println("Total Value: " + computeTotalValue(optimalSchedule)); not sure this needs to be displayed
    }



    public static void main(String[] args) {
        Database db = new Database();
        Scanner scanner = new Scanner(System.in);
        EditDistance editDistance = new EditDistance();

        // Display available contents (movies and genres)
        displayAvailableShowsAndGenres(db);

        // Get user inputs
        int availableTime = getAvailableTime(scanner);
        List<String> preferredGenres = getPreferredGenres(scanner, editDistance, db);

        // Compute weighted values for the contents
        computeShowValues(db.getTVShows(), preferredGenres);

        // Optimal Schedule
        List<TVShow> optimalSchedule = getOptimalSchedule(db.getTVShows(), availableTime);

        // Display the results
        displayOptimalSchedule(optimalSchedule);
    }
}

