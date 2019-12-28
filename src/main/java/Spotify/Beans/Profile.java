package Spotify.Beans;

/**
 * This class represents a Profile-object, which is created
 * for a user based on information received from Spotify API.
 * @author Patriksku
 */
public class Profile {
    private String display_name;
    private String user_id;
    private String profile_url;
    private String image_url;
    private String session_id;

    public Profile() {}

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    @Override
    public String toString() {
        return "Profile\n" +
                "Display name " + display_name + "\n" +
                "User ID " + user_id + "\n" +
                "Profile URL " + profile_url + "\n" +
                "Image URL " + image_url + "\n" +
                "Session ID " + session_id + "\n";
    }
}
