package Spotify.Beans;

public class AllArray {
    private int amountOfAlbumItems;
    private Album[] arrayOfSearchAlbum;
    private int amount;
    private Artist[] arrayOfSearchArtist;
    private int amountOfPlaylists;
    private Playlist[] arrayOfPlaylists;
    private int amountOfTrackItems;
    private Track[] arrayOfSearchTrack;
    private String artist_name;
    private String artist_id;
    private String artist_uri;
    private String artist_url;
    public String h = "<a href=\"https://api.spotify.com/v1/artists/{id}";
    public String h2 = "<a href=\"https://api.spotify.com/v1/album/{id}";// temporär lösning för track2
    public Artist artist_array[];
    private String album_name;
    private String album_id;
    private String album_uri;
    private String album_url;
    private String album_type;

    public AllArray() {}

    public AllArray(int amount) {

        this.amount = amount;
        arrayOfSearchArtist = new Artist[amount];

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


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Artist[] getArrayOfArtistItems() {
        return arrayOfSearchArtist;
    }

    public void setArrayOfSearchArtist(Artist[] arrayOfSearchArtist) {
        this.arrayOfSearchArtist = arrayOfSearchArtist;
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

    //temporär lösning för track2
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


    public String toString() {
        return artist_name + "\n" + artist_id + "\n" + artist_uri + "\n" + artist_url + "\n" + h + "\n" + h2 + "\n" + album_name + "\n" + album_id + "\n" + album_uri + "\n" + album_url + "\n" + artist_array + "\n";
    }


}
