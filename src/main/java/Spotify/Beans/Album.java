package Spotify.Beans;

/**
 * This class represents an Album object in Spotify.
 * @author Sara Karic
 */

public class Album extends Artist {
    private String album_name;
    private String album_id;
    private String album_uri;
    private String album_url;
    private String album_type;


    public Album(){}

    public String getAlbum_name(){
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_id(){
        return  album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_uri() {
        return album_uri;
    }

    public void setAlbum_uri(String album_uri) {
        this.album_uri = album_uri;
    }

    public String getAlbum_url() {
        return album_url;
    }

    public void setAlbum_url(String album_url) {
        this.album_url = album_url;
    }

    public String getAlbum_type() {
        return album_type;
    }

    public void setAlbum_type(String album_type) {
        this.album_type = album_type;
    }

    public Artist[] getArtist_array(){
        return artist_array;
    }

    public void setArtist_array(Artist[] artist_array) {
        this.artist_array = artist_array;
    }



    public String toString() {
        return album_name + "\n" + album_id + "\n" + album_uri + "\n" + album_url + "\n" + artist_array + "\n";
    }
}

