package Spotify.Functions;

import Spotify.API.SpotifyAPI;
import Spotify.Beans.Artist;
import Spotify.Users.UserSessions;
import com.google.gson.FieldAttributes;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;


/**
 * @author Sara Karic
 */

public class SearchSpotify {

    private final String SEARCH_ENDPOINT = "https://api.spotify.com/v1/search";
    private UserSessions userSessions;
    private HttpResponse<JsonNode> response;


    public SearchSpotify(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    public void requestSearch(String search, String session_id) {


        try {
            response = Unirest.get(SEARCH_ENDPOINT)
                    .header("Authorization", (userSessions.get(session_id).getToken().getToken_type() + " " + userSessions.get(session_id).getToken().getAccess_token()))
                    .queryString("q", search)
                    .queryString("type", "album,artist,playlist,track")
                    .asJson();


        } catch (UnirestException e) {
            e.printStackTrace();

        }
        JSONObject envelope = response.getBody().getObject();
        System.out.println(envelope);


    }


}
