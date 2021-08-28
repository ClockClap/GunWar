package com.github.clockclap.gunwar.item;

public enum ClickAction {

    LEFT_CLICK(0, "LEFT_CLICK"),
    RIGHT_CLICK(1, "RIGHT_CLICK");

    private int index;
    private String name;

    private ClickAction(int index, String name) {
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
