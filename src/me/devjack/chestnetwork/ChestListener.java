package me.devjack.chestnetwork;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.devjack.chestnetwork.network.InputChest;
import me.devjack.chestnetwork.network.Network;
import me.devjack.chestnetwork.network.NetworkChest;
import me.devjack.chestnetwork.network.NetworkColor;
import me.devjack.chestnetwork.network.NetworkManager;
import me.devjack.chestnetwork.network.NetworkPlayer;
import me.devjack.chestnetwork.network.StorageChest;

public class ChestListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		if(!(b.getState() instanceof Chest || b.getState() instanceof DoubleChest)) return;
		
		Chest c = (Chest) b.getState();
		NetworkColor color = ChestManager.getNetworkColor(b);
		
		if(color == null) return;
			
		int x = c.getX();
		int y = c.getY();
		int z = c.getZ();
		World w = c.getWorld();
		
		Location loc = new Location(w, x + 1, y, z);
		if(loc.getBlock().getState() instanceof Chest) {
			if(ChestManager.getNetworkChest(p, loc) != null) return;
		}
		
		loc = new Location(w, x - 1, y, z);
		if(loc.getBlock().getState() instanceof Chest) {
			if(ChestManager.getNetworkChest(p, loc) != null) return;
		}
		
		loc = new Location(w, x, y, z + 1);
		if(loc.getBlock().getState() instanceof Chest) {
			if(ChestManager.getNetworkChest(p, loc) != null) return;
		}
		
		loc = new Location(w, x, y, z - 1);
		if(loc.getBlock().getState() instanceof Chest) {
			if(ChestManager.getNetworkChest(p, loc) != null) return;
		}
		
		NetworkPlayer np = NetworkManager.getNetworkPlayer(p);
		Network n = np.getNetwork(color);
		
		if(c.getInventory().getName().equals(ChatColor.GOLD + "Input Chest")) {
			System.out.println("Called input creation!");
			InputChest ic = new InputChest(n, c);
			n.addNetworkChest(ic);
		}
		else if(c.getInventory().getName().equals(ChatColor.GREEN + "Storage Chest")) {
			System.out.println("Called storage creation!");
			StorageChest sc = new StorageChest(n, c);
			n.addNetworkChest(sc);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		if(!(b.getState() instanceof Chest || b.getState() instanceof DoubleChest)) return;
		
		NetworkChest nc = ChestManager.getNetworkChest(b.getLocation());
		if(nc == null) return;
		
		if(!(nc.getNetwork().getOwner() == p)) {
			p.sendMessage("You cannot break a network chest that you don't own!");
			e.setCancelled(true);
			return;
		}
		
		NetworkPlayer np = NetworkManager.getNetworkPlayer(p);
		Network n = np.getNetwork(nc.getColor());
		n.removeNetworkChest(nc);
	}
}
