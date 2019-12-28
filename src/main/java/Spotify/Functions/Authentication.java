package Spotify.Functions;

import Spotify.Beans.Token;
import Spotify.Beans.User;
import Spotify.Users.UserSessions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
import java.util.Base64;

/**
 * This class is responsible for authenticating users to Spotify,
 * requesting Tokens and creating Token-objects for users, and encoding
 * authentication to Spotify.
 * @author Patriksku
 */
public class Authentication {

    private final String AUTH_DOMAIN = "https://accounts.spotify.com/";
    private final String CLIENT_ID = "a73c33b920fc498d831d79de4542ccc1";
    private final String CLIENT_SECRET = "e7b9aa324e3d41e3b89969ec45816e71";
    private final String RESPONSE_TYPE = "code";
    private final String REDIRECT_URI = "http://localhost:4567/api/spotify/login";
    private final String TOKEN_ENDPOINT = "https://accounts.spotify.com/api/token";
    private final String SCOPES = "playlist-read-private playlist-read-collaborative playlist-modify-public playlist-modify-private";

    private String encoded_auth_key = null;

    private HttpResponse<JsonNode> response;

    private UserSessions userSessions;

    public Authentication(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    /**
     * Requests token information for a user from Spotify API.
     * @param authCode The user's authorization code which is exchanged for an access token.
     * @param session_id The user's unique id based on session, for assigning a token
     *                   to the correct user.
     */
    public void requestToken(String authCode, String session_id) {
        if(userSessions.contains(session_id)) {
            System.out.println("User with session_id: " + session_id + " already exists.");
        } else
            try {
                if(encoded_auth_key == null) {
                    this.encoded_auth_key = getBase64EncodedString(CLIENT_ID, CLIENT_SECRET);
                }
                System.out.println("Sending POST request to Spotify...");
                response = Unirest.post(TOKEN_ENDPOINT)
                        .header("Authorization", encoded_auth_key)
                        .field("grant_type", "authorization_code")
                        .field("code", authCode)
                        .field("redirect_uri", REDIRECT_URI)
                        .field("scope", SCOPES)
                        .asJson();

                createToken(response.getBody(), session_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * Creates a Token object and sets it for a new User object.
     * Token contains all necessary information for authorization to Spotify API.
     * @param json from Spotify with token information.
     * @param session_id unique session id for current user.
     */
    private void createToken(JsonNode json, String session_id) {
        User user = new User(); //Creates a User-object which will hold the created token.
        Token tokenObject = new Token();
        JSONObject envelope = json.getObject();

        tokenObject.setAccess_token(envelope.getString("access_token"));
        tokenObject.setToken_type(envelope.getString("token_type"));
        tokenObject.setExpires_in(envelope.getInt("expires_in"));
        tokenObject.setRefresh_token(envelope.getString("refresh_token"));
        tokenObject.setScope(envelope.getString("scope"));

        user.setToken(tokenObject); //Sets the token for current user.
        userSessions.put(session_id, user); //Put together user with the token information.
        System.out.println(userSessions.get(session_id).getToken().toString());
    }

    /**
     * Refreshes access token for a user.
     * @param token to be refreshed.
     */
    public void refreshToken(Token token) {
        try {
            response = Unirest.post(TOKEN_ENDPOINT)
                    .header("Authorization", encoded_auth_key)
                    .field("grant_type", "refresh_token")
                    .field("refresh_token", token.getRefresh_token())
                    .field("scope", SCOPES)
                    .asJson();

            System.out.println("--OLD values of TOKEN--");
            System.out.println(token.toString());

            JSONObject envelope = response.getBody().getObject();

            token.setAccess_token(envelope.getString("access_token"));
            token.setToken_type(envelope.getString("token_type"));
            token.setExpires_in(envelope.getInt("expires_in"));
            token.setScope(envelope.getString("scope"));

            System.out.println("--NEW values of TOKEN--");
            System.out.println(token.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return URI of the page where a user can choose to grant the API access to the user's Spotify account.
     */
    public String getUserAuthToSpotify() {
        return AUTH_DOMAIN +
                "authorize?client_id=" +
                CLIENT_ID +
                "&response_type=" +
                RESPONSE_TYPE +
                "&redirect_uri=" +
                REDIRECT_URI +
                "&scope=" +
                SCOPES;
    }

    /**
     * Encodes Client ID and Client SECRET in Base64 for authorizing the application to Spotify.
     * @param CLIENT_ID of the application.
     * @param CLIENT_SECRET of the application.
     * @return Encoded key.
     */
    private String getBase64EncodedString(String CLIENT_ID, String CLIENT_SECRET){
        String beginning = "Basic ";
        String toEncode = CLIENT_ID + ":" + CLIENT_SECRET;
        String base64encoded = Base64.getEncoder().encodeToString(toEncode.getBytes());
        String auth_header = beginning + base64encoded;
        System.out.println("auth_header to be sent: " + auth_header);
        return auth_header;
    }
}