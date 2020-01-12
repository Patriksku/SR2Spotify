package SR.Beans;

/**
 * Object representation of the resource acquired from Sveriges Radio API.
 * This object contains information about the previous and the next song,
 * and is used in conjunction with radio stations that provide this information.
 * @author Patriksku
 */
public class Songs2Keys {

    private int channelid;
    private String channelname;

    private String title;
    private String description;
    private String artist;
    private String album;

    private String nexttitle;
    private String nextdescription;
    private String nextartist;
    private String nextalbum;

    public Songs2Keys() {}

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public int getChannelid() {
        return channelid;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getNexttitle() {
        return nexttitle;
    }

    public void setNexttitle(String nexttitle) {
        this.nexttitle = nexttitle;
    }

    public String getNextdescription() {
        return nextdescription;
    }

    public void setNextdescription(String nextdescription) {
        this.nextdescription = nextdescription;
    }

    public String getNextartist() {
        return nextartist;
    }

    public void setNextartist(String nextartist) {
        this.nextartist = nextartist;
    }

    public String getNextalbum() {
        return nextalbum;
    }

    public void setNextalbum(String nextalbum) {
        this.nextalbum = nextalbum;
    }
}