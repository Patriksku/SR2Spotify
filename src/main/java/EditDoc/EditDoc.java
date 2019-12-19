package EditDoc;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class contains all methods for modifying xml-files from Sveriges Radio API.
 * @author Patriksku
 */
public class EditDoc {

    /**
     * Removes "starttimeutc" and "stoptimeutc" elements from the document.
     * @param songs The document with prevSong och nextSong elements from the API.
     * @return document with changes.
     */
    @SuppressWarnings("Duplicates")
    public Document songs(Document songs) {

        // Get first element that matches "previoussong".
        Node previousSong = songs.getElementsByTagName("previoussong").item(0);

        // Creates a list of child-nodes to previoussong-element.
        NodeList previousSongList = previousSong.getChildNodes();

        for(int i = 0; i < previousSongList.getLength(); i++) {
            Node node = previousSongList.item(i);

            if("starttimeutc".equals(node.getNodeName()) || "stoptimeutc".equals(node.getNodeName())){
                previousSong.removeChild(node);
            }
        }

        // Get first element that matches "nextsong".
        Node nextSong = songs.getElementsByTagName("nextsong").item(0);

        // Creates a list of child-nodes to nextsong-element.
        NodeList nextSongList = nextSong.getChildNodes();

        for(int i = 0; i < nextSongList.getLength(); i++) {
            Node node = nextSongList.item(i);

            if("starttimeutc".equals(node.getNodeName()) || "stoptimeutc".equals(node.getNodeName())){
                nextSong.removeChild(node);
            }
        }
        //Returns new document with changes.
        return songs;
    }
}
