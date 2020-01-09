package Spotify.API;

import CORS.CORS;
import Spotify.Functions.*;
import Spotify.Users.UserSessions;
import org.w3c.dom.Document;

import java.net.URLDecoder;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * This class is responsible for handling user requests to the Spoitfy API based
 * on available functionalities and returning JSON files when necessary.
 * @author Patriksku
 */
public class SpotifyAPI {

    private final String path = "/api/spotify";
    private UserSessions userSessions = new UserSessions();
    private Authentication auth = new Authentication(userSessions);
    private UserPlaylists userPlaylists = new UserPlaylists(userSessions);
    private AutoRefreshToken autoRefreshToken = null;
    private UserProfile userProfile = new UserProfile(userSessions);
    private CORS cors = new CORS();

    /**
     * Authenticates the user by granting the user a login screen to Spotify.
     * @return response code, and a message if some error occurs.
     */
    public String authUser() {
        get(path + "/authuser", (request, response) -> {
            cors.addSupport(request, response);

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
            cors.addSupport(request, response);

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
            cors.addSupport(request, response);

            String authCode;
            authCode = request.queryParams("code");

            if(authCode == null) {

                return "Something went wrong while authorizing access to user information. Perhaps the user " +
                        "is already verified, or did not use the correct endpoint for verification.";
            } else {
                if(autoRefreshToken == null) {
                    autoRefreshToken = new AutoRefreshToken(userSessions, auth);
                }
                auth.requestToken(authCode, request.session().id());
                userProfile.createProfile(request.session().id());
            }
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
        get(path + "/getprofile/:userid", (request, response) -> {
            cors.addSupport(request, response);

            String sessionOfUserID = userSessions.getUserID(request.params(":userid"));
            if (sessionOfUserID != null) {
                response.type("application/json");
                return userProfile.requestMyProfile(sessionOfUserID, "json");
            } else {
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
        get(path + "/getsession", (request, response) -> {
            cors.addSupport(request, response);

            UserSessionID userSessionID = new UserSessionID(userSessions);

            String sessionResponse = userSessionID.getSessionID(request.session().id());
            if (sessionResponse.equalsIgnoreCase("Something went wrong while converting SessionID-object to JSON.")) {
                response.status(500);
                return sessionResponse;
            } else
                response.status(200);
            response.type("application/json");
            return sessionResponse;
        });
        return "Something went wrong while converting SessionID-object to JSON.";
    }

    /**
     * Returns the current user's information from Spotify if the user's unique
     * session-id exists in the server. If not, the user is redirected to the login-page
     * where the user may grant the application access to the user's information from Spotify.
     * @return JSON-file with the current user's information from Spotify.
     */
    public Document getMyProfile() {
        get(path + "/getmyprofile", (request, response) -> {
            cors.addSupport(request, response);

            if (userSessions.contains(request.session().id())) {
                response.type("application/json");
                return userProfile.requestMyProfile(request.session().id(), "json");
            } else {
                response.redirect(path + "/authuser");
            }
            return "An error occurred while trying to load your profile from Spotify.";
        });
        return null;
    }

    /**
     * Returns the current user's information from Spotify, if the user's unique
     * session-id exists in the server. If not, the user is redirected to the login-page
     * where the user may grant access to the user's information from Spotify.
     * @return JSON or XML based on the parameter specified.e
     */
    public Document getMyProfileFormat() {
        get(path + "/getmyprofile/:format", (request, response) -> {
            cors.addSupport(request, response);

            if (userSessions.contains(request.session().id())) {
                if (request.params(":format").equalsIgnoreCase("json")) {
                    response.type("application/json");
                    return userProfile.requestMyProfile(request.session().id(), "json");

                } else if (request.params(":format").equalsIgnoreCase("xml")) {
                    response.type("application/xml");
                    return userProfile.requestMyProfile(request.session().id(), "xml");
                } else {
                    return "Something went wrong. Please check ur parameters. Only XML and JSON is valid.";
                }

            } else {
                response.redirect(path + "/authuser");
            }
            return "An error occurred while trying to load your profile from Spotify.";
        });
        return null;
    }

    /**
     * Returns JSON containing playlists from the specified user.
     * The user needs to have granted access to the user's Spotify information first.
     * @return JSON with all playlists.
     */
    public Document getPlaylists() {
        get(path + "/getplaylists/:userid", (request, response) -> {
            cors.addSupport(request, response);

            String sessionOfUserID = userSessions.getUserID(request.params(":userid"));
            if (sessionOfUserID != null) {
                response.type("application/json");
                return userPlaylists.requestMyPlaylist(request.session().id(), "50", "json");
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
        get(path + "/getmyplaylists/:limit", (request, response) -> {
            cors.addSupport(request, response);

            String limit = request.params(":limit");
            if (Integer.parseInt(limit) > 50 || Integer.parseInt(limit) < 0) {
                return "The 'limit' parameter has to be a numerical digit between 0 and 50.";
            }
            if (userSessions.contains(request.session().id())) {
                response.type("application/json");
                return userPlaylists.requestMyPlaylist(request.session().id(), limit, "json");
            } else {
                response.redirect(path + "/authuser");
            }
            return "An error has occurred. Please check your parameters, path, or if the user is authorized to Spotify.";
        });
        return null;
    }

    /**
     * Returns playlists of the current user from Spotify.
     * The user will be redirected to a login page where access can be granted,
     * if this has not been made before.
     * @return XML or JSON with all playlists.
     */
    public Document getMyPlaylistsFormat() {
        get(path + "/getmyplaylists/:limit/:format", (request, response) -> {
            cors.addSupport(request, response);

            String limit = request.params(":limit");
            if (Integer.parseInt(limit) > 50 || Integer.parseInt(limit) < 0) {
                return "The 'limit' parameter has to be a numerical digit between 0 and 50.";
            }
            if (userSessions.contains(request.session().id())) {
                if (request.params(":format").equalsIgnoreCase("json")) {
                    response.type("application/json");
                    return userPlaylists.requestMyPlaylist(request.session().id(), limit, "json");

                } else if (request.params(":format").equalsIgnoreCase("xml")) {
                    response.type("application/xml");
                    return userPlaylists.requestMyPlaylist(request.session().id(), limit, "xml");

                } else {
                    return "Something went wrong. Please check ur parameters. Only XML and JSON is valid.";
                }

            } else {
                response.redirect(path + "/authuser");
            }

            return "An error occurred while trying to load your Playlists from Spotify.";
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
            cors.addSupport(request, response);

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
                aiobe.getStackTrace();
                return "Something went wrong while adding song to playlist. Please check your parameters.";
            }

            AddSongToPlaylist addSong = new AddSongToPlaylist(userSessions);
            response.status();
            String status = addSong.addSongToPlaylist(bodyParams[0], bodyParams[1], bodyParams[2]);
            if (!status.equalsIgnoreCase("Successfully added song to playlist.")) {
                response.status(400);
            }
            return status;
        });
        return "Something went wrong while adding song to playlist. Please check your parameters.";
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
        getMyProfileFormat();
        getPlaylists();
        getMyPlaylists();
        getMyPlaylistsFormat();
        addSongToPlaylist();
    }
}