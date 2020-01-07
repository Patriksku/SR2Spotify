package Spotify.Beans;

/**
 * This class represents an Artist object in Spotify.
 * @author Sara Karic
 */

public class Artist {
    private String artist_name;
    private String artist_id;
    private String artist_uri;
    private String artist_url;

    public Artist(){}

    public String getArtist_name(){
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getArtist_id(){
        return  artist_id;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public String getArtist_uri() {
        return artist_uri;
    }

    public void setArtist_uri(String artist_uri) {
        this.artist_uri = artist_uri;
    }

    public String getArtist_url() {
        return artist_url;
    }

    public void setArtist_url(String artist_url) {
        this.artist_url = artist_url;
    }

    public String toString() {
        return artist_name + "\n" + artist_id + "\n" + artist_uri + "\n" + artist_url + "\n";
    }
}



