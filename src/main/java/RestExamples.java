

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RestExamples {
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

    public static String getContent(Node n) {
        StringBuilder sb =new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();

    }
    public static String MusicBrainzExample() {


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

            InputStream in = u.getInputStream();
            Document doc = db.parse(in);
            in.close();
            /* let's get the artist-list node */
            NodeList artists = doc.getElementsByTagName("artist-list");
            /* let's assume that the one we want is first. */
            Node beatlesNode = artists.item(0).getFirstChild();
            Node beatlesIDNode = beatlesNode.getAttributes().getNamedItem("id");
            String id = beatlesIDNode.getNodeValue();
            System.out.println("-------------");
            System.out.println(id);

            /* Now let's use that ID to get info specifically about this artist. */


            return id;

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
            return null;
        }
    }


    public static void sperateMethod(String id){

        try {
            DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder db1 = dbf1.newDocumentBuilder();

            String lookupURL = "https://musicbrainz.org/ws/2/artist/" + id + "?inc=aliases&fmt=xml";

            URLConnection u2 = new URL(lookupURL).openConnection();
            u2.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");

            Document doc1;

            InputStream in = u2.getInputStream();

            doc1 = db1.parse(in);
            in.close();
            /* let's get all the aliases. */
            NodeList aliases = doc1.getElementsByTagName("alias");
            for (int i = 0; i < aliases.getLength(); i++) {
                System.out.println(aliases.item(i).getFirstChild().getNodeValue());
            }
        }catch (Exception ex){
            System.out.println("XML parsing error" + ex);
        }
    }






    public static void main(String[] args) {

        sperateMethod(MusicBrainzExample());

    }
}
