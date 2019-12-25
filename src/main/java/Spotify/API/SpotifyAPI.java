package Spotify.API;

import SR.Functions.ChannelSongs;
import Spotify.Beans.Token;
import Spotify.Functions.Authentication;
import Spotify.Functions.AutoRefreshToken;
import Spotify.Functions.UserSessions;
import org.w3c.dom.Document;
import spark.Session;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

public class SpotifyAPI {

    private final String path = "/api/spotify";
    private UserSessions userSessions = new UserSessions();
    private Authentication auth = new Authentication(userSessions);
    private AutoRefreshToken autoRefreshToken = null;

    /**
     * Required params:
     * client_id
     * When you register your application, Spotify provides you a Client ID.
     *
     * response_type
     * Set to code.
     *
     * redirect_uri	Required.
     * The URI to redirect to after the user grants or denies permission.
     * This URI needs to have been entered in the Redirect URI whitelist that you specified when you registered your application.
     * The value of redirect_uri here must exactly match one of the values you entered when you registered your application,
     * including upper or lowercase, terminating slashes, and such.
     */

    public String hej(){
        get(path + "/hej", (request, response) -> {
            // experiment med sessions...
            Session session = request.session();
            System.out.println("session-id: " + session.id());
            //
            return "hej";
        });
        return "nej";
    }

    public String authingTest() {
        get(path + "/initTest", (request, response) -> {
            response.redirect(auth.getUserAuthToSpotify());
            return "Mby yes authingTest";
        });
        return "Mby fucking no";
    }

    public void spotify() {
        get(path + "/login", (request, response) -> {
            String authCode = null;
            authCode = request.queryParams("code");

            if(authCode == null) {
                return "Something went wrong while authorizing access to user information.";
            } else {
                if(autoRefreshToken == null) {
                    autoRefreshToken = new AutoRefreshToken(userSessions, auth);
                }
                auth.requestToken(authCode, request.session().id());
            }
            return authCode;
        });
    }


    public void init(){
        hej();
        authingTest();
        spotify();
    }
}