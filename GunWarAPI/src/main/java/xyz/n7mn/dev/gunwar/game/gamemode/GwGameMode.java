package xyz.n7mn.dev.gunwar.game.gamemode;

public abstract class GwGameMode implements IGwGameMode {

    private final int index;
    private String name;
    private String displayName;
    private int gameTime;
    private int elapsedTime;

    protected GwGameMode(int index) {
        this.index = index;
    }

    public GwGameMode(int index, String name, String displayName, int gameTime) {
        this.index = index;
        this.name = name;
        this.displayName = displayName;
        this.gameTime = gameTime;
        this.elapsedTime = 0;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int getGameTime() {
        return gameTime;
    }

    @Override
    public int getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public int getRemainingTime() {
        return Math.max(0, gameTime - elapsedTime);
    }

    protected void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    protected void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
