package googleplayservice;

/**
 * Created by Bruker on 07.04.2018.
 */

public interface PlayServices {

    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement();
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}
