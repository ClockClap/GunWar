package xyz.n7mn.dev.gunwar.entity.selector;

import org.bukkit.Location;
import org.bukkit.entity.Player;

abstract class A1 {

    static A1 a1;

    abstract TargetSelector.Builder builder(TargetSelector.Type t, Player ps, Location source);

}
