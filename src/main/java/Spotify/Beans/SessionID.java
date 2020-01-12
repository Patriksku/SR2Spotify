package Spotify.Beans;

/**
 * An object representing the unique session id of a user and if the user has granted
 * the application access to its Spotify account, based on a boolean value.
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
