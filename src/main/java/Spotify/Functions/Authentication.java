package Spotify.Functions;

import Spotify.Beans.Token;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Authentication {

    /**
     * Auth - Funkar
     * https://accounts.spotify.com/authorize?client_id=a73c33b920fc498d831d79de4542ccc1&response_type=code&redirect_uri=http://localhost:4567/api/spotify/login
     */

    private final String AUTH_DOMAIN = "https://accounts.spotify.com/";
    private final String CLIENT_ID = "a73c33b920fc498d831d79de4542ccc1";
    private final String CLIENT_SECRET = "finns i Spotify API-dokumentet i drive (Patriks konto)";
    private final String RESPONSE_TYPE = "code";
    private final String REDIRECT_URI = "http://localhost:4567/api/spotify/login";
    private final String TOKEN_ENDPOINT = "https://accounts.spotify.com/api/token";

    private String encoded_auth_key = null;
    private String session_id = null;

    private HttpResponse<JsonNode> response;

    private UserSessions userSessions;

    public Authentication(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

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
                        .asJson();

                createToken(response.getBody(), session_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void createToken(JsonNode json, String session_id) {
        Token tokenObject = new Token();
        JSONObject envelope = json.getObject();

        tokenObject.setAccess_token(envelope.getString("access_token"));
        tokenObject.setToken_type(envelope.getString("token_type"));
        tokenObject.setExpires_in(envelope.getInt("expires_in"));
        tokenObject.setRefresh_token(envelope.getString("refresh_token"));
        tokenObject.setScope(envelope.getString("scope"));

        userSessions.put(session_id, tokenObject);
        System.out.println("\nTesting if tokenObject is correct... \n");
        System.out.println(userSessions.get(session_id).toString());
    }

    public void refreshToken(Token token) {
        try {
            response = Unirest.post(TOKEN_ENDPOINT)
                    .header("Authorization", encoded_auth_key)
                    .field("grant_type", "refresh_token")
                    .field("refresh_token", token.getRefresh_token())
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

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getUserAuthToSpotify() {
        return AUTH_DOMAIN +
                "authorize?client_id=" +
                CLIENT_ID +
                "&response_type=" +
                RESPONSE_TYPE +
                "&redirect_uri=" +
                REDIRECT_URI;
    }

    private String getBase64EncodedString(String CLIENT_ID, String CLIENT_SECRET){
        String beginning = "Basic ";
        String toEncode = CLIENT_ID + ":" + CLIENT_SECRET;
        String base64encoded = Base64.getEncoder().encodeToString(toEncode.getBytes());
        String auth_header = beginning + base64encoded;
        System.out.println("auth_header to be sent: " + auth_header);
        return auth_header;
    }
}
