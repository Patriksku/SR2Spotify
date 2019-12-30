package Spotify.Functions;

import Spotify.Beans.VisitorStatus;
import Spotify.Handlers.FormatHandler;

/**
 * The only function of this class is to return a JSON-file with one object (one key/value pair),
 * which is true if the current user has granted the application access to the user's
 * Spotify account, and false otherwise.
 * @author Patriksku
 */
public class CheckVisitorStatus {

    /**
     * Returns JSON file based on if the current user has granted the
     * application access to the user's Spotify account.
     * @param status TRUE or FALSE
     * @return JSON
     */
    public String checkStatus(boolean status) {
        VisitorStatus visitorStatus = new VisitorStatus();
        FormatHandler formatHandler = new FormatHandler();
        if (status) {
            visitorStatus.setStatus(true);
        } else {
            visitorStatus.setStatus(false);
        }
        return formatHandler.getFormat(visitorStatus);
    }
}
