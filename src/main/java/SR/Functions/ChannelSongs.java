package SR.Functions;

import SR.Beans.Songs2Keys;
import Handlers.FormatHandler;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Response;

/**
 * This class is responsible for grabbing the resource with song information from Sveriges Radio API as a JSON-file -
 * modifications are made for cleaner information, and then returns this as a JSON-file.
 * @author Patriksku
 */
public class ChannelSongs {

    HttpResponse<JsonNode> response;

    /**
     * Modifies and returns the acquired information from a given radio station.
     * @param URI endpoint of a resource in Sveriges Radio API.
     * @param serverResponse Response object for setting HTTP status code and Content-Type.
     * @return JSON.
     */
    public String getFormat(String URI, Response serverResponse) {

        Songs2Keys songs = new Songs2Keys(); //Object representation of the resource.

        try {
            response = Unirest.get(URI) //Loads the resource into a response object.
                    .queryString("format", "json")
                    .asJson();

            //Creates JSON objects of the necessary fields.
            JsonNode json = response.getBody();

            JSONObject envelope = json.getObject();
            JSONObject playlist = envelope.getJSONObject("playlist");
            JSONObject channel = playlist.getJSONObject("channel");

            if(channel.getInt("id") == 0) { //SR-API indicates that no content for this resources exists.
                serverResponse.status(204); //No Content
                serverResponse.type(""); //Empty Content-Type
                return ""; //Return nothing
            } else {

                if (!playlist.has("previoussong")) {
                    JSONObject nextsong = playlist.getJSONObject("nextsong");

                    songs.setChannelid(channel.getInt("id"));
                    songs.setChannelname(channel.getString("name"));

                    songs.setPrevioustitle("");
                    songs.setPreviousdescription("");
                    songs.setPreviousartist("");
                    songs.setPreviousalbum("");

                    songs.setNexttitle(nextsong.getString("title"));
                    songs.setNextdescription(nextsong.getString("description"));
                    songs.setNextartist(nextsong.getString("artist"));
                    songs.setNextalbum(nextsong.getString("albumname"));

                    FormatHandler formatHandler = new FormatHandler();
                    return formatHandler.getFormat(songs);

                } else if (!playlist.has("nextsong")) {
                    JSONObject previoussong = playlist.getJSONObject("previoussong");

                    songs.setChannelid(channel.getInt("id"));
                    songs.setChannelname(channel.getString("name"));

                    songs.setPrevioustitle(previoussong.getString("title"));
                    songs.setPreviousdescription(previoussong.getString("description"));
                    songs.setPreviousartist(previoussong.getString("artist"));
                    songs.setPreviousalbum(previoussong.getString("albumname"));

                    songs.setNexttitle("");
                    songs.setNextdescription("");
                    songs.setNextartist("");
                    songs.setNextalbum("");

                    FormatHandler formatHandler = new FormatHandler();
                    return formatHandler.getFormat(songs);

                } else { //all fields exist in response-object from SR-API
                    JSONObject previoussong = playlist.getJSONObject("previoussong"); //--
                    JSONObject nextsong = playlist.getJSONObject("nextsong");

                    songs.setChannelid(channel.getInt("id"));
                    songs.setChannelname(channel.getString("name"));

                    songs.setPrevioustitle(previoussong.getString("title"));
                    songs.setPreviousdescription(previoussong.getString("description"));
                    songs.setPreviousartist(previoussong.getString("artist"));
                    songs.setPreviousalbum(previoussong.getString("albumname"));

                    songs.setNexttitle(nextsong.getString("title"));
                    songs.setNextdescription(nextsong.getString("description"));
                    songs.setNextartist(nextsong.getString("artist"));
                    songs.setNextalbum(nextsong.getString("albumname"));

                    FormatHandler formatHandler = new FormatHandler();
                    return formatHandler.getFormat(songs);
                }
            }
        } catch (UnirestException | JSONException e) {
            serverResponse.status(404);
            serverResponse.type("text/plain");
            return "404 Not Found. Make sure that your parameter is right - only numerical values are valid.";
        }
    }
}