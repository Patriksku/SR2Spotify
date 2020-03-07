package Spotify.API;

import Lyrics.Functions.StringSimplifier;
import SR.Beans.Radio;
import SR.Functions.ChannelSongs;
import Spotify.Authentication.Authentication;
import Spotify.Authentication.AutoSessionManager;
import Spotify.Beans.AllArray;
import Spotify.Functions.*;
import Spotify.Users.UserSessions;
import org.json.JSONException;
import org.w3c.dom.Document;
import java.net.URLDecoder;


import static spark.Spark.get;
import static spark.Spark.post;

/**
 * This class is responsible for handling user requests to the Spoitfy API based
 * on available functionalities and returning JSON files when necessary.
 * @author Patriksku, Sara Karic
 */
public class SpotifyAPI extends SearchSpotify {

    private final String path = "/api/v1/spotify";
    private UserSessions userSessions = new UserSessions();
    private Authentication auth = new Authentication(userSessions);
    private UserPlaylists userPlaylists = new UserPlaylists(userSessions);
    private AutoSessionManager autoSessionManager = null;
    private UserProfile userProfile = new UserProfile(userSessions);
    private UserSessionID userSessionID = new UserSessionID(userSessions);
    private SearchSpotify searchSpotify = new SearchSpotify(userSessions);
    private AddSongToPlaylist addSong = new AddSongToPlaylist(userSessions);

    public SpotifyAPI(UserSessions userSessions) {
        super(userSessions);
    }

    public SpotifyAPI() {
        super();
    }

    /**
     * Authenticates the user by granting the user a login screen to Spotify.
     */
    public void authUser() {
        get(path + "/authuser", (request, response) -> {

            response.redirect(auth.getUserAuthToSpotify());
            response.status(200);
            response.type("");

            return response.status();
        });
    }

    /**
     * Returns true if the current user has granted the application access to Spotify,
     * otherwise false.
     *
     * @return JSON with "status" set to true or false.
     */
    public String visitorStatus() {
        get(path + "/visitorstatus", (request, response) -> {

            response.status(200);
            response.type("application/json");
            CheckVisitorStatus checkVisitor = new CheckVisitorStatus();
            return checkVisitor.checkStatus(userSessions.contains(request.session().id()));
        });
        return null;
    }

    /**
     * This method returns a JSON object containing the unique session_id of
     * the current user together with a boolean which is True if the current user
     * has granted the application access to the user's Spotify account - otherwise False.
     *
     * @return JSON
     */
    public Document getSessionID() {
        get(path + "/session", (request, response) -> {

            response.status(200);
            response.type("application/json");
            return userSessionID.getSessionID(request.session().id());
        });
        return null;
    }

    /**
     * The Callback or redirect URI where a user lands after a successful authentication.
     * If everything goes well, the user is matched with his token for later usage.
     * A profile-object is also created for the user with information from Spotify profile.
     */
    public void spotify() {
        get(path + "/login", (request, response) -> {

            String authCode;
            authCode = request.queryParams("code");

            if (request.queryParams().contains("error")) {
                response.type("text/plain");
                response.status(401);
                return "The user chose not to authorize the application to the user's Spotify account.";
            } else if (authCode == null) {
                response.type("text/plain");
                response.status(403);
                return "This endpoint was used for other purposes than for the verification process by the API itself. " +
                        "Do not use this endpoint on your own - the API handles this redirect automatically.";
            } else {
                if (autoSessionManager == null) {
                    autoSessionManager = new AutoSessionManager(userSessions);
                }
                auth.requestToken(authCode, request.session().id());
                userProfile.createProfile(request.session().id(), response);
            }
            response.status(200);
            response.type("text/plain");
            response.redirect("/");
            return "User was successfully authorized to Spotify and will be redirected to the homepage [/].";
        });
    }

    /**
     * Returns information from the specified user's Spotify profile if authentication has been granted.
     *
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
                        "access to the user's Spotify.";
            }
        });
        return null;
    }

    /**
     * Returns the current user's information from Spotify if the user's unique
     * session-id exists in the server.
     *
     * @return JSON-file with the current user's information from Spotify.
     */
    public Document getMyProfile() {
        get(path + "/myprofile", (request, response) -> {

            if (userSessions.contains(request.session().id())) {
                response.status(200);
                response.type("application/json");
                auth.refreshToken(userSessions.get(request.session().id()).getToken());
                return userProfile.requestMyProfile(request.session().id());
            }
            response.status(401);
            response.type("text/plain");
            return "An error occurred while trying to load the user's profile from Spotify. Please verify that the user " +
                    "has granted the application access to the user's Spotify Account.";
        });
        return null;
    }

    /**
     * Returns JSON containing playlists from the specified user.
     * The user needs to have granted access to the user's Spotify information first.
     *
     * @return JSON with all playlists.
     */
    public Document getPlaylists() {
        get(path + "/playlists/:userid", (request, response) -> {

            String sessionOfUserID = userSessions.getUserID(request.params(":userid"));
            if (sessionOfUserID != null) {
                response.status(200);
                response.type("application/json");
                return userPlaylists.requestMyPlaylist(sessionOfUserID, "50");
            } else {
                response.status(401);
                response.type("text/plain");
                return "User with ID: " + request.params(":userid") + " has not authorized " +
                        "access to Spotify.";
            }
        });
        return null;
    }

    /**
     * Returns playlists of the current user, based on the "limit" parameter.
     * Limit has to be a numerical digit between 0 and 50.
     *
     * @return JSON containing playlists of the current user.
     */
    public Document getMyPlaylists() {
        get(path + "/myplaylists/:limit", (request, response) -> {

            String limit = request.params(":limit");
            try {
                if (Integer.parseInt(limit) < 0 || Integer.parseInt(limit) > 50) {
                    response.status(400);
                    response.type("text/plain");
                    return "The 'limit' parameter has to be a numerical digit between 0 and 50.";
                }
                if (userSessions.contains(request.session().id())) {
                    response.status(200);
                    response.type("application/json");
                    auth.refreshToken(userSessions.get(request.session().id()).getToken());
                    return userPlaylists.requestMyPlaylist(request.session().id(), limit);
                } else {
                    response.status(401);
                    response.type("text/plain");
                    return "This user has not granted access to Spotify.";
                }
            } catch (NumberFormatException e) {
                response.status(400);
                response.type("text/plain");
                return "The 'limit' parameter has to be a numerical digit between 0 and 50.";
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
     *
     * @return 200 OK if everything went fine, or 400 Bad Request
     * with an error message stating what went wrong.
     */
    public String addSongToPlaylist() {
        post(path + "/addsongplaylist", (request, response) -> {

            /*System.out.println("Request BODY:\n" + request.body());*/
            String body = request.body();

            //Splits the parameters accordingly to Spotify requirements.
            String[] pairs = body.split("&");
            String[] bodyParams = new String[3];
            try {
                for (int i = 0; i < pairs.length; i++) {
                    String[] eachParam = pairs[i].split("=");
                    String param = URLDecoder.decode(eachParam[1], "UTF-8");
                    bodyParams[i] = param;
                }
            } catch (ArrayIndexOutOfBoundsException aiobe) {
                response.status(400);
                response.type("text/plain");
                return "Something went wrong while adding song to playlist. Please check your parameters.";
            }

            if (userSessions.contains(bodyParams[0])) {
                auth.refreshToken(userSessions.get(bodyParams[0]).getToken());
                String status = addSong.addSongToPlaylist(bodyParams[0], bodyParams[1], bodyParams[2]);
                if (!status.equalsIgnoreCase("Successfully added song to playlist.")) {
                    response.status(400);
                    response.type("text/plain");
                    return "Something went wrong while adding song to playlist. Please check your parameters.";
                } else {
                    response.status(201);
                    response.type("text/plain");
                    return status;
                }
            } else {
                response.status(401);
                response.type("text/plain");
                return "This user is currently not authorized to Spotify. Please grant the application access to your Spotify account" +
                        " if you wish to use this feature.";
            }
        });
        return "Something went wrong while adding song to playlist. Please check your parameters.";
    }

    /**
     * Returns information about album, artist, playlist and track.
     *
     * @return search
     */

    public String getSearch() {
        get(path + "/getsearch", (request, response) -> {
            Radio radio = ChannelSongs.getCurrentRadio();
            AllArray allArray = new AllArray();
            StringSimplifier simply = new StringSimplifier();
            // SearchSpotify SRartist = new SearchSpotify(SR);

            try {


                if (userSessions.contains(request.session().id())) {
                    response.status(200);
                    response.type("application/json");
                    auth.refreshToken(userSessions.get(request.session().id()).getToken());
                    return searchSpotify.requestSearch(simply.simplyString(radio.getTitle()), request.session().id());

                } else if (!userSessions.contains(request.session().id())) {
                    response.status(401);
                    response.type("text/plain");
                    return "Please authorize to Spotify";

                }


            } catch (NullPointerException e) {
                response.status(400);
                response.type("text/plain");
                return "Please select channel";

            } catch (JSONException j) {
                response.status(204);
                response.type("");
                return "";

            }

            return null;

        });

        return null;
    }


    /**
     * Method initializes all methods in this class so that the endpoints are active.
     */
    public void init() {
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