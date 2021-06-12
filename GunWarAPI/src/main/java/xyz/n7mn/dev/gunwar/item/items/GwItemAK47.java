package xyz.n7mn.dev.gunwar.item.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import xyz.n7mn.dev.gunwar.item.GwGunBase;
import xyz.n7mn.dev.gunwar.item.GwGunType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GwItemAK47 extends GwGunBase {

    public GwItemAK47() {
        super(0, Material.IRON_HOE, "AK47", "AK-47", "0f00a46e", Arrays.asList("" +
                        ChatColor.WHITE + "初期武器のAK-47。ソ連が開発したアサルトライフル。攻撃力や精度、ノックバックなどから考えて割と使いやすい。"),
                10F, 13F, 30, 30, 0.6F, 0F, 100L,
                4L, 30F, 50F, 300F, 1.5F, GwGunType.ASSAULT_RIFLE);
    }

}
