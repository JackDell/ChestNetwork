package me.devjack.chestnetwork;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.devjack.chestnetwork.network.InputChest;
import me.devjack.chestnetwork.network.NetworkChest;
import me.devjack.chestnetwork.network.StorageChest;

public class FilterListener implements Listener {
	
	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent e) {
		if(!(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)) return;
		
		Block b = e.getClickedBlock();
		Player p = e.getPlayer();
		
		if(!(b.getState() instanceof Chest || b.getState() instanceof DoubleChest)) return;
		
		NetworkChest nc = ChestManager.getNetworkChest(p, b.getLocation());
		if(nc == null) return;
		
		if(ChestManager.isInFilterModer(p)) {
			if(nc instanceof InputChest) return;
			StorageChest sc = (StorageChest) nc;
			
			for(ItemStack is : ((Chest) b.getState()).getInventory().getContents()) {
				if(is == null) continue;
				sc.addFilter(is.getType());
			}
			
			e.setCancelled(true);
			ChestManager.removeFilterMode(p);
			p.sendMessage("Chests filter updated!");
			return;
		}
	}
	
}
