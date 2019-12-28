package Spotify.Functions;

import Spotify.Beans.Token;
import Spotify.Beans.User;
import Spotify.Users.UserSessions;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class creates a Thread that automatically refreshes the access-tokens
 * every 30 minutes of all users that currently exist in the server.
 * @author Patriksku
 */
public class AutoRefreshToken extends TimerTask {
    private Timer timer = new Timer(true);
    private Authentication auth;
    private UserSessions userSessions;

    public AutoRefreshToken(UserSessions userSessions, Authentication auth) {
        this.userSessions = userSessions;
        this.auth = auth;
        timer.scheduleAtFixedRate(this, 0, 3 * 6000); // 30 minutes
    }

    /**
     * Method automatically runs when this class is instantiated, that is,
     * when the first user is created server-side.
     */
    @Override
    public void run() {
        System.out.println("Timer task started at:" + new Date());
        refresh();
        System.out.println("Timer task finished at:" + new Date());
    }

    /**
     * Loops through every user and calls upon the refreshToken method in the
     * Authentication class, which refreshes access tokens of current user.
     * Thread rests for 3 seconds between each refresh.
     */
    private void refresh() {
        for (User user : userSessions.getHashMap().values()) {
            auth.refreshToken(user.getToken());
            try {
                Thread.sleep(3000); //Give thread 3 seconds of rest before every refresh.
                System.out.println("Thread slept well.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
