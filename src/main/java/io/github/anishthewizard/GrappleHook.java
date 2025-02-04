package io.github.anishthewizard;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class GrappleHook implements Listener {

    public Vector zero = new Vector(0, 0, 0);

    @EventHandler
    public void onGrapple(PlayerFishEvent event) {
        Player player = event.getPlayer();
        PlayerFishEvent.State state = event.getState();
        if (state == PlayerFishEvent.State.REEL_IN) {
            Location loc = event.getHook().getLocation();
            
            Vector delta = loc.subtract(player.getLocation()).toVector();
            double mag = zero.distance(delta);

            double pitch = Math.asin(delta.getY() / mag);
            double yaw = Math.atan2(delta.getZ(), delta.getX());

            Vector vector = getForceVector(yaw, pitch);

            player.setVelocity(vector);
        }
    }

    public Vector getForceVector(double yaw, double pitch) {
        double x = Math.cos(yaw) * Math.cos(pitch);
        double y = Math.sin(pitch);
        double z = Math.sin(yaw) * Math.cos(pitch);

        return new Vector(x, y, z).multiply(5);
    }
}
