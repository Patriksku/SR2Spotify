package Spotify.Beans;

public class AllArray {

    private String track_name;
    private String track_uri;
    private String track_url;
    private String artist_name;
    private String artist_uri;
    private String artist_url;

    public AllArray() {}

    public String getTrack_name(){
        return track_name;
    }

    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }


    public String getTrack_uri() {
        return track_uri;
    }

    public void setTrack_uri(String track_uri) {
        this.track_uri = track_uri;
    }

    public String getTrack_url() {
        return track_url;
    }

    public void setTrack_url(String track_url) {
        this.track_url = track_url;
    }

    public String getArtist_name(){
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getArtist_uri() {
        return artist_uri;
    }

    public void setArtist_uri(String artist_uri) {
        this.artist_uri = artist_uri;
    }

    public String toString() {
        return artist_name + "\n" + artist_uri ;
    }
}
