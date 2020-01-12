package SR.API;

import SR.Functions.ChannelSongs;

import static spark.Spark.get;

/**
 * This class is responsible for returning a representation of a resource - current songs playing
 * at a given radio station, from Sveriges Radio API.
 * @author Patriksku
 */
public class SrAPI {

    private final String domain = "http://api.sr.se/api/v2/playlists/rightnow?channelid=";

    /**
     * Returns a JSON with various information about recent and upcoming songs
     * of a given radio-station at Sveriges Radio - the specified channelid query parameter,
     * when accessing this endpoint.
     */
    public void getSongsJson() {
        get("/api/v1/sveriges-radio/songs/:channelid", (request, response) -> {

            String URI = domain + request.params(":channelid");
            ChannelSongs channelSongs = new ChannelSongs();

            response.status(200);
            response.type("application/json");
            return channelSongs.getFormat(URI, response);
        });
    }

    /**
     * Initializes methods with endpoints in this class.
     */
    public void init(){
        getSongsJson();
    }
}