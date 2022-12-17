import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Artist extends Entity{
    protected ArrayList<Song> songs;
    protected ArrayList<Album> albums;

    public Artist(String name) {
        super(name);
    }

    public Artist() {
    }


    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    protected ArrayList<Album> getAlbums() {
        return albums;
    }

    protected void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }






    public String toSQL() {
        return "insert into artists (id, name, nAlbums, nSongs) values (" + this.entityID + ", " +"\""+ this.name+"\"" + "," + albums.size() + ","
                + songs.size()  + ");";
    }

    public void fromSQL(ResultSet rs) {
        try {
            this.entityID = rs.getInt("id");
            this.name = rs.getString("name");
            //this.albums.size() = rs.getInt("nAlbums");
            //this.songs.size() = rs.getInt("nSongs");

        } catch(SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }

    }
}
