package com.github.clockclap.gunwar.nanamiserver.permission;

import java.util.UUID;

public class NanamiServerRoleData {

    private java.util.UUID uniqueId;
    private String id;
    private String name;
    private int rank;

    public NanamiServerRoleData(UUID uuid, String discordRoleID, String roleName, int roleRank){
        this.uniqueId = uuid;
        this.id = discordRoleID;
        this.name = roleName;
        this.rank = roleRank;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getDiscordRoleId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

}
