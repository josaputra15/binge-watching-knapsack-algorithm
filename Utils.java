import java.util.ArrayList;
import java.util.List;

// This algo recursively tries all combinations of including or skipping each content
// Each step will ensure that:
// - the current time doesn't exceed the capacity (so it will keep adding if it still doesn't)
// - the bestList is updated whenever a higher value is found
// - Backtracking will allow checking all possibilities are explored efficiently

public class Utils {
    // Knapsack algorithm
    public static List<TVShow> getOptimalSchedule(List<TVShow> tvShows, int capacity) {
        List<TVShow> result = new ArrayList<>();
        dfs(tvShows, capacity, 0, new ArrayList<>(), 0, 0, result);
        return result;
    }

    private static void dfs(List<TVShow> tvShows, int capacity, int index, List<TVShow> currentList, double currentValue, int currentTime, List<TVShow> bestList) {

        if (currentTime > capacity) {
            return; //invalid case if current time (of all the best list) is more than capacity
        }
        if (currentValue > computeTotalValue(bestList)) {
            bestList.clear();
            bestList.addAll(currentList);//recursively shcecking the best list if the currentValue(which is the total value in currentList) is bigger than the total value in the best, we gonna clear all of them and replace them with the bestList contents
        }

        if (index == tvShows.size()) {
            return;
        } //If index equals the size of the tvShows list, it means all contents have been considered, and no further recursion is needed

        // Skip current contents by incrementing the index and keep the values unchanged
        dfs(tvShows, capacity, index + 1, currentList, currentValue, currentTime, bestList);

        // Add the current movie to the list (keeping track with the index)
        currentList.add(tvShows.get(index));

        // THis is a recursive call to traverse by moving the next movie to the index++, and updates all component
        dfs(tvShows, capacity, index + 1, currentList, currentValue + tvShows.get(index).value, 
            currentTime + tvShows.get(index).totalDuration, bestList);

        currentList.remove(currentList.size() - 1); // this is the backtracking step where that this recursive function can explore other paths without interference from previously added movies.
    }

    public static void computeShowValues(List<TVShow> tvShows, List<String> preferredGenres, List<String> preferredShows) {

        for (TVShow tvShow : tvShows) {
            double genrePreference = computeGenrePreference(tvShow.genre, preferredGenres);
            tvShow.value = (genrePreference);
            //ANYBODY PLEASE add another value you guys were talkin about and tinker with it and determine how to collect it
            //this part (and anything in the main class) are all you need to change how do we compute the value
        }
    }


    //The way it calculates is based on the count of the matches ex
    // Case 1: Breaking Bad, Genre: Crime, Drama, Thriller
    // and the user's genre preferences are Crime, Horror, Comedy , that means = matches 1/3 for that movie = score = 0.3
    //
    // Case 2: Game of Thrones, Genre: Action, Adventure, Drama
    // and the user's genre preferences are Action, Adventure, Drama , that means = matches 3/3 for that movie = score = 1.0
    //
    // Case 3: The Walking Dead, Genre: Drama, Horror, Thriller
    // and the user's genre preferences are Crime, Adventure, Sci-Fi that means = matches 0/3 for that movie = score = 0.0
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

    //David may need to implement this after changing the knapsack algorithm 
    private static double computeShowPreference(String shows, List<String> preferredShows) {
        return 0.0;
    }

    // This method is for summing up the values that we get from every movie chosen
    public static double computeTotalValue(List<TVShow> tvShows) {
        return tvShows.stream().mapToDouble(show -> show.value).sum();
    }
}



