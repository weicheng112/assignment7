

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class RestExampleTest {
    /** TheAudioDB uses a REST interface, accepting URLs as input and returning JSON. Here's an example of
     * how to fetch info about The Beatles. (of course.)
     * TheAudioDB's API is described here. If you pay $3, you can get access to all their Patreon services; the free
     * ones are fine for this assignment.
     */
    public static void TheAudioDBExample() {
        String requestURL = "https://www.theaudiodb.com/api/v1/json/2/search.php?s=";
        String artist = "coldplay";
        StringBuilder response = new StringBuilder();
        URL u;
        try {
            u = new URL(requestURL + artist);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
            return;
        }
        try {
            URLConnection connection = u.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int code = httpConnection.getResponseCode();

            String message = httpConnection.getResponseMessage();
            System.out.println(code + " " + message);
            if (code != HttpURLConnection.HTTP_OK) {
                return;
            }
            InputStream instream = connection.getInputStream();
            Scanner in = new Scanner(instream);
            while (in.hasNextLine()) {
                response.append(in.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading response");
            return;
        }
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray artists = (JSONArray)jsonObject.get("artists"); // get the list of all artists returned.
            JSONObject beatles =(JSONObject) artists.get(0);  // I happen to know that the beatles is the first entry. Otherwise I'd iterate.
            String mood = (String)beatles.get("mood");
            System.out.println("Mood: " + mood);
            String bio = (String)beatles.get("strBiographyEN");
            System.out.println("Biography: " + bio);

        } catch(ParseException e) {
            System.out.println("Error parsing JSON");
            return;
        }

    }

    public static void MusicBrainzExample() {

        String initialURL = "https://musicbrainz.org/ws/2/artist?query=beatles&fmt=xml";
        /* MusicBrainz gives each element in their DB a unique ID, called an MBID. We'll use this to fecth that. */

        /* now let's parse the XML.  */
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if threre's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");


            InputStream in = null;
            in = u.getInputStream();
            InputStream raw = new BufferedInputStream(in);
            Reader r = new InputStreamReader(raw);

            //打印输出
            int c;
            while((c = r.read())>0){
                System.out.println((char)c);
            }



//            Document doc = db.parse(u.getInputStream());
            /* let's get the artist-list node */
//            NodeList artists = doc.getElementsByTagName("artist-list");
            /* let's assume that the one we want is first. */
//            Node beatlesNode = artists.item(0).getFirstChild();
//            Node beatlesIDNode = beatlesNode.getAttributes().getNamedItem("id");
//            String id = beatlesIDNode.getNodeValue();
//            System.out.println("-------------");
//            System.out.println(id);


            String id = "b10bbbfc-cf9e-42e0-be17-e2c3e1d2600d";
            /* Now let's use that ID to get info specifically about this artist. */
//
            String lookupURL = "https://musicbrainz.org/ws/2/artist/" + id + "?inc=aliases&fmt=xml";
            URLConnection u2 = new URL(lookupURL).openConnection();
            u2.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");

            InputStream in2;
            in2 = u2.getInputStream();
            InputStream raw2 = new BufferedInputStream(in2);
            Reader r1 = new InputStreamReader(raw2);

            int d;
            while((d = r1.read())>0){
                System.out.print((char)d);
            }



//
//            db = dbf.newDocumentBuilder();
//            Document doc = db.parse(u2.getInputStream());
//            /* let's get all the aliases. */
//            NodeList aliases = doc.getElementsByTagName("alias");
//            for (int i = 0; i < aliases.getLength(); i++) {
//                System.out.println(aliases.item(i).getFirstChild().getNodeValue());
//            }

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
    }


    public static void main(String[] args) {
        MusicBrainzExample();
    }

}
