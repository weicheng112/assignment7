import junit.framework.TestCase;

public class PlaylistTest extends TestCase {

    Playlist playlist;
    public void setUp() throws Exception {
        super.setUp();
        playlist = new Playlist();
    }
    public void testAddSong() {
        Song s1 = new Song();
        s1.setName("COOL");
        Song s2 = new Song();
        s2.setName("NotCool");
        playlist.addSong(s1);
        playlist.addSong(s2);
        System.out.println(playlist.getListOfSongs());
    }

    public void testDeleteSong() {
        Song s1 = new Song();
        s1.setName("COOL");
        Song s2 = new Song();
        s2.setName("NotCool");
        playlist.addSong(s1);
        playlist.addSong(s2);
        System.out.println(playlist.getListOfSongs());

        playlist.deleteSong(s1);
        System.out.println(playlist.getListOfSongs());
    }

    public void testMergeTwoPlaylist() {
        Song s1 = new Song();
        s1.setName("COOL");
        Song s2 = new Song();
        s2.setName("NotCool");
        Song s3 = new Song();
        s3.setName("Wow");
        Song s4 = new Song();
        s4.setName("Meow");
        Song s5 = new Song();
        s5.setName("PPPPPP");
        Song s6 = new Song();
        s6.setName("WHAT?");
        Playlist playlist100 = new Playlist();
        playlist100.addSong(s1);
        playlist100.addSong(s3);
        playlist100.addSong(s5);
        playlist100.addSong(s6);
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);

        Playlist newPlayList = new Playlist();
        newPlayList = playlist.mergeTwoPlaylist(playlist100.getListOfSongs());
        for(Song s: newPlayList.getListOfSongs()){
            System.out.println(s.name);
        }
    }

    public void testCreateRandomPlaylistByGenre() {
        Song s1 = new Song();
        s1.setName("1");
        s1.setGenre("jazz");
        Song s2 = new Song();
        s2.setName("2");
        s2.setGenre("rockN");
        Song s3 = new Song();
        s3.setName("3");
        s3.setGenre("rockN");
        Song s4 = new Song();
        s4.setName("4");
        s4.setGenre("jazz");
        Song s5 = new Song();
        s5.setName("5");
        s5.setGenre("rap");
        Song s6 = new Song();
        s6.setName("6");
        s6.setGenre("rap");
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);playlist.addSong(s4);playlist.addSong(s5);playlist.addSong(s6);

        Playlist ramdomList = new Playlist();
        ramdomList = playlist.createRandomPlaylistByGenre("rap");
        System.out.println("rap!!!");
        for(Song s:ramdomList.getListOfSongs()){
            System.out.println(s.name);
        }

        ramdomList = playlist.createRandomPlaylistByGenre("jazz");
        System.out.println("jazz");
        for(Song s: ramdomList.getListOfSongs()){
            System.out.println(s.name);
        }
    }

    public void testToXML() {
        Song s1 = new Song();
        s1.setName("1");
        s1.setPerformer(new Artist("hi"));
        s1.setAlbum(new Album("ohoh"));
        Song s2 = new Song();
        s2.setName("2");
        s2.setPerformer(new Artist("nihao"));
        s2.setAlbum(new Album("im good"));
        Song s3 = new Song();
        s3.setName("3");
        s3.setPerformer(new Artist("how bout dat"));
        s3.setAlbum(new Album("cat"));

        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);

        System.out.println(playlist.toXML());
    }

    public void testToJSON() {
        Song s1 = new Song();
        s1.setName("1");
        s1.setPerformer(new Artist("hi"));
        s1.setAlbum(new Album("ohoh"));
        Song s2 = new Song();
        s2.setName("2");
        s2.setPerformer(new Artist("nihao"));
        s2.setAlbum(new Album("im good"));
        Song s3 = new Song();
        s3.setName("3");
        s3.setPerformer(new Artist("how bout dat"));
        s3.setAlbum(new Album("cat"));

        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);

        System.out.println(playlist.toJSON());
    }



}