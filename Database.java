import java.util.ArrayList;
import java.util.List;

class TVShow {
    String title;
    String genre;
    double imdbRating;
    int totalDuration; // in minutes
    double value;

    public TVShow(String title, String genre, double imdbRating, int totalDuration) {
        this.title = title;
        this.genre = genre;
        this.imdbRating = imdbRating;
        this.totalDuration = totalDuration;
        this.value = 0;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Genre: " + genre + ", IMDb Rating: " + imdbRating + ", Total Duration: "
                + totalDuration + " minutes";
    }
}

public class Database {
    List<TVShow> tvShows;

    public Database() {
        this.tvShows = new ArrayList<>();
        populateDatabase();
    }

    public List<TVShow> getTVShows() {
        return tvShows;
    }

    public List<String> getGenres() {
        List<String> genres = new ArrayList<>();
        for (TVShow tvShow : tvShows) {
            for (String genre : tvShow.genre.split(", ")) {
                if (!genres.contains(genre)) {
                    genres.add(genre);
                }
            }
        }
        return genres;
    }

    public void populateDatabase() {
        this.tvShows.add(new TVShow("Breaking Bad", "Crime, Drama, Thriller", 9.5, 2940)); // 49 minutes * 60 episodes
        this.tvShows.add(new TVShow("Game of Thrones", "Action, Adventure, Drama", 9.3, 4074)); // 57 minutes * 71
                                                                                                // episodes
        this.tvShows.add(new TVShow("The Wire", "Crime, Drama, Thriller", 9.3, 3600)); // 60 minutes * 60 episodes
        this.tvShows.add(new TVShow("Sherlock", "Crime, Drama, Mystery", 9.1, 792)); // 88 minutes * 9 episodes
        this.tvShows.add(new TVShow("Friends", "Comedy, Romance", 8.9, 5280)); // 22 minutes * 240 episodes
        this.tvShows.add(new TVShow("The Office", "Comedy", 8.9, 4840)); // 22 minutes * 220 episodes
        this.tvShows.add(new TVShow("Stranger Things", "Drama, Fantasy, Horror", 8.7, 2040)); // 51 minutes * 40
                                                                                              // episodes
        this.tvShows.add(new TVShow("Narcos", "Biography, Crime, Drama", 8.8, 1470)); // 49 minutes * 30 episodes
        this.tvShows.add(new TVShow("House of Cards", "Drama", 8.7, 3060)); // 51 minutes * 60 episodes
        this.tvShows.add(new TVShow("Black Mirror", "Drama, Sci-Fi, Thriller", 8.8, 1800)); // 60 minutes * 30 episodes
        this.tvShows.add(new TVShow("The Crown", "Biography, Drama, History", 8.7, 3480)); // 58 minutes * 60 episodes
        this.tvShows.add(new TVShow("The Mandalorian", "Action, Adventure, Fantasy", 8.8, 1600)); // 40 minutes * 40
                                                                                                  // episodes
        this.tvShows.add(new TVShow("The Witcher", "Action, Adventure, Drama", 8.2, 720)); // 60 minutes * 12 episodes
        this.tvShows.add(new TVShow("Mindhunter", "Crime, Drama, Thriller", 8.6, 1200)); // 60 minutes * 20 episodes
        this.tvShows.add(new TVShow("Ozark", "Crime, Drama, Thriller", 8.4, 1800)); // 60 minutes * 30 episodes
        this.tvShows.add(new TVShow("The Boys", "Action, Comedy, Crime", 8.7, 1800)); // 60 minutes * 30 episodes
        this.tvShows.add(new TVShow("Westworld", "Drama, Mystery, Sci-Fi", 8.6, 1860)); // 62 minutes * 30 episodes
        this.tvShows.add(new TVShow("The Handmaid's Tale", "Drama, Sci-Fi, Thriller", 8.4, 1800)); // 60 minutes * 30
        // episodes
        this.tvShows.add(new TVShow("Fargo", "Crime, Drama, Thriller", 8.9, 1590)); // 53 minutes * 30 episodes
        this.tvShows.add(new TVShow("True Detective", "Crime, Drama, Mystery", 9.0, 1650)); // 55 minutes * 30 episodes
        this.tvShows.add(new TVShow("Chernobyl", "Drama, History, Thriller", 9.4, 300)); // 60 minutes * 5 episodes
        this.tvShows.add(new TVShow("Better Call Saul", "Crime, Drama", 8.7, 2760)); // 46 minutes * 60 episodes
        this.tvShows.add(new TVShow("The Sopranos", "Crime, Drama", 9.2, 4620)); // 55 minutes * 84 episodes
        this.tvShows.add(new TVShow("Dexter", "Crime, Drama, Mystery", 8.6, 3180)); // 53 minutes * 60 episodes
        this.tvShows.add(new TVShow("Lost", "Adventure, Drama, Fantasy", 8.3, 3168)); // 44 minutes * 72 episodes
        this.tvShows.add(new TVShow("The Walking Dead", "Drama, Horror, Thriller", 8.2, 3168)); // 44 minutes * 72
                                                                                                // episodes
        this.tvShows.add(new TVShow("Homeland", "Crime, Drama, Mystery", 8.3, 3300)); // 55 minutes * 60 episodes
        this.tvShows.add(new TVShow("Peaky Blinders", "Crime, Drama", 8.8, 1800)); // 60 minutes * 30 episodes
        this.tvShows.add(new TVShow("Vikings", "Action, Adventure, Drama", 8.5, 2640)); // 44 minutes * 60 episodes
        this.tvShows.add(new TVShow("Mr. Robot", "Crime, Drama, Thriller", 8.5, 1960)); // 49 minutes * 40 episodes
        this.tvShows.add(new TVShow("The Big Bang Theory", "Comedy, Romance", 8.1, 4840)); // 22 minutes * 220 episodes
        this.tvShows.add(new TVShow("How I Met Your Mother", "Comedy, Romance", 8.3, 4840)); // 22 minutes * 220
                                                                                             // episodes
        this.tvShows.add(new TVShow("Brooklyn Nine-Nine", "Comedy, Crime", 8.4, 4840)); // 22 minutes * 220 episodes
        this.tvShows.add(new TVShow("Rick and Morty", "Animation, Adventure, Comedy", 9.2, 1150)); // 23 minutes * 50
        // episodes
        this.tvShows.add(new TVShow("BoJack Horseman", "Animation, Comedy, Drama", 8.7, 1250)); // 25 minutes * 50
                                                                                                // episodes
        this.tvShows.add(new TVShow("Archer", "Animation, Action, Comedy", 8.6, 1100)); // 22 minutes * 50 episodes
        this.tvShows.add(new TVShow("The Simpsons", "Animation, Comedy", 8.6, 6600)); // 22 minutes * 300 episodes
        this.tvShows.add(new TVShow("Family Guy", "Animation, Comedy", 8.1, 6600)); // 22 minutes * 300 episodes
        this.tvShows.add(new TVShow("South Park", "Animation, Comedy", 8.7, 6600)); // 22 minutes * 300 episodes
        this.tvShows.add(new TVShow("Futurama", "Animation, Comedy, Sci-Fi", 8.4, 2200)); // 22 minutes * 100 episodes
        this.tvShows.add(new TVShow("The Flash", "Action, Adventure, Drama", 7.6, 1806)); // 43 minutes * 42 episodes
        this.tvShows.add(new TVShow("Arrow", "Action, Adventure, Crime", 7.5, 1764)); // 42 minutes * 42 episodes
        this.tvShows.add(new TVShow("Supernatural", "Drama, Fantasy, Horror", 8.4, 1936)); // 44 minutes * 44 episodes
        this.tvShows.add(new TVShow("Smallville", "Action, Adventure, Drama", 7.5, 1764)); // 42 minutes * 42 episodes
        this.tvShows.add(new TVShow("Lucifer", "Crime, Drama, Fantasy", 8.1, 1764)); // 42 minutes * 42 episodes
        this.tvShows.add(new TVShow("Gotham", "Action, Crime, Drama", 7.8, 1764)); // 42 minutes * 42 episodes
        this.tvShows.add(new TVShow("Daredevil", "Action, Crime, Drama", 8.6, 1620)); // 54 minutes * 30 episodes
        this.tvShows.add(new TVShow("Jessica Jones", "Action, Crime, Drama", 8.0, 1680)); // 56 minutes * 30 episodes
    }
}