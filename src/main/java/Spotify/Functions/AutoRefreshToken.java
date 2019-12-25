package Spotify.Functions;

import Spotify.Beans.Token;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AutoRefreshToken extends TimerTask {
    private Timer timer = new Timer(true);
    private Authentication auth;
    private UserSessions userSessions;

    public AutoRefreshToken(UserSessions userSessions, Authentication auth) {
        this.userSessions = userSessions;
        this.auth = auth;
        timer.scheduleAtFixedRate(this, 0, 10 * 2000); // 20 sekunder?
    }

    @Override
    public void run() {
        System.out.println("Timer task started at:" + new Date());
        refresh();
        System.out.println("Timer task finished at:" + new Date());
    }

    private void refresh() {
        for (Token value : userSessions.getHashMap().values()) {
            auth.refreshToken(value);
            try {
                Thread.sleep(5000); //Give thread 5 seconds of rest before every refresh.
                System.out.println("Thread slept well.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
