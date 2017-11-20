package me.devjack.chestnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.devjack.chestnetwork.network.ChestType;
import me.devjack.chestnetwork.network.InputChest;
import me.devjack.chestnetwork.network.Network;
import me.devjack.chestnetwork.network.NetworkChest;
import me.devjack.chestnetwork.network.NetworkColor;
import me.devjack.chestnetwork.network.NetworkManager;
import me.devjack.chestnetwork.network.NetworkPlayer;
import me.devjack.chestnetwork.network.StorageChest;

public class ConfigManager {

	private ChestNetwork plugin;
	
	public ConfigManager(ChestNetwork plugin) {
		this.plugin = plugin;
	}
	
	public void saveAllNetworkPlayers() {
		FileConfiguration config = this.plugin.getConfig();
		
		for(NetworkPlayer np : NetworkManager.getNetworkPlayers()) {
			for(Network n : np.getNetworks()) {
				
				int i = 0;
				for(NetworkChest nc : n.getNetworkChests()) {
					String path = (n.getPath() + "Chests." + i + ".");
					config.set(path + "Type", nc.getType().toString());
					
					System.out.println("Called 1");
					if(nc instanceof StorageChest) {
						System.out.println("Called in");
						int k = 0;
						for(Material m : ((StorageChest) nc).getItemFilter()) {
							config.set(path + "ItemFilter." + k, m.toString());
							k++;
						}
					}
					
					Location chestLoc = nc.getChestLocation();
					config.set(path + "Location.x", chestLoc.getBlockX());
					config.set(path + "Location.y", chestLoc.getBlockY());
					config.set(path + "Location.z", chestLoc.getBlockZ());
					config.set(path + "Location.world", chestLoc.getWorld().getUID().toString());
					i++;
				}
				
				i = 0;
				for(Player p : n.getWhoCanAccess()) {
					String path = (n.getPath() + "WhoCanAccess." + i);
					config.set(path, p.getUniqueId().toString());
					i++;
				}
			}
			plugin.saveConfig();
		}
	}
	
	public void loadAllNetworkPlayers() {
		FileConfiguration config = plugin.getConfig();
		for(String uuid : config.getKeys(false)) {
			UUID uniqueId = UUID.fromString(uuid);
			Player p = Bukkit.getPlayer(uniqueId);
			NetworkPlayer np = new NetworkPlayer(p);
			
			for(String network : config.getConfigurationSection(uuid).getKeys(false)) {
				NetworkColor color = NetworkColor.fromString(network);
				Network newNetwork = new Network(p, color);
				
				for(String chestNumber : config.getConfigurationSection(uuid + "." + network + ".Chests").getKeys(false)) {
					String path = (uuid + "." + network + ".Chests." + chestNumber + ".");
					
					ChestType type = ChestType.fromString(config.getString(path + "Type"));
					
					int x = config.getInt(path + "Location.x");
					int y = config.getInt(path + "Location.y");
					int z = config.getInt(path + "Location.z");
					World w = Bukkit.getWorld(UUID.fromString(config.getString(path + "Location.world")));
					
					Location chestLoc = new Location(w, x, y, z);
					
					Block b = chestLoc.getBlock();
					
					if(!(b.getState() instanceof Chest || b.getState() instanceof DoubleChest)) continue;
					
					Chest chest = (Chest) b.getState();
					
					if(type == ChestType.INPUT) {
						InputChest ic = new InputChest(newNetwork, chest);
						newNetwork.addNetworkChest(ic);
					}
					else {
						List<Material> itemFilter = new ArrayList<Material>();
						
						try {
							for(String filter : config.getConfigurationSection(path + "ItemFilter").getKeys(false)) {
								itemFilter.add(Material.valueOf(config.getString(path + "ItemFilter." + filter)));
							}
						}
						catch(NullPointerException e) {
							// Will get a null pointer if there is no items being filtered, catching and continuing
						}
						
						StorageChest sc = new StorageChest(newNetwork, chest, itemFilter);
						newNetwork.addNetworkChest(sc);
					}
					
					
				}
				
				String path = (newNetwork.getPath() + "WhoCanAccess");
				try {
					for(String access : config.getConfigurationSection(path).getKeys(false)) {
						newNetwork.allowAccess(Bukkit.getPlayer(UUID.fromString(config.getString(path + "." + access))));
					}
				}
				catch(NullPointerException e) {
					// Will get a null pointer if there are no players allowed access to network, catching and continuing
				}
				
				np.addNetwork(newNetwork);
			}
			NetworkManager.addNetworkPlayer(np);
		}
	}
	
}
