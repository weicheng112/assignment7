import java.sql.ResultSet;
import java.sql.SQLException;

public class Song extends Entity{
    protected Album album;
    protected Artist performer;
    protected SongInterval duration;
    protected String genre;
    protected boolean liked;

    public Song(String name) {
        super(name);
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(0);
        genre = "";

    }

    public Song(){

    }
    public Song(String name, int length) {
        super(name);
        duration = new SongInterval(length);
        genre = "";
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        duration = new SongInterval(length);
    }

    public String showLength() {
        return duration.toString();
    }


    protected Album getAlbum() {
        return album;
    }

    protected void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getPerformer() {
        return performer;
    }

    public void setPerformer(Artist performer) {
        this.performer = performer;
    }


    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
    public String toString() {
        return super.toString() + " Artist" + this.performer + " Album" + this.album + " ";

    }

    public String toSQL() {
        return "insert into songs (id, name, artist, album) values (" + this.entityID + ", " +"\""+ this.name +"\""+ ", " + performer.entityID + ", "
                + album.entityID  + ");";
    }

    public void fromSQL(ResultSet rs) {
        try {
            this.entityID = rs.getInt("id");
            this.name = rs.getString("name");
        } catch(SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }

    }
}
