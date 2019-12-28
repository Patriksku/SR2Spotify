package SR.API;

import SR.Functions.ChannelSongs;
import org.w3c.dom.Document;

import static spark.Spark.get;

/**
 * This class is responsible for displaying or returning a representation of a resource,
 * based on what type of URI a client is connecting to. This class handles all functionalities
 * from Sveriges Radio API.
 * @author Patriksku
 */
public class SrAPI {

    private final String path = "/api/sveriges-radio";
    private final String domain = "http://api.sr.se/api/v2/playlists/rightnow?channelid=";

    // http://localhost:4567/api/sveriges-radio/getsongs/207 <-- inga parametrar ger .json
    /**
     * Returns XML or JSON based on the requested Content-Type of the client.
     * The standard format is JSON if no format is specified.
     * @return JSON-file.
     */
    public Document getSongsJson() {
        get(path + "/getsongs/:channelid", (request, response) -> {
            String channelID = request.params(":channelid");
            String URI = domain + request.params(":channelid");
            ChannelSongs channelSongs = new ChannelSongs();

            if(request.contentType() != null){
                if (request.contentType().equalsIgnoreCase("application/xml")) {
                    response.type("application/xml");
                    return channelSongs.getFormat(URI, "xml", channelID);

                } else if (request.contentType().equalsIgnoreCase("application/json")) {
                    response.type("application/json");
                    return channelSongs.getFormat(URI, "json", channelID);
                }

            } else
                response.type("application/json");
            return channelSongs.getFormat(URI, "json", channelID);
        });
        return null;
    }

    // http://localhost:4567/api/sveriges-radio/getsongs/207/xml    ||
    // http://localhost:4567/api/sveriges-radio/getsongs/207/json   ||

    /**
     * Returns a JSON or XML-file with various information about recent and upcoming songs
     * of a given radio-station at Sveriges Radio.
     * @return JSON or XML-file.
     */
    public Document getSongsFormat() {
        get(path + "/getsongs/:channelid/:format", (request, response) -> {
            String channelID = request.params(":channelid");
            String URI = domain + request.params(":channelid");

            if (request.params(":format").equalsIgnoreCase("json")) {
                ChannelSongs channelSongs = new ChannelSongs();
                response.type("application/json"); //The response type will be JSON.
                return channelSongs.getFormat(URI, "json", channelID);

            } else if (request.params(":format").equalsIgnoreCase("xml")) {
                ChannelSongs channelSongs = new ChannelSongs();
                response.type("application/xml"); //The response type will be XML.
                return channelSongs.getFormat(URI, "xml", channelID);
            }
            return "Something went wrong. Please check ur parameters. Only XML and JSON is valid.";
        });
        return null;
    }

    /**
     * Initializes/deploys all methods within this class to act upon various URI-inputs based on
     * paths specified in the methods.
     */
    public void init(){
        getSongsJson();
        getSongsFormat();
    }
}