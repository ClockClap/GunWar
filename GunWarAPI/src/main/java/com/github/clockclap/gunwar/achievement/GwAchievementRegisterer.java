package com.github.clockclap.gunwar.achievement;

import java.util.*;

public final class GwAchievementRegisterer {

    private static Map<Integer, GwAchievement> achievementMap = new HashMap<>();

    public static List<GwAchievement> toList() {
        List<GwAchievement> list = new ArrayList<>();
        for(Map.Entry<Integer, GwAchievement> entry : achievementMap.entrySet()) list.add(entry.getValue());
        return list;
    }

    public static GwAchievement get(int id) {
        return achievementMap.get(id);
    }

    public static GwAchievement get(String name) {
        for(Map.Entry<Integer, GwAchievement> entry : achievementMap.entrySet())
            if(entry.getValue().getName().equalsIgnoreCase(name)) return entry.getValue();
        return null;
    }

    public static <T extends GwAchievement> T get(Class<T> achievement) {
        for(Map.Entry<Integer, GwAchievement> entry : achievementMap.entrySet())
            if(achievement.isInstance(entry.getValue())) return achievement.cast(entry.getValue());
        return null;
    }

    public static int getId(GwAchievement achievement) {
        if(achievementMap.containsValue(achievement)) for(Map.Entry<Integer, GwAchievement> entry : achievementMap.entrySet())
            if(entry.getValue() == achievement) return entry.getKey();
        return -1;
    }

    public static void register(int id, GwAchievement achivement) {
        if(!achievementMap.containsKey(id) && !achievementMap.containsValue(achivement)) achievementMap.put(id, achivement);
    }

    public static void register(GwAchievement achievement) {
        if(!achievementMap.containsValue(achievement)) {
            List<Integer> l = new ArrayList<>();
            for(Map.Entry<Integer, GwAchievement> entry : achievementMap.entrySet()) l.add(entry.getKey());
            int id = Collections.max(l);
            achievementMap.put(id, achievement);
        }
    }

    public static void unregister(GwAchievement achievement) {
        int id = getId(achievement);
        if(id >= 0) {
            Map<Integer, GwAchievement> map = new HashMap<>(achievementMap);
            for (Map.Entry<Integer, GwAchievement> entry : map.entrySet()) {
                if(entry.getKey() == id) {
                    achievementMap.remove(id);
                }
            }
        }
    }

    public static void unregister(int id) {
        if(id >= 0) {
            Map<Integer, GwAchievement> map = new HashMap<>(achievementMap);
            for (Map.Entry<Integer, GwAchievement> entry : map.entrySet()) {
                if(entry.getKey() == id) {
                    achievementMap.remove(id);
                }
            }
        }
    }

    public static void clear() {
        achievementMap.clear();
    }

}
