/*
 * Copyright (c) 2021, ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.github.clockclap.gunwar.game.data;

import com.github.clockclap.gunwar.GwAPI;
import com.github.clockclap.gunwar.entity.HitEntity;
import com.github.clockclap.gunwar.item.GwGunItem;
import com.github.clockclap.gunwar.item.GwItem;
import com.github.clockclap.gunwar.item.GwKnifeItem;
import com.github.clockclap.gunwar.util.Angle;
import com.github.clockclap.gunwar.util.PlayerWatcher;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;

@GwAPI
public interface PlayerData extends EntityData {

    public Player getPlayer();

    public PlayerWatcher getWatcher();

    public double getHealth();

    public double getMaxHealth();

    public int getTeam();

    public boolean isSpectator();

    public boolean isClickable();

    public boolean isDead();

    public boolean isZoom();

    public void setZoom(boolean zoom, float zoomLevel);

    public void setDead(boolean dead);

    public void setClickable(boolean clickable);

    public void setHealth(double health);

    public void setMaxHealth(double maxHealth);

    public void setTeam(int team);

    public void setSpectator(boolean spectator);

    public void infect();

    public void kill();

    @Deprecated
    public void drawParticleLine(Particle particle, double start, double distance, double separate);

    @Deprecated
    public void drawParticleLine(Particle particle, double startX, double startY, double startZ, double distance,
                                 double separateX, double separateY, double separateZ);

    @Deprecated
    public HitEntity drawParticleLine(Particle particle, double startX, double startY, double startZ, double distance,
                                      double separateX, double separateY, double separateZ, GwGunItem gun);

    public HitEntity drawParticleLine(Particle particle, double startX, double startY,
                                      double startZ, double distance, Angle angle, double separate, GwGunItem gun, boolean aim);

    public HitEntity drawParticleLine(Particle particle, double startX, double startY, double startZ, double distance, double separate, GwKnifeItem gun);

    public void giveItem(GwItem item);

    public class Detail extends EntityData.Detail {
        public String getOriginalName() {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void setName(String name) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void setName(Player from, String name) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void setNameByMap(Map<Player, String> map) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void resetName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void resetName(Player from) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public boolean canSee(LivingEntity from) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public boolean canSee(LivingEntity from, double distance) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public String getName(Player player) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void show(Player player) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void hide(Player player) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void updateName() {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void updateName(Player player) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }
    }

    public void sendMessage(String message);

    public void sendMessage(BaseComponent component);

    public void sendMessage(BaseComponent... components);

    public void sendMessage(String[] messages);

    public Detail detail();

}
