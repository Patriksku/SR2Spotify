package SR.Functions;

import SR.Beans.Songs2Keys;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import java.io.IOException;

/**
 * This class is responsible for grabbing the resource with song information from Sveriges Radio API as a JSON-file.
 * Modifications are made, and then either returned as XML or JSON-files, depending on what the user requests.
 * @author Patriksku
 */
public class ChannelSongs {

    HttpResponse<JsonNode> response;

    /**
     * Modifies and returns the acquired information from a given radio station.
     * @param URI endpoint of a resource in Sveriges Radio API.
     * @param format of the file to be returned.
     * @param channelID id of the radio station from where information is gathered.
     * @return String representation of a XML or JSON file.
     */
    public String getFormat(String URI, String format, String channelID) {

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
                JSONObject previoussong = playlist.getJSONObject("previoussong");
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

                if (format.equalsIgnoreCase("json")) {
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonFormat = mapper.writeValueAsString(songs);

                    return jsonFormat;

                } else if (format.equalsIgnoreCase("xml")) {
                    ObjectMapper mapper = new XmlMapper();
                    String xmlFormat = mapper.writeValueAsString(songs);

                    return xmlFormat;
                }

            } catch (UnirestException | IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}