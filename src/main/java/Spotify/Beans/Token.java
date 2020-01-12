package Spotify.Beans;

/**
 * This class represents a Token-object, which is created
 * for a user when granting access to Spotify account. It
 * contains all information needed for a user for token-management.
 * @author Patriksku
 */
public class Token {

    private String access_token;
    private String token_type;
    private int expires_in;
    private String refresh_token;
    private String scope;

    //Keeps track if user is active.
    private boolean active;
    private int activityActions = 0;

    public Token() {}

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isActive() {
        return active;
    }

    /**
     * Every time a user uses a function that needs to contact the Spotify API, this function
     * is called. A user is defined as "Active" by the server is the user has used three of
     * these functions in a time frame of 45 minutes. This functions logs of these events -
     * once the total number of these functions reaches three for a user, that user will
     * be labeled as "Active".
     */
    public void logActivityEvent() {
        if (!isActive()) {
            if (activityActions < 3) {
                activityActions++;
                if (activityActions == 3) {
                    System.out.println("User now qualifies for staying in the system for another time-unit.");
                    setActive(true);
                }
            }
        }
    }

    public void setActive(boolean active) {
        this.active = active;
        if (!active) {
            activityActions = 0;
        }
    }

    public String toString() {
        return "access_token: " + getAccess_token() + "\n" +
                "token_type: " + getToken_type() + "\n" +
                "expires_in: " + getExpires_in() + "\n" +
                "refresh_token: " + getRefresh_token() + "\n" +
                "scope: " + getScope() + "\n" +
                "active: " + isActive() + "\n" +
                "activity actions: " + activityActions + "\n";
    }
}