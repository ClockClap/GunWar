package xyz.n7mn.dev.gunwar.item;

import java.util.ArrayList;
import java.util.List;

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
