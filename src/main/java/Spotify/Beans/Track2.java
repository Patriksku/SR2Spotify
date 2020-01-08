package Spotify.Beans;

/**
 * This class represents a Track object in Spotify.
 * @author Sara Karic
 */

public class Track2 extends Artist {
    private String track_name;
    private String track_id;
    private String track_uri;
    private String track_url;
    private int track_number;
// specife which album the track is on
    //specife an array of artists

    public Track2(){}

    public String getTrack_name(){
        return track_name;
    }

    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }

    public String getTrack_id(){
        return  track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
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

    public int getTrack_number() {
        return track_number;
    }

    public void setTrack_number(int track_number) {
        this.track_number = track_number;
    }


    public String toString() {
        return track_name + "\n" + track_id + "\n" + track_uri + "\n" + track_url + "\n" + track_number + "\n";
    }
}

