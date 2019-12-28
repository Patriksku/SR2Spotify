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

    public String toString() {
        return "access_token: " + getAccess_token() + "\n" +
                "token_type: " + getToken_type() + "\n" +
                "expires_in: " + getExpires_in() + "\n" +
                "refresh_token: " + getRefresh_token() + "\n" +
                "scope: " + getScope() + "\n";
    }
}