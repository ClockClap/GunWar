package com.github.clockclap.gunwar.game.data;

public interface KnifeData extends ItemData {

    public boolean canThrow();

    public void throwKnife();

    public void cancelThrowingCooldown();

}
