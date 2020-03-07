package Spotify.Functions;

import Spotify.Beans.Profile;
import Handlers.FormatHandler;
import Spotify.Users.UserSessions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Response;

/**
 * This class is responsible for returning a JSON-file containing information
 * from a user's Spotify profile, and setting the profile together with
 * the specified User.
 * @author Patriksku
 */
public class UserProfile {
    private UserSessions userSessions;
    private final String USER_PROFILE_ENDPOINT = "https://api.spotify.com/v1/me";

    private HttpResponse<JsonNode> response;

    public UserProfile(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    /**
     * Requests access to a user's Spotify profile.
     * @param session_id of the user.
     * @return JSON.
     */
    public String requestMyProfile(String session_id) {
            try {
                response = Unirest.get(USER_PROFILE_ENDPOINT)
                        .header("Authorization", (userSessions.get(session_id).getToken().getToken_type() + " " + userSessions.get(session_id).getToken().getAccess_token()))
                        .asJson();
            } catch (UnirestException e) {
                e.printStackTrace();
            }

        JSONObject envelope = response.getBody().getObject();
        return getMyProfile(envelope, session_id);
    }

    /**
     * Creates a Profile-object for a user containing information from their Spotify account
     * based on the unique session id. This method also checks if the profile has granted
     * the application access to the user's Spotify account. If a profile exists - it will be updated,
     * otherwise a profile will be created.
     * @param session_id unique id for current user.
     */
    public void createProfile(String session_id, Response serverResponse) {
        if (!userSessions.contains(session_id)) {
            serverResponse.status(401);
            serverResponse.type("text/plain");
            System.out.println("This user's session_id does not exist. Please connect your account to Spotify first, then try again.");
        } else {
            try {
                response = Unirest.get(USER_PROFILE_ENDPOINT)
                        .header("Authorization", (userSessions.get(session_id).getToken().getToken_type() + " " + userSessions.get(session_id).getToken().getAccess_token()))
                        .asJson();
            } catch (UnirestException e) {
                e.printStackTrace();
            }

            JSONObject envelope = response.getBody().getObject();
            getProfile(envelope, session_id);
        }
    }

    /**
     * Creates and returns a Profile-object with information received from the current user's Spotify profile.
     * @param envelope JSONObject returned from Spotify.
     * @param session_id of current user.
     * @return JSON.
     */
    private String getMyProfile(JSONObject envelope, String session_id) {
        Profile profile = new Profile();

        JSONObject externalUrls = envelope.getJSONObject("external_urls");
        JSONArray images = envelope.getJSONArray("images");
        if (images.length() > 0) {
            JSONObject imageUrl = images.getJSONObject(0);
            profile.setDisplay_name(envelope.getString("display_name"));
            profile.setUser_id(envelope.getString("id"));
            profile.setProfile_url(externalUrls.getString("spotify"));
            profile.setImage_url(imageUrl.getString("url"));
        } else {
            profile.setDisplay_name(envelope.getString("display_name"));
            profile.setUser_id(envelope.getString("id"));
            profile.setProfile_url(externalUrls.getString("spotify"));
            profile.setImage_url(null);
        }
        profile.setSession_id(session_id);
        setProfile(session_id, profile); //Sets the profile for user.

        FormatHandler formatHandler = new FormatHandler();
        return formatHandler.getFormat(profile);
    }

    /**
     * Creates a Profile-object with information received from a user's Spotify profile.
     * @param envelope JSONObject returned from Spotify.
     * @param session_id of the user that a profile will be created for.
     */
    private void getProfile(JSONObject envelope, String session_id) {
        Profile profile = new Profile();

        JSONObject externalUrls = envelope.getJSONObject("external_urls");
        JSONArray images = envelope.getJSONArray("images");
        if (images.length() > 0) {
            JSONObject imageUrl = images.getJSONObject(0);
            profile.setDisplay_name(envelope.getString("display_name"));
            profile.setUser_id(envelope.getString("id"));
            profile.setProfile_url(externalUrls.getString("spotify"));
            profile.setImage_url(imageUrl.getString("url"));
        } else {
            profile.setDisplay_name(envelope.getString("display_name"));
            profile.setUser_id(envelope.getString("id"));
            profile.setProfile_url(externalUrls.getString("spotify"));
            profile.setImage_url(null);
        }
        profile.setSession_id(session_id);
        setProfile(session_id, profile);
    }

    /**
     * Sets the profile for current User, identifiable by the session_id.
     * @param session_id for current User to set the profile for.
     * @param profile to be set.
     */
    public void setProfile(String session_id, Profile profile) {
        userSessions.get(session_id).setProfile(profile);
    }
}
