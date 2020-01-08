package SR.API;

import CORS.CORS;
import SR.Functions.ChannelSongs;
import org.w3c.dom.Document;

import static spark.Spark.get;

/**
 * This class is responsible for returning a representation of a resource
 * from Sveriges Radio API.
 * @author Patriksku
 */
public class SrAPI {

    private final String path = "/api/sveriges-radio";
    private final String domain = "http://api.sr.se/api/v2/playlists/rightnow?channelid=";
    private CORS cors = new CORS();

    /**
     * Returns a JSON with various information about recent and upcoming songs
     * of a given radio-station at Sveriges Radio - the specified channelid query parameter.
     * @return JSON-file.
     */
    public Document getSongsJson() {
        get(path + "/getsongs/:channelid", (request, response) -> {
            cors.addSupport(request, response);

            String URI = domain + request.params(":channelid");
            ChannelSongs channelSongs = new ChannelSongs();

            response.status(200);
            response.type("application/json");
            return channelSongs.getFormat(URI, response);
        });
        return null;
    }

    /**
     * Initializes/deploys all methods within this class to act upon various URI-inputs based on
     * paths specified in the methods.
     */
    public void init(){
        getSongsJson();
    }
}