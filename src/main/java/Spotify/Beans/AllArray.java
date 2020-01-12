package Spotify.Beans;

public class AllArray {
    private int amountOfAlbumItems;
    private Album[] arrayOfSearchAlbum;
    private int amountOfArtistItems;
    private Artist[] arrayOfSearchArtist;
    private int amountOfPlaylists;
    private Playlist[] arrayOfPlaylists;
    private int amountOfTrackItems;
    private Track[] arrayOfSearchTrack;

    public AllArray() {}

    public AllArray(int amountOfAlbumItems, int amountOfArtistItems, int amountOfPlaylists, int amountOfTrackItems) {
        this.amountOfAlbumItems = amountOfAlbumItems;
        arrayOfSearchAlbum = new Album[amountOfAlbumItems];
        this.amountOfArtistItems = amountOfArtistItems;
        arrayOfSearchArtist = new Artist[amountOfArtistItems];
        this.amountOfPlaylists = amountOfPlaylists;
        arrayOfPlaylists = new Playlist[amountOfPlaylists];
        this.amountOfTrackItems = amountOfTrackItems;
        arrayOfSearchTrack = new Track[amountOfTrackItems];
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
}

