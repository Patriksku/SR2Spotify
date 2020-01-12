package Spotify.Beans;

/**
 * This class represents a User-object, which contains a Token-object
 * and a Profile-object. These are created when a user grants access to
 * the user's Spotify account.
 * @author Patriksku
 */
public class User {
    private Token token;
    private Profile profile;

    public User() {}

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}

