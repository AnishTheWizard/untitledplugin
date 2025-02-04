package io.github.anishthewizard;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;

public final class ploog extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("YIPPEEE!!!!Kaiyay");
        Bukkit.getPluginCommand("balls").setExecutor(new BallsCommand());

        Bukkit.getPluginManager().registerEvents(new TNTStick(), this);
        Bukkit.getPluginManager().registerEvents(new GrappleHook(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::BlowUpTNT, 0L, 0L);
    }

    public void BlowUpTNT() {
        for(int i = 0; i < TNTStick.primedSet.size(); i++) {
            TNTPrimed tp = TNTStick.primedSet.get(i);
            if(tp.isDead()) {
                TNTStick.primedSet.remove(tp);
                i--;
            }
            else {
                Vector vector = tp.getVelocity();
                double x = vector.getX();
                double y = vector.getY();
                double z = vector.getZ();

                // Calculate the magnitude of the vector
                double speed = Math.sqrt(x * x + y * y + z * z);
                if(speed < 4 || isEntities(tp.getNearbyEntities(1, 1, 1))) {
                    tp.setFuseTicks(0);
                }
            }
        }
    }

    public boolean isEntities(List<Entity> entityList) {
        for(Entity ent : entityList) {
            if(ent instanceof Mob) {return true;}
        }
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
