package SR.API;

import org.w3c.dom.Document;

//CHECKA STATIC INNAN AVSLUTNING!
import static spark.Spark.get;

public class SrAPI {

    private static final String path = "/api/sveriges-radio";
    private static final String domain = "http://api.sr.se/api/v2/playlists/rightnow?channelid=";

    public static String getSongs() {
        Object obj = new Object();
        get(path + "/getsongs/:channelid/:format", (request, response) -> {
            String format = request.params(":format").toLowerCase();
            System.out.println("FORMAT IS: " + format);
            return "Tjena";
        });
        return "Tjena";
    }

    public void init(){
        getSongs();
    }
}
