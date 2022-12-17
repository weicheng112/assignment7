import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Scanner;

public class UserTest extends TestCase {

    User u = new User();
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testStoreSong() {
        u.defaultMusicBox();
        u.storeSong();
    }

    public void testStoreAlbum() {
        u.defaultMusicBox();
        u.storeAlbum();
    }

    public void testStoreArtist() {
        u.defaultMusicBox();
        u.storeArtist();
    }

    public void testSearchForArtist() {
        u.defaultMusicBox();
        u.searchForArtist("the weeknd");
        //c8b03190-306c-4120-bb0b-6f2ebfc06ea9
    }

    public void testSearchForAlbum() {
        u.defaultMusicBox();
        u.searchForAlbum(u.searchForArtist("the weeknd"));
        //8793ef8f-f1c1-4c58-90dd-268cf6d35a71 return starboy's ID
    }

    public void testforArray(){
        Scanner sc = new Scanner(System.in);
        String s=sc.nextLine();
        for(String x: s.split(" ")) {
            System.out.println(x);
        }
    }

    public void testSearchForSong() {
        u.defaultMusicBox();
        u.searchForSong("8793ef8f-f1c1-4c58-90dd-268cf6d35a71");
    }

    public void testCreatePlayList() {
        u.defaultMusicBox();
        u.createPlayList();
    }
}