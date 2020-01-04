package Spotify.Beans;

/**
 * An object representing the unique session id that is used for
 * identifying the current user. This object is returned as JSON
 * whenever the client needs a user's session_id.
 * @author Patriksku
 */
public class SessionID {
    private String session_id;
    private boolean authorized_to_spotify;

    public SessionID() {}

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public boolean getAuthorized_to_spotify() {
        return authorized_to_spotify;
    }

    public void setAuthorized_to_spotify(boolean status) {
        this.authorized_to_spotify = status;
    }
}
