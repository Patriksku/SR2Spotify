package Spotify.Functions;

import Spotify.Users.UserSessions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

/**
 * This class is responsible for adding a song to a user's
 * playlist on Spotify.
 * @author Patriksku
 */
public class AddSongToPlaylist {

    private final String ADD_SONG_PLAYLIST_ENDPOINT = "https://api.spotify.com/v1/playlists/";
    private HttpResponse<JsonNode> response;
    private UserSessions userSessions;

    public AddSongToPlaylist(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    /**
     * Adds a song to a user's playlist.
     * @param session_id of user to add song to
     * @param playlist_id of the playlist where a song will be added
     * @param song_uri of the song to add
     * @return Success or Error message
     */
    public String addSongToPlaylist(String session_id, String playlist_id, String song_uri) {

        if (!userSessions.contains(session_id)) {
            return "A user with this session_id has not granted access to the user's Spotify account.";
        }
        try {
            response = Unirest.post(ADD_SONG_PLAYLIST_ENDPOINT + playlist_id + "/tracks")
                    .header("Authorization", (userSessions.get(session_id).getToken().getToken_type() + " " + userSessions.get(session_id).getToken().getAccess_token()))
                    .queryString("uris", song_uri)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        try {
            response.getBody().getObject().getString("snapshot_id");
            return "Successfully added song to playlist.";
        } catch (JSONException e) {
            return "Something went wrong while adding song to playlist. Please check your parameters.";
        }
    }
}

