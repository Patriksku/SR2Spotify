package Handlers;

import Lyrics.Functions.Lyrics;
import SR.Beans.Songs2Keys;
import Spotify.Beans.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * This class handles conversions from various objects to JSON.
 * @author Patriksku
 */
public class FormatHandler {
    private String jsonFormat;

    /**
     * Creates and returns a JSON based on a Profile object.
     *
     * @param profile represents a user's Spotify profile.
     * @return JSON.
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
     *
     * @param visitorStatus contains a boolean value - true if current user has
     *                      granted the application access to the user's Spotify account - otherwise false.
     * @return JSON.
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

    /**
     * Creates and returns a JSON file based on a SessionID object.
     *
     * @param sessionID object with session_id and Spotify-authorization status of a user.
     * @return JSON.
     */
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

    /**
     * Creates and returns a JSON file based on a Songs2Keys object.
     *
     * @param songs2Keys object with various information about current songs of a radio station.
     * @return JSON.
     */
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
     * Creates and returns a JSON file based on a PlaylistArray object.
     *
     * @param playlistArray represents a user's playlists from Spotify.
     * @return JSON.
     */
    public String getFormat(PlaylistArray playlistArray) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonFormat = mapper.writeValueAsString(playlistArray);

            return jsonFormat;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Something went wrong while returning playlist information. Please check your parameters.";
    }

    /**
     * creates a Json based on Lyrics object
     *
     * @param lyrics
     * @return JSON
     */
    public String getFormat(Lyrics lyrics) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonFormat = mapper.writeValueAsString(lyrics);

            return jsonFormat;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Something went wrong while converting Lyrics to JSON.";
    }

    public String getFormatAll(AllArray allArray) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonFormat = mapper.writeValueAsString(allArray);

            return jsonFormat;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Something went wrong while converting Search to JSON.";
    }

}

