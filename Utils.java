import java.util.ArrayList;
import java.util.List;

class Bucket {
    List<TVShow> tvShows;
    double value;

    public Bucket(List<TVShow> tvShows, double value) {
        this.tvShows = tvShows;
        this.value = value;
    }
}

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

    public static void computeShowValues(List<TVShow> tvShows, List<String> preferredGenres, List<String> preferredShows) {

        assignShowPreference(preferredShows, tvShows);

        for (TVShow tvShow : tvShows) {
            double genrePreference = computeGenrePreference(tvShow.genre, preferredGenres);
            tvShow.value = (0.3 * genrePreference + 0.6 * tvShow.prefScore + 0.1 * tvShow.imdbRating);
        }
    }

    // The way it calculates is based on the count of the matches ex
    // Case 1: Breaking Bad, Genre: Crime, Drama, Thriller
    // and the user's genre preferences are Crime, Horror, Comedy , that means =
    // matches 1/3 for that movie = score = 0.3
    //
    // Case 2: Game of Thrones, Genre: Action, Adventure, Drama
    // and the user's genre preferences are Action, Adventure, Drama , that means =
    // matches 3/3 for that movie = score = 1.0
    //
    // Case 3: The Walking Dead, Genre: Drama, Horror, Thriller
    // and the user's genre preferences are Crime, Adventure, Sci-Fi that means =
    // matches 0/3 for that movie = score = 0.0
    //
    private static double computeGenrePreference(String showGenres, List<String> preferredGenres) {
        int matches = 0;
        for (String genre : showGenres.split(", ")) {
            if (preferredGenres.contains(genre)) {
                matches++;
            }
        }
        return matches > 0 ? (double) matches / preferredGenres.size() : 0.0;
    }

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

    // This method is for summing up the values that we get from every movie chosen
    public static double computeTotalValue(List<TVShow> tvShows) {
        return tvShows.stream().mapToDouble(show -> show.value).sum();
    }
}