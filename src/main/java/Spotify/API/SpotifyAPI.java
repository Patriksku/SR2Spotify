package Spotify.API;

import Spotify.Functions.*;
import Spotify.Users.UserSessions;
import org.w3c.dom.Document;

import java.net.URLDecoder;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * This class is responsible for handling user requests to the Spoitfy API based
 * on available functionalities and returning JSON files when necessary.
 * @author Patriksku, Sara Karic
 */
public class SpotifyAPI {

    private final String path = "/api/spotify";
    private UserSessions userSessions = new UserSessions();
    private Authentication auth = new Authentication(userSessions);
    private UserPlaylists userPlaylists = new UserPlaylists(userSessions);
    private AutoRefreshToken autoRefreshToken = null;
    private UserProfile userProfile = new UserProfile(userSessions);
    private UserSessionID userSessionID = new UserSessionID(userSessions);
    private SearchSpotify searchSpotify = new SearchSpotify(userSessions);
    private final String SEARCH_ENDPOINT = "https://api.spotify.com/v1/search";


    /**
     * Authenticates the user by granting the user a login screen to Spotify.
     * @return response code, and a message if some error occurs.
     */
    public String authUser() {
        get(path + "/authuser", (request, response) -> {

            response.redirect(auth.getUserAuthToSpotify());
            response.status(202); //Accepted - Spotify handles the login process.

            return response.status();
        });
        return null;
    }

    /**
     * Returns true if the current user has granted the application access to Spotify,
     * otherwise false.
     * @return JSON with "status" set to true or false.
     */
    public String visitorStatus() {
        get(path + "/visitorstatus", (request, response) -> {

            response.type("application/json");
            CheckVisitorStatus checkVisitor = new CheckVisitorStatus();
            return checkVisitor.checkStatus(userSessions.contains(request.session().id()));
        });
        return null;
    }

    /**
     * The Callback or redirect URI where a user lands after a successful authentication.
     * If everything goes well, the user is matched with his token for later usage,
     * and a Thread is created which automatically refreshes access tokens every 30 minutes.
     * A profile-object is also created for the user with information from Spotify profile.
     */
    public void spotify() {
        get(path + "/login", (request, response) -> {

            String authCode;
            authCode = request.queryParams("code");

            if(authCode == null) {
                response.type("text/plain");
                response.status(403);
                return "Something went wrong while authorizing access to user information. Perhaps the user " +
                        "is already verified, or did not use the correct endpoint for verification.";
            } else {
                if(autoRefreshToken == null) {
                    autoRefreshToken = new AutoRefreshToken(userSessions, auth);
                }
                auth.requestToken(authCode, request.session().id());
                userProfile.createProfile(request.session().id(), response);
            }
            response.status(200);
            response.redirect("/");
            return response.status();
        });
    }

    /**
     * Returns information from the specified user's Spotify profile.
     * Note: The user needs to grant the application access first -
     * The client will get an information message if no access has been granted.
     * @return JSON-file which contains various fields from the user's Spotify profile.
     */
    public Document getProfile() {
        get(path + "/profile/:userid", (request, response) -> {

            String sessionOfUserID = userSessions.getUserID(request.params(":userid"));
            if (sessionOfUserID != null) {
                response.type("application/json");
                response.status(200);
                return userProfile.requestMyProfile(sessionOfUserID);
            } else {
                response.type("text/plain");
                response.status(401);
                return "User with ID: " + request.params(":userid") + " has not authorized " +
                        "access to Spotify.";
            }
        });
        return null;
    }

    /**
     * This method returns a JSON object containing the unique session_id of
     * the current user, together with a boolean which is True if the current user
     * has granted the application access to the user's Spotify account - otherwise False.
     * @return JSON
     */
    public String getSessionID() {
        get(path + "/session", (request, response) -> {

            String sessionID = userSessionID.getSessionID(request.session().id());
            if (sessionID.equalsIgnoreCase("Something went wrong while converting SessionID-object to JSON.")) {
                response.status(500);
                return sessionID;
            } else
                response.status(200);
            response.type("application/json");
            return sessionID;
        });
        return null;
    }

    /**
     * Returns the current user's information from Spotify if the user's unique
     * session-id exists in the server. If not, the user is redirected to the login-page
     * where the user may grant the application access to the user's information from Spotify.
     * @return JSON-file with the current user's information from Spotify.
     */
    public Document getMyProfile() {
        get(path + "/myprofile", (request, response) -> {

            if (userSessions.contains(request.session().id())) {
                response.type("application/json");
                return userProfile.requestMyProfile(request.session().id());
            }
            response.status(401);
            response.type("text/plain");
            return "An error occurred while trying to load your profile from Spotify. Please verify that you " +
                    "have granted the application access to your Spotify Account.";
        });
        return null;
    }

    /**
     * Returns JSON containing playlists from the specified user.
     * The user needs to have granted access to the user's Spotify information first.
     * @return JSON with all playlists.
     */
    public Document getPlaylists() {
        get(path + "/playlists/:userid", (request, response) -> {

            String sessionOfUserID = userSessions.getUserID(request.params(":userid"));
            if (sessionOfUserID != null) {
                response.type("application/json");
                return userPlaylists.requestMyPlaylist(sessionOfUserID, "50", "json");
            } else {
                response.status(401);
                return "User with ID: " + request.params(":userid") + " has not authorized " +
                        "access to Spotify.";
            }
        });
        return null;
    }

    /**
     * Returns playlists of the current user, based on the "limit" parameter.
     * Limit has to be a numerical digit between 0 and 50.
     * @return JSON containing playlists of the current user.
     */
    public Document getMyPlaylists() {
        get(path + "/myplaylists/:limit", (request, response) -> {

            String limit = request.params(":limit");
            if (Integer.parseInt(limit) > 50 || Integer.parseInt(limit) < 0) {
                response.status(400);
                response.type("text/plain");
                return "The 'limit' parameter has to be a numerical digit between 0 and 50.";
            }
            if (userSessions.contains(request.session().id())) {
                response.status(200);
                response.type("application/json");
                return userPlaylists.requestMyPlaylist(request.session().id(), limit, "json");
            } else {
                response.status(401);
                response.type("text/plain");
                return "This user has not granted access to Spotify.";
            }
        });
        return null;
    }

    /**
     * POST method for adding a song to the current user's playlist.
     * 3 Body parameters are needed in x-www-form-urlencoded format,
     * in the following order:
     * - session_id
     * - playlist_id
     * - song_uri
     * @return 200 OK if everything went fine, or 400 Bad Request
     * with an error message stating what went wrong.
     */
    public String addSongToPlaylist() {
        post(path + "/addsongplaylist", (request, response) -> {

            System.out.println("Request BODY:\n" + request.body());
            String body = request.body();

            //Splits the parameters accordingly to Spotify requirements.
            String[] pairs = body.split("&");
            String[] bodyParams = new String[3];
            System.out.println(pairs.length);
            try {
                for (int i = 0; i < pairs.length; i++) {
                    String[] eachParam = pairs[i].split("=");
                    String param = URLDecoder.decode(eachParam[1], "UTF-8");
                    bodyParams[i] = param;
                }
            } catch (ArrayIndexOutOfBoundsException aiobe) {
                response.status(400);
                response.type("text/plain");
                aiobe.getStackTrace();
                return "Something went wrong while adding song to playlist. Please check your parameters.";
            }

            AddSongToPlaylist addSong = new AddSongToPlaylist(userSessions);
            response.status();
            String status = addSong.addSongToPlaylist(bodyParams[0], bodyParams[1], bodyParams[2]);
            if (!status.equalsIgnoreCase("Successfully added song to playlist.")) {
                response.status(400);
                response.type("text/plain");
                return "Something went wrong while adding song to playlist. Please check your parameters.";
            }
            response.status(200);
            response.type("");
            return status;
        });
        return "Something went wrong while adding song to playlist. Please check your parameters.";
    }

    /**
     *
     * @return
     */
    public String getSearch(){
        get(path + "/getsearch/:search", ((request, response) -> {



            searchSpotify.requestSearch(request.params(":search"), request.session().id(), request.params("format"));
            response.type("text/plain");
            return request.params(":search");
        }));

        return null;
    }

    /**
     * Method initializes all methods in this class so that the endpoints are active.
     */
    public void init(){
        authUser();
        visitorStatus();
        spotify();
        getSessionID();
        getProfile();
        getMyProfile();
        getPlaylists();
        getMyPlaylists();
        addSongToPlaylist();
        getSearch();
    }
}
