
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * Let's try to grab albums from "the weeknd"
 * if it worked, it will show Kiss Land, Beauty Behind the Madness,Starboy (which I already saw from the website)
 */

public class RestExamplesTest extends TestCase {



    public static String getContent(Node n) {
        StringBuilder sb =new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();

    }

    public void testMusicBrainzExample() {
        String initialURL = "https://musicbrainz.org/ws/2/artist?query=the+weeknd&fmt=xml";
        /* MusicBrainz gives each element in their DB a unique ID, called an MBID. We'll use this to fecth that. */

        /* now let's parse the XML.  */
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

            System.out.println(id);
            System.out.println("-------------");

            /* Now let's use that ID to get info specifically about this artist. */

            /* check out the weeknd's album*/
            String lookupURL = "https://musicbrainz.org/ws/2/artist/" + id + "?inc=release-groups";
            URLConnection u2 = new URL(lookupURL).openConnection();
            u2.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");

            db = dbf.newDocumentBuilder();
            doc = db.parse(u2.getInputStream());
            /* let's get all the aliases. */
            NodeList aliases = doc.getElementsByTagName("release-group");
            for (int i = 0; i < aliases.getLength(); i++) {
                Node titleNode;
                titleNode = aliases.item(i).getFirstChild();
                System.out.println(getContent(titleNode));
            }

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
    }
    public void testXML(){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;

            String lookupURL = "https://musicbrainz.org/ws/2/release/8793ef8f-f1c1-4c58-90dd-268cf6d35a71?inc=recordings";
            URLConnection u2 = new URL(lookupURL).openConnection();
            u2.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");
            Document doc;
            db = dbf.newDocumentBuilder();
//           InputStream xml = u2.getInputStream();
//            doc = db.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("UTF-8"))));
            doc =db.parse(u2.getInputStream());
            //Document document = documentBuilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("UTF-8"))))

            System.out.println(u2.getInputStream());
        }catch (Exception ex) {
            System.out.println("XML parsing error" + ex);

        }
    }

    public void testMusicSong(){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db ;

            String lookupURL = "https://musicbrainz.org/ws/2/release/8793ef8f-f1c1-4c58-90dd-268cf6d35a71?inc=recordings";
            URLConnection u2 = new URL(lookupURL).openConnection();
            u2.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");
            Document doc ;
            db = dbf.newDocumentBuilder();
            doc = db.parse(u2.getInputStream());
//            Document document = documentBuilder.parse(new InputSource(new StringReader(xml)))
//            Document document = documentBuilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("UTF-8"))))

            NodeList songs = doc.getElementsByTagName("recording");//track-list
            System.out.println(songs.getLength());
            for (int i=0 ; i< songs.getLength();i++) {
                Node songs2 = songs.item(i).getFirstChild();

                System.out.println(getContent(songs2));
            }

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);

        }
    }
}