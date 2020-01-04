package Spotify.Functions;

import Spotify.Users.UserSessions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

public class AddSongToPlaylist {

    private final String ADD_SONG_PLAYLIST_ENDPOINT = "https://api.spotify.com/v1/playlists/";
    private HttpResponse<JsonNode> response;
    private UserSessions userSessions;

    public AddSongToPlaylist(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    public String addSongToPlaylist(String session_id, String playlist_id, String song_uri) {

        System.out.println("Adding song to playlist...");
        if (!userSessions.contains(session_id)) {
            return "A user with this session id does not exist.";
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
            e.printStackTrace();
            return "Something went wrong while adding song to playlist. Please check your parameters.";
        }
    }
}

