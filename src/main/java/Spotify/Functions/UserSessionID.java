package Spotify.Functions;

import Spotify.Beans.SessionID;
import Handlers.FormatHandler;
import Spotify.Users.UserSessions;

/**
 * This class is responsible for creating an object based on the
 * session_id supplied.
 * @author Patriksku
 */
public class UserSessionID {

    private UserSessions userSessions;

    public UserSessionID(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    /**
     * Returns a JSON-file two fields:
     * The session_id and a boolean authorized_to_spotify.
     * @param session_id of the current user.
     * @return JSON
     */
    public String getSessionID(String session_id) {
        SessionID sessionID = new SessionID();
        sessionID.setSession_id(session_id);
        sessionID.setAuthorized_to_spotify(userSessions.contains(session_id));

        FormatHandler formatHandler = new FormatHandler();
        return formatHandler.getFormat(sessionID);
    }
}
