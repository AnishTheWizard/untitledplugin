package io.github.anishthewizard;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class TNTStick  implements Listener {

    public static ArrayList<TNTPrimed> primedSet = new ArrayList<>();

    public static Random rand = new Random();

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if(event.getEntity() instanceof TNTPrimed) {
            TNTPrimed tnt = (TNTPrimed) event.getEntity();
            if(tnt.getSource() instanceof Player)
                for(int i = 0; i < 50; ++i) event.getEntity().getWorld().spawn(event.getLocation().add(10 * rand.nextDouble()-5, 0, 10 * rand.nextDouble() -5 ), TNTPrimed.class).setFuseTicks(0);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(event.hasItem() && event.getItem().getType() == Material.STICK) {
            flingTNT(event.getPlayer());
        }
    }

    public void flingTNT(Player player) {
        Location loc = player.getLocation();
        double yaw = Math.toRadians(player.getLocation().getYaw() + 90);
        double pitch = Math.toRadians(-player.getLocation().getPitch());
        double x = Math.cos(yaw) * Math.cos(pitch);
        double y = Math.sin(pitch);
        double z = Math.sin(yaw) * Math.cos(pitch);
        Vector vector = new Vector(x, y, z).multiply(5);
        TNTPrimed tp = player.getWorld().spawn(player.getLocation().add(1 * Math.cos(yaw), 1, 1 * Math.sin(yaw)), TNTPrimed.class);
        primedSet.add(tp);
        tp.setIsIncendiary(true);
        tp.setGlowing(true);
//        tp.setGravity(false);
        tp.setVelocity(vector);
        tp.setSource(player);
    }

    public void summonTNT(World world, Location l) {
        for(int i = 0; i < 10; i++) {
            world.spawn(l.add(0, 0, 0), TNTPrimed.class);
        }
    }
}
