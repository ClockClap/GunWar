package xyz.n7mn.dev.gunwar.game.gamemode;

public abstract class GwGameMode implements IGwGameMode {

    private final int index;
    private String name;
    private String displayName;

    protected GwGameMode(int index) {
        this.index = index;
    }

    protected GwGameMode(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public GwGameMode(int index, String name, String displayName) {
        this.index = index;
        this.name = name;
        this.displayName = displayName;
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

    protected void setName(String name) {
        this.name = name;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
