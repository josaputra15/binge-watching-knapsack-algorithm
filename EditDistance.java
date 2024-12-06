import java.util.List;

/**
 * Implementing Levenshtein's Edit Distance algorithm without a predecessor table
 */
public class EditDistance {

    public String suggestGenre(String input, Database db) {
        String closestGenre = null;
        int minDistance = Integer.MAX_VALUE;
    
        input = input.toLowerCase().trim();
        System.out.println("Input: " + input);   // testing purposes

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
    
        // for (TVShow show : tvshows) {
        //     String[] genres = show.genre.toLowerCase().split(", ");
            
        //     for (String genre : genres) {
        //         System.out.println("Comparing to genre: " + genre);  //testing
    
        //         if (genre.equals(input)) {
        //             return genre;  // Exact match found
        //         }
    
        //         int distance = calculateEditDistance(input, genre);
        //         System.out.println("Distance to " + genre + ": " + distance);  
    
        //         if (distance < minDistance) {
        //             minDistance = distance;
        //             closestGenre = genre;
        //         }
        //     }
        // }
    
        System.out.println("Suggested Genre: " + closestGenre);  //testing
        
        return closestGenre != null ? closestGenre : "No close match found.";
    }

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

    private int match(char a, char b) {
        return a == b ? 0 : 1;
    }
}