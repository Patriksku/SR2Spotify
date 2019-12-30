package Spotify.Handlers;

import Spotify.Beans.PlaylistArray;
import Spotify.Beans.Profile;
import Spotify.Beans.VisitorStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * This class handles conversions from object to XML or JSON files.
 * @author Patriksku
 */
public class FormatHandler {
    private String jsonFormat;
    private String xmlFormat;

    /**
     * Creates and returns a JSON or XML file based on a Profile object.
     * @param format to be returned - JSON or XML.
     * @param profile represents a user's Spotify profile.
     * @return XML or JSON based on the format.
     */
    public String getFormat(String format, Profile profile) {
        try {
            if (format.equalsIgnoreCase("json")) {
                ObjectMapper mapper = new ObjectMapper();
                jsonFormat = mapper.writeValueAsString(profile);

                return jsonFormat;

            } else if (format.equalsIgnoreCase("xml")) {
                ObjectMapper mapper = new XmlMapper();
                xmlFormat = mapper.writeValueAsString(profile);

                return xmlFormat;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Something went wrong while returning profile information. Please try again.";
    }

    /**
     * Creates and returns a JSON file based on a VisitorStatus object.
     * @param visitorStatus contains a boolean value - true if current user has
     *                      granted the application access to the user's Spotify account - or false.
     * @return JSON
     */
    public String getFormat(VisitorStatus visitorStatus) {
        try {
                ObjectMapper mapper = new ObjectMapper();
                jsonFormat = mapper.writeValueAsString(visitorStatus);

                return jsonFormat;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Something went wrong while checking if the visitor has granted the application access to Spotify.";
    }

    /**
     * Creates and returns a JSON or XML file based on a PlaylistArray object.
     * @param format to be returned - JSON or XML.
     * @param playlistArray represents a user's playlists from Spotify.
     * @return XML or JSON based on the format.
     */
    public String getFormat(String format, PlaylistArray playlistArray) {
        try {
            if (format.equalsIgnoreCase("json")) {
                ObjectMapper mapper = new ObjectMapper();
                jsonFormat = mapper.writeValueAsString(playlistArray);

                return jsonFormat;

            } else if (format.equalsIgnoreCase("xml")) {
                ObjectMapper mapper = new XmlMapper();
                xmlFormat = mapper.writeValueAsString(playlistArray);

                return xmlFormat;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Something went wrong while returning playlist information. Please check your parameters.";
    }
}
