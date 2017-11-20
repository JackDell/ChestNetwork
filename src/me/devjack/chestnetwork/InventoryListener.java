package me.devjack.chestnetwork;

import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

import me.devjack.chestnetwork.network.NetworkChest;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent e) {
		if(!(e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest)) return;
		
		Chest c = (Chest) e.getInventory().getHolder();
		
		Player p = (Player) e.getPlayer();
		
		NetworkChest nc = ChestManager.getNetworkChest(c.getLocation());
		if(nc == null) return;
		
		if(nc.getNetwork().hasAccess(p)) {
			return;
		}
		
		e.setCancelled(true);
		p.sendMessage("You do not have access to this chest network!");
	}
	
}
