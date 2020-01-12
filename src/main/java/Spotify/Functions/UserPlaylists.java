package Spotify.Functions;

import Handlers.FormatHandler;
import Spotify.Beans.Playlist;
import Spotify.Beans.PlaylistArray;
import Spotify.Users.UserSessions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class is responsible for returning a JSON file
 * containing playlists from a user's Spotify account.
 * @author Patriksku
 */
public class UserPlaylists {

    private UserSessions userSessions;
    private HttpResponse<JsonNode> response;

    public UserPlaylists(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    /**
     * Requests access to a user's playlists from Spotify.
     * @param session_id of the user.
     * @param limit amount of playlists to return, a number between 0 and 50.
     * @return JSON with a user's Spotify playlists.
     */
    public String requestMyPlaylist(String session_id, String limit) {
        try {
            System.out.println("Sending GET request to Spotify [USER_PLAYLIST_ENDPOINT]...");
            String USER_PLAYLIST_ENDPOINT = "https://api.spotify.com/v1/me/playlists";
            response = Unirest.get(USER_PLAYLIST_ENDPOINT)
                    .header("Authorization", (userSessions.get(session_id).getToken().getToken_type() + " " + userSessions.get(session_id).getToken().getAccess_token()))
                    .queryString("limit", limit)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return getPlaylist(response.getBody().getObject());
    }

    /**
     * Returns a user's playlists from Spotify.
     * @param envelope JSONObject returned from Spotify with playlists.
     * @return JSON containing playlists.
     */
    public String getPlaylist(JSONObject envelope) {
        JSONArray items = envelope.getJSONArray("items");
        JSONObject objectInsideItems;
        JSONArray images;
        String imageURL;
        String SPOTIFY_PLAYLIST_DOMAIN = "https://open.spotify.com/playlist/";
        int amountOfPlaylists = items.length();
        PlaylistArray arrayOfPlaylists = new PlaylistArray(amountOfPlaylists);
        JSONObject[] objectsOfItems = new JSONObject[amountOfPlaylists];

        for (int i = 0; i < amountOfPlaylists; i++) {
            Playlist playlist = new Playlist();
            objectsOfItems[i] = items.getJSONObject(i);
            objectInsideItems = items.getJSONObject(i);
            images = objectInsideItems.getJSONArray("images");
            imageURL = images.getJSONObject(0).getString("url");

            //Set all chosen fields in a Playlist-object.
            playlist.setPlaylist_name(objectsOfItems[i].getString("name"));
            playlist.setSpotify_url(SPOTIFY_PLAYLIST_DOMAIN + objectsOfItems[i].getString("id"));
            playlist.setPlaylist_id(objectsOfItems[i].getString("id"));
            playlist.setImage_url(imageURL);
            System.out.println(playlist.toString());
            arrayOfPlaylists.getArrayOfPlaylists()[i] = playlist;
        }

        FormatHandler formatHandler = new FormatHandler();
        return formatHandler.getFormat(arrayOfPlaylists);
    }
}
