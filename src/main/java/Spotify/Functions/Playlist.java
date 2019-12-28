package Spotify.Functions;

import Spotify.Beans.Token;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * Inte klar än.
 */
public class Playlist {

    private final String USER_PLAYLIST_ENDPOINT = "https://api.spotify.com/v1/me/playlists";
    private HttpResponse<JsonNode> response;

    public void getUserPlaylist(Token token) {
        try {
            System.out.println("Sending GET request to Spotify [USER_PLAYLIST_ENDPOINT]...");
            response = Unirest.get(USER_PLAYLIST_ENDPOINT)
                    .header("Authorization", (token.getToken_type() + " " + token.getAccess_token()))
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        JSONObject envelope = response.getBody().getObject();
        int numberOfPlaylists = envelope.getInt("total");
        System.out.println("Number of playlists for this account: " + numberOfPlaylists);

        /*System.out.println("envelope: " + envelope);
        System.out.println("envelope toString: " + envelope.toString());*/
    }
}
