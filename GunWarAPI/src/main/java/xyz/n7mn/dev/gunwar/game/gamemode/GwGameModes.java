package xyz.n7mn.dev.gunwar.game.gamemode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class GwGameModes {

    private static List<GwGameMode> registeredGameMode = new ArrayList<>();

    public static GwGameMode NORMAL = new GameModeNormal();
    public static GwGameMode GENERAL_SIEGE = new GameModeGeneralSiege();
    public static GwGameMode ZOMBIE_ESCAPE = new GameModeZombieEscape();

    public static Collection<GwGameMode> getRegisteredGameModes() {
        return registeredGameMode;
    }

    public static void register(GwGameMode gamemode) {
        if(registeredGameMode != null && registeredGameMode.size() > 0 && !registeredGameMode.contains(gamemode)) {
            List<GwGameMode> modeList = new ArrayList<>(registeredGameMode);
            for(GwGameMode mode : modeList) if(mode.getIndex() == gamemode.getIndex() || mode.getName().equalsIgnoreCase(gamemode.getName())) return;
            registeredGameMode.add(gamemode);
        }
    }

    public static void unregister(GwGameMode gamemode) {
        registeredGameMode.remove(gamemode);
    }

    public static void unregister(int index) {
        if(registeredGameMode != null && registeredGameMode.size() > 0) {
            List<GwGameMode> modeList = new ArrayList<>(registeredGameMode);
            for(GwGameMode mode : modeList) {
                if (mode.getIndex() == index) {
                    registeredGameMode.remove(mode);
                }
            }
        }
    }

    public static void unregister(String name) {
        if(registeredGameMode != null && registeredGameMode.size() > 0) {
            List<GwGameMode> modeList = new ArrayList<>(registeredGameMode);
            for(GwGameMode mode : modeList) {
                if (mode.getName().equalsIgnoreCase(name)) {
                    registeredGameMode.remove(mode);
                }
            }
        }
    }

    public static void unregisterAll(Collection<? extends GwGameMode> gamemodes) {
        registeredGameMode.removeAll(gamemodes);
    }

    public static void registerDefault() {
        register(NORMAL);
        register(ZOMBIE_ESCAPE);
        register(GENERAL_SIEGE);
    }

    public static void clear() {
        unregisterAll(new ArrayList<>(registeredGameMode));
    }

}
