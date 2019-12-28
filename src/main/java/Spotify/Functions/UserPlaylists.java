package Spotify.Functions;

import Spotify.Beans.Playlist;
import Spotify.Beans.PlaylistArray;
import Spotify.Beans.User;
import Spotify.Handlers.FormatHandler;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Inte klar än.
 */
public class UserPlaylists {

    private final String SPOTIFY_PLAYLIST_DOMAIN = "https://open.spotify.com/playlist/";
    private final String USER_PLAYLIST_ENDPOINT = "https://api.spotify.com/v1/me/playlists";
    private HttpResponse<JsonNode> response;

    public String requestMyPlaylist(User user, String limit, String format) {
        try {
            System.out.println("Sending GET request to Spotify [USER_PLAYLIST_ENDPOINT]...");
            response = Unirest.get(USER_PLAYLIST_ENDPOINT)
                    .header("Authorization", (user.getToken().getToken_type() + " " + user.getToken().getAccess_token()))
                    .queryString("limit", limit)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return getPlaylist(response.getBody().getObject(), format);
    }

    public String getPlaylist(JSONObject envelope, String format) {
        JSONArray items = envelope.getJSONArray("items");
        JSONObject objectInsideItems;
        JSONArray images;
        String imageURL;
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
            arrayOfPlaylists.getArrayOfPlaylists()[i] = playlist;
        }

        FormatHandler formatHandler = new FormatHandler();
        return formatHandler.getFormat(format, arrayOfPlaylists);
    }
}
