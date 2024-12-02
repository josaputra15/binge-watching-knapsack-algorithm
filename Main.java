public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        for (TVShow tvShow : db.tvShows) {
            System.out.println(tvShow);
        }
        for (String genre : db.getGenres()) {
            System.out.println(genre);
        }
    }
}
