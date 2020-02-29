package Spotify.Beans;

public class AllArray {
    private int amountOfAlbumItems;
    private Album[] arrayOfSearchAlbum;
    private int amountOfArtistItems;
    private Artist[] arrayOfSearch;
    private int amountOfPlaylists;
    private Playlist[] arrayOfPlaylists;
    private int amountOfTrackItems;
    private Track[] arrayOfSearchTrack;
    private String track_name;
    private String track_id;
    private String track_uri;
    private String track_url;
    private int track_number;
    private String artist_name;
    private String artist_id;
    private String artist_uri;
    private String artist_url;
    public String h = "<a href=\"https://api.spotify.com/v1/artists/{id}";
    public String h2 = "<a href=\"https://api.spotify.com/v1/album/{id}";// temporär lösning för track2
    public Artist artist_array[];

    public AllArray() {}

    public AllArray(int amountOfArtistItems) {

        this.amountOfArtistItems = amountOfArtistItems;
        arrayOfSearch = new Artist[amountOfArtistItems];

    }

    public int getAmountOfAlbumItems() {
        return amountOfAlbumItems;
    }

    public void setAmountOfAlbumItems(int amountOfAlbumItems) {
        this.amountOfAlbumItems = amountOfAlbumItems;
    }

    public Album[] getArrayOfAlbumItems() {
        return arrayOfSearchAlbum;
    }

    public void setArrayOfSearchAlbum(Album[] arrayOfSearchAlbum) {
        this.arrayOfSearchAlbum = arrayOfSearchAlbum;
    }


    public int getAmountOfArtistItems() {
        return amountOfArtistItems;
    }

    public void setAmountOfArtistItems(int amountOfArtistItems) {
        this.amountOfArtistItems = amountOfArtistItems;
    }

    public Artist[] getArrayOfSearch() {
        return arrayOfSearch;
    }

    public void setArrayOfSearch(Artist[] arrayOfSearch) {
        this.arrayOfSearch = arrayOfSearch;
    }


    public int getAmountOfPlaylists() {
        return amountOfPlaylists;
    }

    public void setAmountOfPlaylists(int amountOfPlaylists) {
        this.amountOfPlaylists = amountOfPlaylists;
    }

    public Playlist[] getArrayOfPlaylists() {
        return arrayOfPlaylists;
    }

    public void setArrayOfPlaylists(Playlist[] arrayOfPlaylists) {
        this.arrayOfPlaylists = arrayOfPlaylists;
    }

    public int getAmountOfTrackItems() {
        return amountOfTrackItems;
    }

    public void setAmountOfTrackItems(int amountOfTrackItems) {
        this.amountOfTrackItems = amountOfTrackItems;
    }

    public Track[] getArrayOfTrackItems() {
        return arrayOfSearchTrack;
    }

    public void setArrayOfSearchTrack(Track[] arrayOfSearchTrack) {
        this.arrayOfSearchTrack = arrayOfSearchTrack;
    }

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

    public String getH(){
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getH2(){
        return h2;
    }

    public void setH2(String h2) {
        this.h2 = h2;
    }

    public Artist[] getArtist_array(){
        return artist_array;
    }

    public void setArtist_array(Artist[] artist_array) {
        this.artist_array = artist_array;
    }

    public String toString() {
        return artist_name + "\n" + artist_uri ;
    }
}
