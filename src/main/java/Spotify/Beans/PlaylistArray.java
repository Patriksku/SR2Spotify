package Spotify.Beans;

/**
 * This class represents a PlaylistArray-object which contains
 * several playlists in an array, As a user usually has more than one
 * playlist.
 * @author Patriksku
 */
public class PlaylistArray {
    private int amountOfPlaylists;
    private Playlist[] arrayOfPlaylists;

    public PlaylistArray() {}

    public PlaylistArray(int amountOfPlaylists) {
        this.amountOfPlaylists = amountOfPlaylists;
        arrayOfPlaylists = new Playlist[amountOfPlaylists];
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
}
