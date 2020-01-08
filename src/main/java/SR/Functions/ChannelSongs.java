package SR.Functions;

import SR.Beans.Songs2Keys;
import Handlers.FormatHandler;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import spark.Response;

/**
 * This class is responsible for grabbing the resource with song information from Sveriges Radio API as a JSON-file.
 * Modifications are made, and then either returned as XML or JSON-files, depending on what the user requests.
 * @author Patriksku
 */
public class ChannelSongs {

    HttpResponse<JsonNode> response;
    FormatHandler formatHandler = new FormatHandler();

    /**
     * Modifies and returns the acquired information from a given radio station.
     * @param URI endpoint of a resource in Sveriges Radio API.
     * @param serverResponse ----
     * @return String representation of a XML or JSON file.
     */
    public String getFormat(String URI, Response serverResponse) {

        Songs2Keys songs = new Songs2Keys(); //Object representation of the resource.

        try {
            response = Unirest.get(URI) //Loads the resource into a response object.
                    .queryString("format", "json")
                    .asJson();

            System.out.println("Response from SR:");
            System.out.println(response.getBody());
            System.out.println();

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
                    System.out.println("PREVIOUSSONG IS NOT HYE");

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
                    System.out.println("NEXTSONG IS NOT HYE");

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
        } catch (UnirestException ue) {
            serverResponse.status(404);
            ue.printStackTrace();
        }
        return formatHandler.getFormat(songs);
    }
}