package me.devjack.chestnetwork;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.devjack.chestnetwork.commands.AccessCommandExecutor;
import me.devjack.chestnetwork.network.InputChest;
import me.devjack.chestnetwork.network.Network;
import me.devjack.chestnetwork.network.NetworkChest;
import me.devjack.chestnetwork.network.NetworkManager;
import me.devjack.chestnetwork.network.NetworkPlayer;
import me.devjack.chestnetwork.network.StorageChest;

public class ChestNetwork extends JavaPlugin {

	private final ConfigManager cm = new ConfigManager(this);

	@Override
	public void onEnable() {
		new NetworkManager();
		this.registerListeners();
		this.registerCommands();

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();

		System.out.print("[ChestNetwork] Loading all chest networks from config...");
		cm.loadAllNetworkPlayers();
		System.out.println("[ChestNetwork] Load complete!");

		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for(NetworkPlayer np : NetworkManager.getNetworkPlayers()) {
					for(Network n : np.getNetworks()) {
						n.moveItems();
					}
				}
			}
		}, 0, 100L);
		System.out.println("[ChestNetwork] Scheduler started!");
	}

	@Override
	public void onDisable() {
		System.out.print("[ChestNetwork] Saving all chest networks to config...");
		File configFile = new File(this.getDataFolder(), "config.yml");
		configFile.delete();
		saveDefaultConfig();
		reloadConfig();
		cm.saveAllNetworkPlayers();
		System.out.println("[ChestNetwork] Save complete!");
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ChestListener(), this);
		pm.registerEvents(new FilterListener(), this);
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new InventoryListener(), this);
	}

	private void registerCommands() {
		this.getCommand("access").setExecutor(new AccessCommandExecutor());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;

		Player p = (Player) sender;

		if (label.equalsIgnoreCase("filter")) {
			ChestManager.setFilterMode(p);
		} else if (label.equalsIgnoreCase("getChests")) {
			ItemStack inputChest = new ItemStack(Material.CHEST, 4);
			ItemMeta icMeta = inputChest.getItemMeta();
			icMeta.setDisplayName(ChatColor.GOLD + "Input Chest");
			inputChest.setItemMeta(icMeta);

			ItemStack storageChest = new ItemStack(Material.CHEST, 4);
			ItemMeta scMeta = inputChest.getItemMeta();
			scMeta.setDisplayName(ChatColor.GREEN + "Storage Chest");
			storageChest.setItemMeta(scMeta);

			p.getInventory().addItem(inputChest);
			p.getInventory().addItem(storageChest);
			p.updateInventory();
		}
		else if(label.equalsIgnoreCase("info")) {
			NetworkPlayer np = NetworkManager.getNetworkPlayer(p);
			
			p.sendMessage("Your Networks:");
			for(Network n : np.getNetworks()) {
				int input = 0;
				int storage = 0;
				
				for(NetworkChest nc : n.getNetworkChests()) {
					if(nc instanceof InputChest) input++;
					if(nc instanceof StorageChest) storage++;
				}
				
				p.sendMessage(n.getColor().toString() + "(Inputs: " + input + ", Storage: " + storage + ")");
			}
		}

		return true;
	}
}
