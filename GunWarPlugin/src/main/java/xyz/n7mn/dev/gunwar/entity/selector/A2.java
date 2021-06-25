package xyz.n7mn.dev.gunwar.entity.selector;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

final class A2 extends A1 {

    A2() {
        a1 = this;
    }

    CraftTargetSelector.Builder builder(TargetSelector.Type t, Player ps, Location source) {
        return CraftTargetSelector.builder(t, (CraftPlayer) ps, source);
    }

}
