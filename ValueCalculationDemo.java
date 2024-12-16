import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates the value calculation involved in the optimal schedule generation process
 */
public class ValueCalculationDemo extends Utils{
    public static void main(String[] args) {
        // Example dataset of TV shows
        List<TVShow> tvShows = Arrays.asList(
            new TVShow("Breaking Bad", "Crime, Drama, Thriller", 9.5, 3000),
            new TVShow("Game of Thrones", "Action, Adventure, Drama", 9.3, 4000),
            new TVShow("Sherlock", "Crime, Mystery, Drama", 9.1, 1500),
            new TVShow("The Walking Dead", "Drama, Horror, Thriller", 8.2, 3600),
            new TVShow("Stranger Things", "Drama, Fantasy, Horror", 8.7, 2000)
        );

        // User preferences
        List<String> preferredGenres = Arrays.asList("Crime", "Action");
        List<String> preferredShows = Arrays.asList("Breaking Bad", "Game of Thrones", "Sherlock");

        // Available time (in minutes)
        int availableTime = 6000; // for example, 5000 minutes

        // Compute values and display calculations
        computeShowValues(tvShows, preferredGenres, preferredShows);

        // Sort the shows by value-to-duration ratio (descending order)
        tvShows.sort((a, b) -> Double.compare(b.value / b.totalDuration, a.value / a.totalDuration));

        // Select shows that fit in the available time
        int totalDuration = 0;
        double totalValue = 0;

        System.out.println("TV Show Calculations (Duration / Total Value for each show):");
        for (TVShow show : tvShows) {
            double durationOverValue = show.totalDuration / show.value; // Calculating Duration / Total Value
            System.out.printf("Title: %s, Duration: %d minutes, Total Value: %.2f, Duration/Total Value: %.2f%n", 
                              show.title, show.totalDuration, show.value, durationOverValue);

            // Add the show to the "bucket" if it fits in the available time
            if (totalDuration + show.totalDuration <= availableTime) {
                totalDuration += show.totalDuration;
                totalValue += show.value;
            }
        }

        // Display total duration and value for selected shows
        System.out.println("\nTotal Duration of selected shows: " + totalDuration + " minutes");
        System.out.println("Total Value of selected shows: " + totalValue);
    }

   
}
