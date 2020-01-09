package Handlers;

import SR.Beans.Songs2Keys;
import Spotify.Beans.PlaylistArray;
import Spotify.Beans.Profile;
import Spotify.Beans.SessionID;
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
     * @param profile represents a user's Spotify profile.
     * @return XML or JSON based on the format.
     */
    public String getFormat(Profile profile) {
        try {
                ObjectMapper mapper = new ObjectMapper();
                jsonFormat = mapper.writeValueAsString(profile);
                return jsonFormat;

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
        return "Something went wrong while converting VisitorStatus-object to JSON.";
    }

    public String getFormat(SessionID sessionID) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonFormat = mapper.writeValueAsString(sessionID);

            return jsonFormat;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Something went wrong while converting SessionID-object to JSON.";
    }

    public String getFormat(Songs2Keys songs2Keys) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonFormat = mapper.writeValueAsString(songs2Keys);

            return jsonFormat;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Something went wrong while converting SessionID-object to JSON.";
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
