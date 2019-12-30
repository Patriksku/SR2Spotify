package Spotify.Beans;

/**
 * This class represents an object that is converted to a JSON file when
 * checking if the current user has granted the application access to the user's Spotify account.
 * @author Patriksku
 */
public class VisitorStatus {
    private boolean status;

    public VisitorStatus() {}

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
