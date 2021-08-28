package com.github.clockclap.gunwar.util;

public class Angle {

    private final double yaw;
    private final double pitch;

    public Angle(double yaw, double pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }
}
