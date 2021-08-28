package com.github.clockclap.gunwar.util;

import java.util.UUID;

public class GwUUID {

    public static boolean match(UUID uuid1, UUID uuid2) {
        return uuid1.toString().equalsIgnoreCase(uuid2.toString());
    }

}
