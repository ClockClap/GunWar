package com.github.clockclap.gunwar.item;

public enum GwGunType {

    ASSAULT_RIFLE(0, "ASSAULT_RIFLE"),
    SNIPER_RIFLE(1, "SNIPER_RIFLE"),
    SUBMACHINE_GUN(2, "SUBMACHINE_GUN"),
    SHOTGUN(3, "SHOTGUN"),
    HAND_GUN(4, "HAND_GUN"),
    ORIGIN(5, "ORIGIN");

    private int index;
    private String name;

    private GwGunType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

}
