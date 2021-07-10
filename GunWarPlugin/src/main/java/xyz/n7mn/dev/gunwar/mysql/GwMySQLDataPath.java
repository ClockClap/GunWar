package xyz.n7mn.dev.gunwar.mysql;

import java.io.Serializable;
import java.util.*;

public class GwMySQLDataPath implements Serializable {

    private transient static Set<GwMySQLDataPath> paths = new HashSet<>();

    public static Set<GwMySQLDataPath> pathSet() {
        return paths;
    }

    public static GwMySQLDataPath get(UUID id) {
        for(GwMySQLDataPath p : paths) {
            if(p.getUniqueId() == id) {
                return p;
            }
        }
        return null;
    }

    public static String getPath(UUID id) {
        if(get(id) != null) {
            return get(id).getPath();
        }
        return null;
    }

    public static GwMySQLDataPath insert(UUID id, String path) {
        GwMySQLDataPath p = new GwMySQLDataPath(id, path);
        paths.add(p);
        return p;
    }

    public static void delete(UUID id) {
        Set<GwMySQLDataPath> paths_ = new HashSet<>(paths);
        for(GwMySQLDataPath p : paths_) {
            if(p.getUniqueId() == id) {
                paths.remove(p);
                break;
            }
        }
    }

    private final UUID id;
    private String path;

    private GwMySQLDataPath(UUID id, String path) {
        this.id = id;
        this.path = path;
    }

    public UUID getUniqueId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
