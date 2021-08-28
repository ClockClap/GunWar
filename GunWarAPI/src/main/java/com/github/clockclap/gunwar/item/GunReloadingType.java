package com.github.clockclap.gunwar.item;

public enum GunReloadingType {

    ONCE(0),
    SINGLE(1);

    private int id;

    private GunReloadingType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
