import java.util.ArrayList;
import java.util.List;

/**
 * The Bucket class represents a container for a list of TV shows and their associated value. 
 * It is used as part of the dynamic programming approach for the knapsack problem.
 */
class Bucket {
    List<TVShow> tvShows;
    double value;

    public Bucket(List<TVShow> tvShows, double value) {
        this.tvShows = tvShows;
        this.value = value;
    }
}

/**
 * The Utils class provides utility methods for calculating optimal schedules, show values, and preferences 
 * for TV shows based on user preferences and genres using the knapsack algorithm.
 */
public class Utils {
    // Knapsack algorithm
    public static List<TVShow> getOptimalSchedule(List<TVShow> tvShows, int capacity, List<String> preferredShows) {

        List<TVShow> showsList = new ArrayList<>();
        for (TVShow show : tvShows){
            if (preferredShows.contains(show.title)){
                showsList.add(show);
            }
        }
        List<Bucket> dp = new ArrayList<>();
        for (int i = 0; i < capacity + 1; i++) {
            dp.add(new Bucket(new ArrayList<>(), 0));
        }

        int n = showsList.size();
        for (int i = 1; i < n + 1; i++) {
            for (int w = capacity; w >= 0; w--) {
                int weight = showsList.get(i - 1).totalDuration;
                double val = showsList.get(i - 1).value;

                if (weight <= w && dp.get(w).value < dp.get(w - weight).value + val) {
                    List<TVShow> newTvShows = new ArrayList<>(dp.get(w - weight).tvShows);
                    newTvShows.add(showsList.get(i - 1));
                    dp.set(w, new Bucket(newTvShows, dp.get(w - weight).value + val));
                }
            }
        }

        // Returning the maximum value of knapsack
        return dp.get(capacity).tvShows;
    }

     /**
     * Computes and assigns values to TV shows based on genre preferences, show preferences, and IMDB ratings.
     * The value of each show is calculated as a weighted sum of its genre preference, user preference score, and IMDB rating.
     * 
     * @param tvShows The list of TV shows to compute values for.
     * @param preferredGenres A list of preferred genres based on the user's preference.
     * @param preferredShows A list of preferred TV shows based on the user's preference.
     */
    public static void computeShowValues(List<TVShow> tvShows, List<String> preferredGenres, List<String> preferredShows) {

        assignShowPreference(preferredShows, tvShows);

        for (TVShow tvShow : tvShows) {
            double genrePreference = computeGenrePreference(tvShow.genre, preferredGenres);
            tvShow.value = (0.3 * genrePreference + 0.6 * tvShow.prefScore + 0.1 * tvShow.imdbRating);
        }
    }

    /**
     * Computes the genre preference score for a given TV show based on the user's preferred genres.
     * The score is calculated as the ratio of genres that match between the TV show and the user's preferences.
     * For example:
     * - Case 1: "Breaking Bad" (Genres: Crime, Drama, Thriller) and the user's preferences (Crime, Horror, Comedy) 
     *   would result in 1 match out of 3 genres, giving a score of 0.3.
     * - Case 2: "Game of Thrones" (Genres: Action, Adventure, Drama) and the user's preferences (Action, Adventure, Drama) 
     *   would result in 3 matches out of 3 genres, giving a score of 1.0.
     * - Case 3: "The Walking Dead" (Genres: Drama, Horror, Thriller) and the user's preferences (Crime, Adventure, Sci-Fi) 
     *   would result in 0 matches, giving a score of 0.0.
     * 
     * @param showGenres The genres of the TV show, separated by commas.
     * @param preferredGenres A list of the user's preferred genres.
     * @return A value between 0 and 1 indicating the TV show's genre preference score.
     */
    private static double computeGenrePreference(String showGenres, List<String> preferredGenres) {
        int matches = 0;
        for (String genre : showGenres.split(", ")) {
            if (preferredGenres.contains(genre)) {
                matches++;
            }
        }
        return matches > 0 ? (double) matches / preferredGenres.size() : 0.0;
    }

    /**
     * Assigns a preference score to each TV show based on the user's preferred shows list.
     * The preference score is distributed evenly across the preferred shows, with higher scores for earlier preferences.
     * 
     * @param preferredShows A list of the user's preferred TV shows.
     * @param tvShows The list of TV shows to assign preference scores to.
     */
    private static  void assignShowPreference(List<String> preferredShows, List<TVShow> tvShows) {
        double step = 10/preferredShows.size();
    
        for (int i = 0; i < preferredShows.size(); i++){
            for (TVShow show : tvShows){
                if (show.title.equals(preferredShows.get(i))){
                    show.prefScore = 10 - (step * i);
                }
            }
        }
    }

     /**
     * Computes the total value of a list of selected TV shows by summing their individual values.
     * 
     * @param tvShows The list of selected TV shows.
     * @return The total value of the selected TV shows.
     */
    public static double computeTotalValue(List<TVShow> tvShows) {
        return tvShows.stream().mapToDouble(show -> show.value).sum();
    }
}