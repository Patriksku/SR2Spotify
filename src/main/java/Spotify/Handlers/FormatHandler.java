package Spotify.Handlers;

import Spotify.Beans.PlaylistArray;
import Spotify.Beans.Profile;
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
