package SR.Beans;

/**
 * Object representation of the resource acquired from Sveriges Radio API.
 * This object contains information about the previous, current and the next song,
 * and is used in conjunction with radio stations that provide this information.
 * @author Patriksku
 */
public class Songs3Keys {

    private int channelid;
    private String channelname;

    private String previoustitle;
    private String previousdescription;
    private String previousartist;
    private String previousalbum;

    private String currenttitle;
    private String currentdescription;
    private String currentartist;
    private String currentalbum;

    private String nexttitle;
    private String nextdescription;
    private String nextartist;
    private String nextalbum;

    public Songs3Keys() {}

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
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

    public String getCurrenttitle() {
        return currenttitle;
    }

    public void setCurrenttitle(String currenttitle) {
        this.currenttitle = currenttitle;
    }

    public String getCurrentdescription() {
        return currentdescription;
    }

    public void setCurrentdescription(String currentdescription) {
        this.currentdescription = currentdescription;
    }

    public String getCurrentartist() {
        return currentartist;
    }

    public void setCurrentartist(String currentartist) {
        this.currentartist = currentartist;
    }

    public String getCurrentalbum() {
        return currentalbum;
    }

    public void setCurrentalbum(String currentalbum) {
        this.currentalbum = currentalbum;
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