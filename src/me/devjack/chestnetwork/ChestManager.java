package me.devjack.chestnetwork;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;

import me.devjack.chestnetwork.network.Network;
import me.devjack.chestnetwork.network.NetworkChest;
import me.devjack.chestnetwork.network.NetworkColor;
import me.devjack.chestnetwork.network.NetworkManager;
import me.devjack.chestnetwork.network.NetworkPlayer;

public class ChestManager {

	private static final List<Player> inChestFilterMode = new ArrayList<Player>();
	
	public static List<Player> getPlayersInFilterMode() {
		return inChestFilterMode;
	}
	
	public static boolean isInFilterModer(Player p) {
		return inChestFilterMode.contains(p);
	}
	
	public static void setFilterMode(Player p) {
		inChestFilterMode.add(p);
	}
	
	public static void removeFilterMode(Player p) {
		inChestFilterMode.remove(p);
	}
	
	public static NetworkChest getNetworkChest(Location chestLoc) {
		for(NetworkPlayer np : NetworkManager.getNetworkPlayers()) {
			for(Network n : np.getNetworks()) {
				for(NetworkChest nc : n.getNetworkChests()) {
					if(nc.getChestLocation().equals(chestLoc)) return nc;
				}
			}
		}
		
		return null;
	}
	
	public static NetworkChest getNetworkChest(Player p, Location chestLoc) {
		NetworkPlayer np = NetworkManager.getNetworkPlayer(p);
		for(Network n : np.getNetworks()) {
			for(NetworkChest nc : n.getNetworkChests()) {
				if(nc.getChestLocation().equals(chestLoc)) return nc;
			}
		}
		
		return null;
	}
	
	public static NetworkColor getNetworkColor(Block chest) {
		
		Location woolLoc = chest.getLocation();
		woolLoc.setY(woolLoc.getY() - 1);
		Block wool = woolLoc.getBlock();
		
		if(!(wool.getState().getData() instanceof Wool)) return null;
		
		DyeColor color = ((Wool) wool.getState().getData()).getColor();
		
		if(color == DyeColor.WHITE) {
			return NetworkColor.WHITE;
		} else if(color == DyeColor.ORANGE) {
			return NetworkColor.ORANGE;
		} else if(color == DyeColor.MAGENTA) {
			return NetworkColor.MAGENTA;
		} else if(color == DyeColor.LIGHT_BLUE) {
			return NetworkColor.LIGHT_BLUE;
		} else if(color == DyeColor.YELLOW) {
			return NetworkColor.YELLOW;
		} else if(color == DyeColor.LIME) {
			return NetworkColor.LIGHT_GREEN;
		} else if(color == DyeColor.PINK) {
			return NetworkColor.PINK;
		} else if(color == DyeColor.GRAY) {
			return NetworkColor.GRAY;
		} else if(color == DyeColor.SILVER) {
			return NetworkColor.LIGHT_GRAY;
		} else if(color == DyeColor.CYAN) {
			return NetworkColor.CYAN;
		} else if(color == DyeColor.PURPLE) {
			return NetworkColor.PURPLE;
		} else if(color == DyeColor.BLUE) {
			return NetworkColor.BLUE;
		} else if(color == DyeColor.BROWN) {
			return NetworkColor.BROWN;
		} else if(color == DyeColor.GREEN) {
			return NetworkColor.GREEN;
		} else if(color == DyeColor.RED) {
			return NetworkColor.RED;
		} else if(color == DyeColor.BLACK) {
			return NetworkColor.BLACK;
		}
		
		return null;
	}
}
