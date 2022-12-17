
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;



public class User {

    private ArrayList<Song> userSong ;
    private ArrayList<Album> userAlbum ;
    private ArrayList<Artist> userArtist ;

    public static String getContent(Node n) {
        StringBuilder sb =new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();

    }


    /**
     * Save all the Song in the userSong into DB
     */
    public void storeSong(){
        ArrayList<String> sqlSongList=new ArrayList<String>();
        for(Song s: userSong){
            sqlSongList.add(s.toSQL());
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            //open the new database: musicTest.db!!!!!!
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists songs");
            statement.executeUpdate("create table songs (id INTEGER NOT NULL PRIMARY KEY, name VARCHAR(50), artist integer, album integer)");

            for(int i=0;i<sqlSongList.size();i++){
                statement.executeUpdate(sqlSongList.get(i));
            }


            ResultSet rs = statement.executeQuery("select * from songs");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }


    }

    /**
     * Save all the Album in the userAlbum into DB
     */
    public void storeAlbum(){
        ArrayList<String> sqlAlbumList = new ArrayList<String>();

        for(Album a: userAlbum){
            sqlAlbumList.add(a.toSQL());
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            //open the new database: musicTest.db!!!!!!
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists albums");
            statement.executeUpdate("create table albums ( id INTEGER NOT NULL PRIMARY KEY, name TEXT NOT NULL, artist integer , nSongs integer)");

            for(int i=0;i<sqlAlbumList.size();i++){
                statement.executeUpdate(sqlAlbumList.get(i));
            }


            ResultSet rs = statement.executeQuery("select * from albums");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }


    }
    /**
     * Save all the Artist in the userArtist into DB
     */
    public void storeArtist(){
        ArrayList<String> sqlArtistList = new ArrayList<String>();
        for(Artist a: userArtist){
            sqlArtistList.add(a.toSQL());
        }


        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:musicTest.db");
            //open the new database: musicTest.db!!!!!!
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists artists");
            statement.executeUpdate("create table artists ( id INTEGER NOT NULL PRIMARY KEY, name TEXT NOT NULL, nAlbums integer, nSongs integer)");

            for(int i = 0; i<sqlArtistList.size();i++){
                statement.executeUpdate(sqlArtistList.get(i));
            }

            ResultSet rs = statement.executeQuery("select * from artists");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    /**
     * Create the original Music for the user
     */
    public void defaultMusicBox(){
        userSong = new ArrayList<Song>();
        userAlbum = new ArrayList<Album>();
        userArtist = new ArrayList<Artist>();
        Song song1 = new Song("Fix You");
        Artist artist1 = new Artist("Coldplay");
        Album album1 = new Album("X&Y");
        ArrayList<Song> tempSonglist = new ArrayList<Song>();
        ArrayList<Album> tempAlbumlist = new ArrayList<Album>();
        tempAlbumlist.add(album1); tempSonglist.add(song1);
        //song setup
        song1.setPerformer(artist1);song1.setAlbum(album1);
        //album setup
        album1.setArtist(artist1);album1.setSongs(tempSonglist);
        //artist setup
        artist1.setSongs(tempSonglist); artist1.setAlbums(tempAlbumlist);
        userAlbum.add(album1); userArtist.add(artist1); userSong.add(song1);

        ///notice that the Song have created the empty artist and album
        //need to figure out how to fix it in the future!!!!
    }

    /**
     * Search for Song base on previous searching on album
     * using mbIDFromAlbum to do that
     * user could choose what they want and create new Song into userSong
     * @param mbIDFromAlbum from the method: searchForAlbum
     *                      the unique ID in MusicBrainz
     */
    public void searchForSong(String mbIDFromAlbum){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db ;

            String lookupURL = "https://musicbrainz.org/ws/2/release/" + mbIDFromAlbum + "?inc=recordings";
            URLConnection u2 = new URL(lookupURL).openConnection();
            u2.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");
            Document doc ;
            db = dbf.newDocumentBuilder();
            doc = db.parse(u2.getInputStream());

            NodeList songs = doc.getElementsByTagName("recording");//recording
//            System.out.println(songs.getLength());
            for (int i=0 ; i< songs.getLength();i++) {
                Node songs2 = songs.item(i).getFirstChild();
                System.out.print(i+": ");
                System.out.println(getContent(songs2));
            }
            System.out.println("-------");
            System.out.println("choose what you like:)");
            Scanner sc = new Scanner(System.in);
            String ans = sc.next();
            for (int i=0 ; i< songs.getLength();i++) {
                Node songs2 = songs.item(i).getFirstChild();

                if(i == Integer.parseInt(ans)){
                    Song s = new Song(getContent(songs2));
                    userSong.add(s);
                    System.out.print("result: ");
                    System.out.println(userSong.get(userSong.size()-1));

                }
            }
        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);

        }
    }

    /**
     * Search for Album base on previous searching on Artist
     * using mbIDFromArtist to search for the Artist all album
     * release-groups is kind of album in MusicBrainz (not duplicate)
     * but we want to see the real album we need to go through release
     * therefore base on release-groupsID we still need releaseID(real album)
     * that we can find the song in release
     * @param mbIDFromArtist from the method: searchForArtist
     * @return albumId: the real album ID to find the song in that
     */
    public String searchForAlbum(String mbIDFromArtist){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            String lookupURL = "https://musicbrainz.org/ws/2/artist/" + mbIDFromArtist + "?inc=release-groups";
            URLConnection u2 = new URL(lookupURL).openConnection();
            u2.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");
            Document doc ;
            db = dbf.newDocumentBuilder();
            doc = db.parse(u2.getInputStream());
            /* let's get all the aliases. */
            NodeList artists = doc.getElementsByTagName("release-group");
            String releaseGroupId="";

            for (int i = 0; i < artists.getLength(); i++) {
                Node titleNode;
                titleNode = artists.item(i).getFirstChild();
                System.out.println(i+": ");
                System.out.println(getContent(titleNode));

//
            }

            System.out.println("-------");
            System.out.println("choose what you like:)");
            Scanner sc = new Scanner(System.in);
            String ans = sc.next();
            for (int i = 0; i < artists.getLength(); i++) {
                Node titleNode;
                titleNode = artists.item(i).getFirstChild();

                if(i==Integer.parseInt(ans)){
                    Album album = new Album(getContent(titleNode));
                    userAlbum.add(album);
                    releaseGroupId =artists.item(i).getAttributes().getNamedItem("id").getNodeValue();
                    System.out.println(releaseGroupId);
                }
//
            }

//            Node beatlesNode = artists.item(0).getFirstChild();
//            Node beatlesIDNode = beatlesNode.getAttributes().getNamedItem("id");
//            String id = beatlesIDNode.getNodeValue();

            String lookupURL2= "https://musicbrainz.org/ws/2/release-group/"+releaseGroupId+"?inc=releases";
            URLConnection u3 = new URL(lookupURL2).openConnection();
            u3.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");
            db = dbf.newDocumentBuilder();
            doc = db.parse(u3.getInputStream());
            NodeList albums = doc.getElementsByTagName("release-list");
            Node albumsNode = albums.item(0).getFirstChild();
            Node albumIDNode = albumsNode.getAttributes().getNamedItem("id");
            String albumId = albumIDNode.getNodeValue();
            System.out.println("-------------");
            System.out.println(albumId);
            return albumId;


        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
            return null;
        }
    }

    /**
     * Search for Artist by user providing artist name
     *
     * @param userString user can decide which artist they want to find
     * @return artistID: the artist ID in the MusicBrainz
     */
    public String searchForArtist(String userString){

        userString.split(" ");
        String initialURL = "https://musicbrainz.org/ws/2/artist?query=";
        for(String s:userString.split(" ")){
            initialURL=initialURL.concat(s);
            initialURL=initialURL.concat("+");
        }
        String leastString="&fmt=xml";
        initialURL = initialURL.concat(leastString);
//        the+weeknd&fmt=xml";

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if threre's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");

            Document doc = db.parse(u.getInputStream());
            /* let's get the artist-list node */
            NodeList artists = doc.getElementsByTagName("artist-list");
            /* let's assume that the one we want is first. */
            Node beatlesNode = artists.item(0).getFirstChild();
            Node beatlesIDNode = beatlesNode.getAttributes().getNamedItem("id");
            String id = beatlesIDNode.getNodeValue();
            Artist artist = new Artist(userString);
            userArtist.add(artist);
            System.out.println(id);
            System.out.println("-------------");
            return id;

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
            return null;
        }
    }

    /**
     * the method that in the starter() that user can generate their own playlist
     * and print out the playlist in the XML format
     */
    public void createPlayList(){
        Playlist pl = new Playlist();
        for ( int i=0; i<userSong.size();i++){
            System.out.print(i+": ");
            System.out.println(userSong.get(i));
        }
        System.out.println("which one do you want to put in the playlist?");
        System.out.println("((Please follow this format: 0 2 6 8))");
        System.out.println("After this we will show you the XML");
        Scanner sc = new Scanner(System.in);
        String s=sc.nextLine();
        for(String x: s.split(" ")) {
            int y = Integer.parseInt(x);
            pl.addSong(userSong.get(y));
        }
        System.out.println(pl.toXML());




    }

    /**
     * combine all the method in it
     * 1:Let user browse all the music information ( default)
     * 2:Let user search for music online (from MusicBrainz) starting with artist
     * 3:Let user create their own playlist and output in the XML format
     *
     */
    public void starter(){
        defaultMusicBox();
        String endOff;
        do{
        System.out.println("Welcome!!");
        System.out.println("press 1 for browsing your songs,albums,artists!! ");
        System.out.println("press 2 for finding something new from the website! (start with artist!!)");
        System.out.println("press 3 for creating your own playlist!!");
        Scanner sc = new Scanner(System.in);
        String answer = sc.next();
        if (answer.equals("1")){
            System.out.println("Songs:");
            for(int i=0;i<userSong.size();i++){
                System.out.println(userSong.get(i));
            }

            System.out.println("Albums:");
            for(int i=0;i<userAlbum.size();i++){
                System.out.println(userAlbum.get(i));
            }

            System.out.println("Artists:");
            for(int i=0;i<userArtist.size();i++){
                System.out.println(userArtist.get(i));
            }

        } else if (answer.equals("2")) {
            System.out.println("Please enter the artist name");
            Scanner sccc =new Scanner(System.in);
            String answer2 = sccc.nextLine();
            searchForSong(searchForAlbum(searchForArtist(answer2)));

            Song tempSong = userSong.get(userSong.size()-1);
            Album tempAlbum = userAlbum.get(userAlbum.size()-1);
            Artist tempArtist = userArtist.get(userArtist.size()-1);

            ArrayList<Song> tempSlist = new ArrayList<Song>();
            ArrayList<Album> tempAlist = new ArrayList<Album>();
            tempAlist.add(tempAlbum); tempSlist.add(tempSong);
            //song setup
            tempSong.setPerformer(tempArtist);tempSong.setAlbum(tempAlbum);
            //album setup
            tempAlbum.setArtist(tempArtist);tempAlbum.setSongs(tempSlist);
            //artist setup
            tempArtist.setSongs(tempSlist); tempArtist.setAlbums(tempAlist);

            userSong.set(userSong.size()-1,tempSong );
            userAlbum.set(userAlbum.size()-1,tempAlbum);
            userArtist.set(userArtist.size()-1,tempArtist);

            storeSong();
            storeArtist();
            storeAlbum();

        } else if (answer.equals("3")) {
            createPlayList();
        }

        System.out.println("continue???(Y/N)");
        Scanner scan = new Scanner(System.in);
        endOff = scan.next();

        }while (endOff.equalsIgnoreCase("Y"));


    }


    /**
     * Start Here!!!!
     * @param args
     */
    public static void main(String[] args) {
        User u = new User();
        u.starter();
    }

}
