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

    private String previoustitle;
    private String previousdescription;
    private String previousartist;
    private String previousalbum;

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

    public String getPrevioustitle() {
        return previoustitle;
    }

    public void setPrevioustitle(String previoustitle) {
        this.previoustitle = previoustitle;
    }

    public String getPreviousdescription() {
        return previousdescription;
    }

    public void setPreviousdescription(String previousdescription) {
        this.previousdescription = previousdescription;
    }

    public String getPreviousartist() {
        return previousartist;
    }

    public void setPreviousartist(String previousartist) {
        this.previousartist = previousartist;
    }

    public String getPreviousalbum() {
        return previousalbum;
    }

    public void setPreviousalbum(String previousalbum) {
        this.previousalbum = previousalbum;
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