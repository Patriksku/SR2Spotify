package SR.Functions;

import Handlers.FormatHandler;
import SR.Beans.Radio;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Response;

/**
 * This class is responsible for grabbing the resource with song information from Sveriges Radio API as a JSON-file,
 * creating an object based on available information, and returning this as a JSON.
 * @author Patriksku
 */
public class ChannelSongs {

    HttpResponse<JsonNode> response;

    private static Radio currentRadioObject;
    public static Radio getCurrentRadio(){
        return currentRadioObject;
    }


    /**
     * Modifies and returns the acquired information from a given radio station.
     * @param URI endpoint of a resource in Sveriges Radio API.
     * @param serverResponse Response object for setting HTTP status code and Content-Type.
     * @return JSON.
     */
    public String getFormat(String URI, Response serverResponse) {

        Radio radio = new Radio(); //Object representation of the resource.
        currentRadioObject = radio;

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

                radio.setChannelid(channel.getInt("id"));
                radio.setChannelname(channel.getString("name"));

                //
                if (playlist.has("previoussong")) {
                    JSONObject previoussong = playlist.getJSONObject("previoussong");

                    if (previoussong.has("title")) {
                        radio.setPrevioustitle(previoussong.getString("title"));
                    }

                    if (previoussong.has("description")) {
                        radio.setPreviousdescription(previoussong.getString("description"));
                    }

                    if (previoussong.has("artist")) {
                        radio.setPreviousartist(previoussong.getString("artist"));
                    }

                    if (previoussong.has("album")) {
                        radio.setPreviousalbum(previoussong.getString("album"));
                    }
                }

                if (playlist.has("song")) {
                    JSONObject song = playlist.getJSONObject("song");

                    if (song.has("title")) {
                        radio.setTitle(song.getString("title"));
                    }

                    if (song.has("description")) {
                        radio.setDescription(song.getString("description"));
                    }

                    if (song.has("artist")) {
                        radio.setArtist(song.getString("artist"));
                    }

                    if (song.has("album")) {
                        radio.setAlbum(song.getString("album"));
                    }
                }

                if (playlist.has("nextsong")) {
                    JSONObject nextsong = playlist.getJSONObject("nextsong");

                    if (nextsong.has("title")) {
                        radio.setNexttitle(nextsong.getString("title"));
                    }

                    if (nextsong.has("description")) {
                        radio.setNextdescription(nextsong.getString("description"));
                    }

                    if (nextsong.has("artist")) {
                        radio.setNextartist(nextsong.getString("artist"));
                    }

                    if (nextsong.has("album")) {
                        radio.setNextalbum(nextsong.getString("album"));
                    }
                }
                currentRadioObject = radio;
                FormatHandler formatHandler = new FormatHandler();
                return formatHandler.getFormat(radio);
            }
        } catch (UnirestException | JSONException e) {
            serverResponse.status(400);
            serverResponse.type("text/plain");
            return "Make sure that your parameter is right and that the radio station exists - only numerical values are valid.";
        }
    }
}