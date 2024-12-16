import java.util.ArrayList;
import java.util.List;

/**
 * Implementing Levenshtein's Edit Distance algorithm without a predecessor table
 */
public class EditDistance {

    /**
     * Suggests the closest matching genre from the database based on the input string.
     * Uses the Levenshtein Edit Distance algorithm to calculate the difference between the input 
     * genre and genres in the database.
     * 
     * @param input The genre input by the user to compare against the database.
     * @param db The database containing the genres to be compared.
     * @return The closest genre found in the database, or a message indicating no close match.
     */
    public String suggestGenre(String input, Database db) {
        String closestGenre = null;
        int minDistance = Integer.MAX_VALUE;
    
        input = input.toLowerCase().trim();

        List<String> genresDB = db.getGenres();

        for (String genre : genresDB){

            if(genre.toLowerCase().equals(input)){
                return genre;
            }

            int distance = calculateEditDistance(input, genre.toLowerCase());

            if (distance < minDistance) {
                minDistance = distance;
                closestGenre = genre;
            }
        }
        
        return closestGenre != null ? closestGenre : "No close match found.";
    }

     /**
     * Suggests the closest matching TV show from the database based on the input string.
     * Uses the Levenshtein Edit Distance algorithm to calculate the difference between the input 
     * show title and TV show titles in the database.
     * 
     * @param input The TV show title input by the user to compare against the database.
     * @param db The database containing the TV show titles to be compared.
     * @return The closest TV show title found in the database, or a message indicating no close match.
     */
    public String suggestShow(String input, Database db) {
        String closestShow = null;
        int minDistance = Integer.MAX_VALUE; 
    
        input = input.toLowerCase().trim(); 
    
        List<String> showsDB = new ArrayList<>();
        for (TVShow show : db.getTVShows()) {
            showsDB.add(show.title);
        }
    
        for (String show : showsDB) {
            String normalizedShow = show.toLowerCase().trim(); 
    
            if (normalizedShow.equals(input)) {
                return show; 
            }
    
            int distance = calculateEditDistance(input, normalizedShow);
    
            if (distance < minDistance) {
                minDistance = distance;
                closestShow = show;
            }
        }

        return closestShow != null ? closestShow : "No close match found.";
        
    }
    

    /**
     * Calculates the Levenshtein Edit Distance between two strings.
     * 
     * @param str1 The first string to compare.
     * @param str2 The second string to compare.
     * @return The Levenshtein Edit Distance between the two strings.
     */
    private int calculateEditDistance(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();

        int[][] dist = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) dist[i][0] = i;
        for (int j = 0; j <= m; j++) dist[0][j] = j;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int matchScore = dist[i - 1][j - 1] + match(str1.charAt(i - 1), str2.charAt(j - 1));
                int insertScore = dist[i][j - 1] + 1;
                int deleteScore = dist[i - 1][j] + 1;

                dist[i][j] = Math.min(matchScore, Math.min(insertScore, deleteScore));
            }
        }

        return dist[n][m];
    }

     /**
     * Determines if two characters match.
     * 
     * @param a The first character.
     * @param b The second character.
     * @return 0 if the characters match, or 1 if they do not match.
     */
    private int match(char a, char b) {
        return a == b ? 0 : 1;
    }
}