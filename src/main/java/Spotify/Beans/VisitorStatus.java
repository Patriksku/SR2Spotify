package Spotify.Beans;

/**
 * This class represents an object that is converted to a JSON file when
 * checking if the current user has granted the application access to the user's Spotify account.
 * @author Patriksku
 */
public class VisitorStatus {
    private boolean authorized_to_spotify;

    public VisitorStatus() {}

    public boolean isStatus() {
        return authorized_to_spotify;
    }

    public void setStatus(boolean status) {
        this.authorized_to_spotify = status;
    }
}
