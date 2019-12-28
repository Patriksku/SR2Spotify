package Spotify.Beans;

/**
 * This class represents a Playlist-object, which is created
 * for a user based on their playlists in Spotify.
 * @author Patriksku
 */
public class Playlist {
    private String playlist_name;
    private String spotify_url;
    private String playlist_id;
    private String image_url;

    public Playlist() {}

    public String getPlaylist_name() {
        return playlist_name;
    }

    public void setPlaylist_name(String playlist_name) {
        this.playlist_name = playlist_name;
    }

    public String getSpotify_url() {
        return spotify_url;
    }

    public void setSpotify_url(String spotify_url) {
        this.spotify_url = spotify_url;
    }

    public String getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return playlist_name + "\n" + spotify_url + "\n" + playlist_id + "\n" + image_url + "\n";
    }
}
