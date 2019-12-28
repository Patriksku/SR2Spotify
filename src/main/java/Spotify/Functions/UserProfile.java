package Spotify.Functions;

import Spotify.Beans.Profile;
import Spotify.Users.UserSessions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class is responsible for returning JSON or XML containing information
 * from a user's Spotify profile, and setting the profile together with
 * the specified User.
 * @author Patriksku
 */
public class UserProfile {
    private UserSessions userSessions;
    private final String USER_PROFILE_ENDPOINT = "https://api.spotify.com/v1/me";

    private HttpResponse<JsonNode> response;
    private String session_id;

    public UserProfile(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    //https://developer.spotify.com/documentation/web-api/reference/users-profile/get-current-users-profile/

    /**
     * Requests access to a user's Spotify profile.
     * @param session_id of the user.
     * @param format to be returned.
     * @return XML or JSON based on user input.
     */
    public String requestMyProfile(String session_id, String format) {
        this.session_id = session_id;
        if (!userSessions.contains(session_id)) {
            System.out.println("This user's session_id does not exist. Please connect your account to Spotify first, then try again.");
        }
        try {
            System.out.println("Sending GET request to Spotify [USER_PROFILE_ENDPOINT]...");
            response = Unirest.get(USER_PROFILE_ENDPOINT)
                    .header("Authorization", (userSessions.get(session_id).getToken().getToken_type() + " " + userSessions.get(session_id).getToken().getAccess_token()))
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        JSONObject envelope = response.getBody().getObject();
        return getMyProfile(envelope, format);
    }

    /**
     * Creates and returns a Profile-object of information received from a user's Spotify profile.
     * @param envelope JSONObject returned from Spotify.
     * @param format to be returned.
     * @return XML or JSON, based on user input.
     */
    private String getMyProfile(JSONObject envelope, String format) {
        Profile profile = new Profile();

        JSONObject externalUrls = envelope.getJSONObject("external_urls");
        JSONArray images = envelope.getJSONArray("images");
        JSONObject imageUrl = images.getJSONObject(0);

        profile.setDisplay_name(envelope.getString("display_name"));
        profile.setUser_id(envelope.getString("id"));
        profile.setProfile_url(externalUrls.getString("spotify"));
        profile.setImage_url(imageUrl.getString("url"));
        profile.setSession_id(session_id);

        setProfile(session_id, profile); //Sets the profile for user.

        try {
            if (format.equalsIgnoreCase("json")) {
                ObjectMapper mapper = new ObjectMapper();
                String jsonFormat = mapper.writeValueAsString(profile);

                return jsonFormat;

            } else if (format.equalsIgnoreCase("xml")) {
                ObjectMapper mapper = new XmlMapper();
                String xmlFormat = mapper.writeValueAsString(profile);

                return xmlFormat;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(profile.toString());
        return "Something went wrong while returning profile information. Please try again.";
    }

    /**
     * Sets the profile for current User, identifiable by the session_id.
     * @param session_id for current User to set the profile for.
     * @param profile to be set.
     */
    public void setProfile(String session_id, Profile profile) {
        userSessions.get(session_id).setProfile(profile);
        System.out.println("Successfully set profile for the user: ");
        System.out.println("-TOKEN-");
        System.out.println(userSessions.get(session_id).getToken().toString());
        System.out.println();
        System.out.println("-PROFILE-");
        System.out.println(userSessions.get(session_id).getProfile().toString());
    }

    //public void createUserProfile() {}
}
