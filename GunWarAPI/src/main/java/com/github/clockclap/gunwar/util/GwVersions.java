package com.github.clockclap.gunwar.util;

public final class GwVersions {

    public static String max(String val1, String val2) {
        String[] strs1 = val1.split("\\.");
        String[] strs2 = val2.split("\\.");
        for(String str1 : strs1) {
            for(String str2 : strs2) {
                try {
                    int v = Integer.parseInt(str1);
                    int c = Integer.parseInt(str2);
                    if(v > c) return val1;
                    if(c > v) return val2;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return val1;
    }

    public static String min(String val1, String val2) {
        String[] strs1 = val1.split("\\.");
        String[] strs2 = val2.split("\\.");
        for(String str1 : strs1) {
            for(String str2 : strs2) {
                try {
                    int v = Integer.parseInt(str1);
                    int c = Integer.parseInt(str2);
                    if(v > c) return val2;
                    if(c > v) return val1;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return val1;
    }

    public static boolean greaterThanVal1(String val1, String val2) {
        String[] strs1 = val1.split("\\.");
        String[] strs2 = val2.split("\\.");
        for(String str1 : strs1) {
            for(String str2 : strs2) {
                try {
                    int v = Integer.parseInt(str1);
                    int c = Integer.parseInt(str2);
                    if(v > c) return false;
                    if(c > v) return true;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return false;
    }

    public static boolean lessThanVal1(String val1, String val2) {
        String[] strs1 = val1.split("\\.");
        String[] strs2 = val2.split("\\.");
        for(String str1 : strs1) {
            for(String str2 : strs2) {
                try {
                    int v = Integer.parseInt(str1);
                    int c = Integer.parseInt(str2);
                    if(v > c) return true;
                    if(c > v) return false;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return false;
    }

    public static boolean lessThanOrEqualsVal1(String val1, String val2) {
        return lessThanVal1(val1, val2) || val1.equalsIgnoreCase(val2);
    }

    public static boolean greaterThanOrEqualsVal1(String val1, String val2) {
        return greaterThanVal1(val1, val2) || val1.equalsIgnoreCase(val2);
    }

    public static boolean equals(String val1, String val2) {
        return val1.equalsIgnoreCase(val2);
    }

}
